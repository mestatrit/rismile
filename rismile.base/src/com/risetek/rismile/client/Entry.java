package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.RismileContext.EnablePrivateEvent;
import com.risetek.rismile.client.RismileContext.EnablePrivateHandler;
import com.risetek.rismile.client.control.EntryController;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.Heartbeat;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public abstract class Entry implements EntryPoint {
	
	public static String SinkHeight = "470px";

	/**
	 * An image provider to make available images to Sinks.
	 */
	public interface Resources extends ClientBundle {
		final static Resources INSTANCE = GWT.create(Resources.class);
		
		@Source("gwtLogo.jpg")
		ImageResource gwtLogo();
		
		@Source("tongfaLogo.jpg")
		ImageResource tongfaLogo();
	}

	// 导航条
	public SinkList list =	(RismileContext.OEMFlag == RismileContext.OEM.risetek) ? 
			new SinkList(new Image(Resources.INSTANCE.gwtLogo())) :
			new SinkList(new Image(Resources.INSTANCE.tongfaLogo()));			
			
	private SinkInfo curInfo;
	private static Sink curSink;
	// 操作提示
	private HTML description = new HTML();

	private final HorizontalPanel descriptionPanel = new HorizontalPanel();

	private final VerticalPanel panel = new VerticalPanel();
	private final DockPanel sinkContainer = new DockPanel();
//	private final FlowPanel maskPanel = new FlowPanel();
	private final FlowPanel headPanel = new FlowPanel();
	private final HTML hbMessage = new HTML();
	private final HTML message = new HTML();

	public	static final Button enable = new Button();
	public	static boolean login = false;

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
	    Window.enableScrolling(true);
	    
		panel.setStyleName("rismile");
		// Load all the sinks.
		
		loadSinks();
		
		FlowPanel btn = new FlowPanel();
		btn.add(enable);
		btn.setStyleName("enablePanel");
		list.addWidget(btn);
		enable.setText("特权登录");

		headPanel.setWidth("100%");
		headPanel.add(list);
		panel.add(hbMessage);
		hbMessage.setStyleName("http-message");
		DOM.setElementProperty(hbMessage.getElement(), "id", "hbMessage");
		panel.add(message);
		message.setStyleName("http-message");
		DOM.setElementProperty(message.getElement(), "id", "message");

		sinkContainer.setStyleName("Sink");
		sinkContainer.setWidth("100%");

		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		descriptionPanel.setWidth("100%");
		descriptionPanel.setStyleName("description");
		descriptionPanel.add(description);
		
		panel.add(headPanel);
		panel.add(descriptionPanel);
		panel.add(sinkContainer);
		panel.setWidth("100%");
//		panel.setBorderWidth(1);
		
		RismileContext.addEnablePrivaterHandler( new EnablePrivateHandler() {
			@Override
			public void onEnablePrivate(EnablePrivateEvent event) {
				if( login )
					curSink.getController().enablePrivate();
				else
					curSink.getController().disablePrivate();
			}
		});

		
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
//	    RootLayoutPanel root = RootLayoutPanel.get();
//	    root.add(new HTML());

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
				if(login){
					login = false;
					enable.setText("特权登录");
					RismileContext.fireEvent(new EnablePrivateEvent());
				}
				else
					EntryController.load();
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

}
