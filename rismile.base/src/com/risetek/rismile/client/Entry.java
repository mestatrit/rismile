package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.dialog.AdminDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.utils.MessageConsole;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("deprecation")
public abstract class Entry implements EntryPoint, RequestCallback{
	public enum OEM
	{
		risetek,
		tongfa
	};
	
	public static OEM OEMFlag = OEM.risetek;
	
	public static String SinkHeight = "470px";
	
	private RequestFactory remoteRequest = new RequestFactory();
	private String checkPath = "penable";

	/**
	 * An image provider to make available images to Sinks.
	 */
	public interface Images extends ImageBundle {
		AbstractImagePrototype gwtLogo();
		AbstractImagePrototype tongfaLogo();
	}

	private static final Images images = (Images) GWT.create(Images.class);

	// 导航条
	/*
	public SinkList list = new SinkList(images.gwtLogo().createImage());
	*/

	public SinkList list =	(OEMFlag == OEM.risetek) ? 
			new SinkList(images.gwtLogo().createImage()) :
			new SinkList(images.tongfaLogo().createImage());			

	private SinkInfo curInfo;
	private Sink curSink;
	// 操作提示
	private HTML description = new HTML();

	private final HorizontalPanel descriptionPanel = new HorizontalPanel();

	private final VerticalPanel panel = new VerticalPanel();
	private final DockPanel sinkContainer = new DockPanel();
	private final FlowPanel maskPanel = new FlowPanel();
	private final FlowPanel headPanel = new FlowPanel();
	private final HTML hbMessage = new HTML();
	private final HTML message = new HTML();
	public final Button enable = new Button();
	
	public static boolean login = false;

	public void onHistoryChanged(String token) {
		// Find the SinkInfo associated with the history context. If one is
		// found, show it (It may not be found, for example, when the user mis-
		// types a URL, or on startup, when the first context will be "").
		SinkInfo info = list.find(token);
		if (info == null) {
			showInfo();
			return;
		}
		show(info, false);
	}

	public void onModuleLoad() {
		// Load all the sinks.
		loadSinks();
		enable.setText("特权登录");
		enable.setStyleName("enable-button");
		panel.setStyleName("rismile");

		headPanel.setWidth("100%");
		// DOM.setStyleAttribute(headPanel.getElement(), "position",
		// "relative");
		headPanel.add(list);
		headPanel.add(hbMessage);
		hbMessage.setStyleName("hb-message");
		DOM.setElementProperty(hbMessage.getElement(), "id", "hbMessage");
		headPanel.add(message);
		// message.setWidth("100%");
		message.setStyleName("http-message");
		DOM.setElementProperty(message.getElement(), "id", "message");

		sinkContainer.setStyleName("Sink");
		sinkContainer.setWidth("100%");

		// description.setStyleName("description");
		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		descriptionPanel.setWidth("100%");
		descriptionPanel.setStyleName("description");
		descriptionPanel.add(description);
		maskPanel.setWidth("100%");
		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
		maskPanel.add(headPanel);
		maskPanel.add(descriptionPanel);
		maskPanel.add(sinkContainer);

		panel.add(maskPanel);
		panel.setWidth("100%");
		// panel.setBorderWidth(1);

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				// Find the SinkInfo associated with the history context. If one
				// is
				// found, show it (It may not be found, for example, when the
				// user mis-
				// types a URL, or on startup, when the first context will be
				// "").
				String token = event.getValue();
				SinkInfo info = list.find(token);
				if (info == null) {
					showInfo();
					return;
				}
				show(info, false);
			}
		});

		RootPanel.get("root").add(panel);
//		RootLayoutPanel root = RootLayoutPanel.get();
//		root.add(panel);
		
		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
		// 启动对web服务的poll动作，以确定web是否正确服务。
		Heartbeat.startHeartbeat();
		
		enable.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				load();
				if(login){
					login = false;
					enable.setText("特权登录");
					Window.Location.reload();
				}
			}
		});
	}

	public void show(SinkInfo info, boolean affectHistory) {
		if (info == curInfo) {
			return;
		}
		curInfo = info;

		// Remove the old sink from the display area.
		if (curSink != null) {
			curSink.onHide();
			sinkContainer.remove(curSink);
		}

		// Get the new sink instance, and display its description in the
		// sink list.
		curSink = info.getInstance();
		list.setSinkSelection(info.getName());
		description.setText(info.getDescription() + "    ");

		if (affectHistory) {
			History.newItem(info.getName());
		}

		// Display the new sink.
		curSink.setVisible(false);
		sinkContainer.add(curSink, DockPanel.CENTER);
		sinkContainer.setCellHorizontalAlignment(curSink,
				VerticalPanel.ALIGN_CENTER);
		curSink.setVisible(true);
		curSink.onShow();
	}

	/**
	 * 载入各个sinks的实例，这与每个应用相关，所以在这里用abstract声明，具体载入的实例在其实现类里面去完成。
	 */
	abstract public void loadSinks();

	// 首先显示Home界面。
	// TODO: Home界面与每个产品相关，是否/怎样实现一个abstract来强制产品实例类来构造实际的Home页面呢？
	// 目前采用各个项目自己构造自己的Home界面来实现这个要求。by ychun.w
	private void showInfo() {
		show(list.find("Home"), false);
	}
	
	public void load() {
		MessageConsole.setText("提取系统配置");
		remoteRequest.get(checkPath, null, this);
	}
	
	public class Control implements ClickHandler, RequestCallback {
		public AdminDialog dialog = new AdminDialog(AdminDialog.LOGIN);

		public void onClick(ClickEvent event) {
			if( dialog.isValid())
			{
				login(dialog.pwdBox.getText(), this);
			}
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			if( dialog.processResponse(response)){
				login = true;
				enable.setText("退出特权");
			}
//				load();
		}
		
		public void login(String password, RequestCallback callback) {
			String requestData = "password=" + password;
			remoteRequest.get(checkPath, requestData, callback);

		}
	}
}
