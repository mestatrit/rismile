package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.RismileContext.EnablePrivateEvent;
import com.risetek.rismile.client.RismileContext.EnablePrivateHandler;
import com.risetek.rismile.client.conf.RuntimeConf;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.control.ClientEventBus;
import com.risetek.rismile.client.control.ClientEventBus.DirectKeyEvent;
import com.risetek.rismile.client.control.ClientEventBus.DirectKeyHandler;
import com.risetek.rismile.client.control.EntryController;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.XMLDataParse;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public abstract class Entry implements EntryPoint {
	public static String SinkHeight = "470px";

	public interface Resources extends ClientBundle {
		final static Resources INSTANCE = GWT.create(Resources.class);
		@Source("logo.png")
		ImageResource gwtLogo();
	}

	// 导航条
	public SinkList list = new SinkList(new Image(Resources.INSTANCE.gwtLogo()));
	private SinkInfo curInfo;
	protected static Sink curSink;
	// 操作提示
	private HTML description = new HTML();

	private final Grid descriptionPanel = new Grid(1, 2);

	private final VerticalPanel panel = new VerticalPanel();
	private final DockPanel sinkContainer = new DockPanel();

	private final FlowPanel headPanel = new FlowPanel();

	public static final Button enable = new Button();
	public static boolean login = false;

	public void onHistoryChanged(String token) {
		SinkInfo info = list.find(token);
		if (info == null) {
			showInfo();
			return;
		}
		show(info, false);
	}

	private void requestLoader() {
		// TODO: 我们应该用这个来计算我们通讯速度的基础。
		RequestFactory remoteRequest = new RequestFactory();
		remoteRequest.get("GetSysConfig", null, new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				// 不同版本会应答不同的属性，我们通过这个属性来动态判断怎么构造特定的显示
				// 元素。
				try {
					if( XMLDataParse.getElementNumber(response.getText(), "DV1") > 0 ) {
						RuntimeConf.AddConfig("DV1", "true");
					}
					if( XMLDataParse.getElementNumber(response.getText(), "ASSERT") > 0 ) {
						RuntimeConf.AddConfig("ASSERT", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				SinksLoader();
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("载入页面失败，请刷新页面！");
			}});
	}
	
	
	public void onModuleLoad() {

		requestLoader();
	
	}

	private void SinksLoader() {

		Window.enableScrolling(true);
		panel.setStyleName("rismile");

		// Load all the sinks.
		loadSinks();

		enable.setText("特权登录(L)");

		headPanel.setWidth("100%");
		headPanel.add(list);

		panel.add(new MessageConsole());
		
		sinkContainer.setWidth("100%");
		sinkContainer.getElement().getStyle().setBorderWidth(1, Unit.PX);
		sinkContainer.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		sinkContainer.getElement().getStyle().setBorderColor(UIConfig.OuterLine);
		sinkContainer.getElement().getStyle().setBackgroundColor("#E8EEF7");
		sinkContainer.getElement().getStyle().setPosition(Position.RELATIVE);
		
		

		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		descriptionPanel.setBorderWidth(0);
		descriptionPanel.setWidth("100%");
		//descriptionPanel.setStyleName("description");
		descriptionPanel.setCellPadding(5);
		Style style = descriptionPanel.getElement().getStyle();
		style.setBackgroundColor(UIConfig.OuterLine);
		style.setColor("yellow");
		style.setFontSize(14, Unit.PX);

		description.getElement().getStyle().setMarginRight(20, Unit.PX);
		descriptionPanel.setWidget(0, 0, description);
		descriptionPanel.setWidget(0, 1, enable);
		descriptionPanel.getCellFormatter().setWidth(0, 0, UIConfig.LeftSiderWidth+"px");
		descriptionPanel.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(headPanel);
		panel.add(descriptionPanel);
		
		SimplePanel hr = new SimplePanel();
		hr.setHeight("3px");
		panel.add(hr);
		
		panel.add(sinkContainer);
		panel.setWidth("100%");
		// panel.setBorderWidth(1);

		RismileContext.addEnablePrivaterHandler(new EnablePrivateHandler() {
			@Override
			public void onEnablePrivate(EnablePrivateEvent event) {
				if (login)
					curSink.getController().enablePrivate();
				else
					curSink.getController().disablePrivate();
			}
		});

		RootPanel.get("root").add(panel);
		
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				SinkInfo info = list.find(token);
				if (info == null) {
					showInfo();
					return;
				}
				show(info, false);
			}
		});
		
		// 启动对web服务的poll动作，以确定web是否正确服务。
		Heartbeat.startHeartbeat();

		enable.addClickHandler(new EnableAction());
		ClientEventBus.INSTANCE.addHandler(
				new DirectKeyControl(), DirectKeyEvent.TYPE);
		
		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
			
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
			History.newItem(info.getTag());
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
	private void showInfo() {
		show(list.find("Home"), false);
	}

	static class EnableAction implements ClickHandler {
		public void onClick(ClickEvent event) {
			if (login) {
				login = false;
				enable.setText("特权登录(L)");
				RismileContext.fireEvent(new EnablePrivateEvent());
			} else {
				EntryController.load();
			}
		}
	}

	private class DirectKeyControl implements DirectKeyHandler {

		public void onEvent(DirectKeyEvent event) {
			int keyCode = event.getKeyCode();
			boolean alt = event.isAlt();

			if( alt ){
				if (keyCode == KEY.L) {
					enable.click();
				} else {
					curSink.getController().doAction(keyCode, alt);
				}
				return;
			}
			
			SinkInfo info = list.getSinkSelection();
			int index = list.sinks.indexOf(info);
			if (keyCode == KEY.RIGHT) {
				if (index < list.sinks.size() - 1) {
					info = list.sinks.get(index + 1);
				}
			} else if (keyCode == KEY.LEFT) {
				if (index > 0) {
					info = list.sinks.get(index - 1);
				}
			} else if (keyCode == KEY.UP || keyCode == KEY.DOWN || keyCode == KEY.HOME 
					|| keyCode == KEY.END || keyCode ==KEY.PAGEUP || keyCode == KEY.PAGEDOWN ) {
				curSink.getController().doAction(keyCode, alt);
			}
			
			if (info == null) {
				showInfo();
				return;
			}
			show(info, true);
			onHistoryChanged(info.getTag());
		}
	}
}
