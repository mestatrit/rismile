package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.RismileContext.EnablePrivateEvent;
import com.risetek.rismile.client.RismileContext.EnablePrivateHandler;
import com.risetek.rismile.client.control.EntryController;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.utils.KEY;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

@SuppressWarnings("deprecation")
public abstract class Entry extends Composite implements EntryPoint {
	
	private static EntryUiBinder uiBinder = GWT.create(EntryUiBinder.class);

	interface EntryUiBinder extends UiBinder<Widget, Entry> {
	}
	
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
	protected static Sink curSink;
	// 操作提示
	private HTML description = new HTML();

	private final Grid descriptionPanel = new Grid(1, 2);

	@UiField VerticalPanel panel;
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
	    initWidget(uiBinder.createAndBindUi(this));
		panel.setStyleName("rismile");
		// Load all the sinks.
		
		loadSinks();
		
		enable.setText("特权登录(L)");
		enable.setWidth("100px");
		
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
//		descriptionPanel.add(new HTML());
//		descriptionPanel.add(enable);
		descriptionPanel.setWidget(0, 0, description);
		descriptionPanel.setWidget(0, 1, enable);
		descriptionPanel.getCellFormatter().setWidth(0, 1, "120px");
		descriptionPanel.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
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

//		panel.addKeyPressHandler(new KeyPressHandler() {
//			public void onKeyPress(KeyPressEvent event) {
//				event.getSource();
//			}
//		});
//		RootPanel.get("root").add(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    root.add(this);
	    
		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
		// 启动对web服务的poll动作，以确定web是否正确服务。
		Heartbeat.startHeartbeat();
		
		enable.addClickHandler(new EnableAction());
		
		regAction();
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
			History.newItem(info.getTag());
		}

		// Display the new sink.
		curSink.setVisible(false);
		sinkContainer.add(curSink, DockPanel.CENTER);
		sinkContainer.setCellHorizontalAlignment(curSink,
				VerticalPanel.ALIGN_CENTER);
		curSink.setVisible(true);
		curSink.onShow();
		int height = curSink.getOffsetHeight() + 200;
		RootLayoutPanel.get().setHeight(Integer.toString(height) + "px");
//		int width = curSink.getOffsetWidth() + 40;
//		RootLayoutPanel.get().setWidth(Integer.toString(width) + "px");
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

	static class EnableAction implements ClickHandler {
		public void onClick(ClickEvent event) {
			if(login){
				login = false;
				enable.setText("特权登录(L)");
				RismileContext.fireEvent(new EnablePrivateEvent());
			}
			else {
				EntryController.load();
			}
		}
	}
	
	private void regAction(){
		DOM.addEventPreview(new EventPreview() {
			public boolean onEventPreview(Event eventPreview) {
				int type = eventPreview.getTypeInt();
				switch (type) {
				case Event.ONMOUSEDOWN:
					return true;
				case Event.ONMOUSEMOVE:
					return true;
				case Event.ONMOUSEOUT:
					return true;
				case Event.ONMOUSEOVER:
					return true;
				case Event.ONMOUSEUP:
					return true;
				case Event.ONCLICK:
					return true;
				case Event.ONKEYPRESS:
					return true;
				case Event.ONKEYUP:
					return true;
				case Event.ONKEYDOWN:
					int keyCode = eventPreview.getKeyCode();
//					System.out.println(keyCode);
					if(!eventPreview.getAltKey()){
						SinkInfo info = list.getSinkSelection();
						int index = list.sinks.indexOf(info);
						if(keyCode == KEY.RIGHT){
							if(index<list.sinks.size()-1){
								info = list.sinks.get(index + 1);
							}
						} else if(keyCode == KEY.LEFT) {
							if(index > 0){
								info = list.sinks.get(index - 1);
							}
						} else if(keyCode == KEY.UP | keyCode == KEY.DOWN){
							curSink.getController().doAction(keyCode);
						}
						if (info == null) {
							showInfo();
							break;
						}
						show(info, true);
						onHistoryChanged(info.getTag());
					} else {
						if(keyCode == KEY.L){
							enable.click();
						} else {
//							if(eventPreview.getAltKey()){
								curSink.getController().doAction(keyCode);
//							}
						}
					}
				default:
					break;
				}
				return true;
			}
		});
	}
}
