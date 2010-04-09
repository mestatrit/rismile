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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
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
public abstract class Entry implements EntryPoint, RequestCallback {
	
	public enum OEM
	{
		risetek,
		tongfa
	};
	
	public static OEM OEMFlag = OEM.risetek;

	interface localUiBinder extends UiBinder<DockLayoutPanel, Entry> {}
	protected static localUiBinder uiBinder = GWT.create(localUiBinder.class);

	private RequestFactory remoteRequest = new RequestFactory();
	private String checkPath = "penable";
	
	public static String SinkHeight = "470px";
	
//	public static int window_width = Window.getClientWidth();
//	public static int window_height = Window.getClientHeight();
	
	// 导航条
	@UiField	public SinkList list;

	private SinkInfo curInfo;
	private Sink curSink;
	// 操作提示
	@UiField
	protected	HTML description;
	
//	@UiField	Image logo;

	@UiField	HorizontalPanel descriptionPanel;

	@UiField	VerticalPanel sinkContainer;
	
	@UiField
	protected	FlowPanel maskPanel;
	
	@UiField
	protected	FlowPanel headPanel;

	@UiField
	protected	HTML hbMessage;

	@UiField
	protected	HTML message;
	
	@UiField	public Button login_out;
	public static boolean logined = false;

	
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

	public DockLayoutPanel getUiBinder(){
		DockLayoutPanel outer = uiBinder.createAndBindUi(this);
		return outer;
	}
	
	public void onModuleLoad() {
	    Window.enableScrolling(true);
	    DockLayoutPanel outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    root.add(outer);
//	    RootPanel.get("main").add(outer);

		// Load all the sinks.
		loadSinks();
		headPanel.add(list);

		DOM.setElementProperty(hbMessage.getElement(), "id", "hbMessage");
		DOM.setElementProperty(message.getElement(), "id", "message");

		
//		if( Entry.OEMFlag == Entry.OEM.risetek )
//			logo.setUrl(Images.INSTANCE.gwtLogo().getURL());
//		else
//			logo.setUrl(Images.INSTANCE.tongfaLogo().getURL());
		
		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
		
		DOM.setStyleAttribute(description.getParent().getElement(), "backGroundColor", "#6694E3");

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				// Find the SinkInfo associated with the history context. If one
				// is found, show it (It may not be found, for example, when the
				// user mis-types a URL, or on startup, when the first context will be "").
				String token = event.getValue();
				SinkInfo info = list.find(token);
				if (info == null) {
					showInfo();
					return;
				}
				show(info, false);
			}
		});

		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
		// 启动对web服务的poll动作，以确定web是否正确服务。
		Heartbeat.startHeartbeat();

		login_out.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				load();
				if( logined ) {
					logined = false;
					login_out.setText("特权登录");
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
		description.setText(info.getDescription());

		if (affectHistory) {
			History.newItem(info.getName());
		}

		// Display the new sink.
		curSink.setVisible(false);
		
		sinkContainer.add(curSink);
		curSink.setVisible(true);
		curSink.onShow();
	}

	/**
	 * 载入各个sinks的实例，这与每个应用相关，所以在这里用abstract声明，具体载入的实例在其实现类里面去完成。
	 */
	abstract public void loadSinks();
	abstract public void loadPrivateSinks();

	// 首先显示Home界面。
	// TODO: Home界面与每个产品相关，是否/怎样实现一个abstract来强制产品实例类来构造实际的Home页面呢？
	// 目前采用各个项目自己构造自己的Home界面来实现这个要求。by ychun.w
	public void showInfo() {
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
				logined = true;
				login_out.setText("退出特权");
				loadPrivateSinks();
			}
//				load();
		}
		
		public void login(String password, RequestCallback callback) {
			String requestData = "password=" + password;
			remoteRequest.get(checkPath, requestData, callback);

		}
	}
}
