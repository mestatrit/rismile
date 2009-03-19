package com.risetek.rismile.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.sink.Sink;
import com.risetek.rismile.client.sink.SinkList;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.Heartbeat;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public abstract class Entry implements EntryPoint, HistoryListener {
	
	public static String SinkHeight = "470px";
	
	/**
	 * An image provider to make available images to Sinks.
	 */
	public interface Images extends ImageBundle {
		AbstractImagePrototype gwtLogo();
	}

	private static final Images images = (Images) GWT.create(Images.class);

	// 导航条
	public SinkList list = new SinkList(images.gwtLogo().createImage());
	
	private SinkInfo curInfo;
	private Sink curSink;
	// 操作提示
	private HTML description = new HTML();
	
	private final VerticalPanel panel = new VerticalPanel();
	private final DockPanel sinkContainer = new DockPanel();
	private final FlowPanel maskPanel = new FlowPanel();
	private final FlowPanel headPanel = new FlowPanel();
	private final HTML hbMessage = new HTML();
	private final HTML message = new HTML();

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
		panel.setStyleName("rismile");
		
		headPanel.setWidth("100%");
		//DOM.setStyleAttribute(headPanel.getElement(), "position", "relative");
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

		description.setStyleName("Info");
		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		maskPanel.setWidth("100%");
		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
		maskPanel.add(headPanel);
		maskPanel.add(description);
		maskPanel.add(sinkContainer);

		panel.add(maskPanel);
		panel.setWidth("100%");
		//panel.setBorderWidth(1);

		History.addHistoryListener(this);
		RootPanel.get("root").add(panel);

		// Show the initial screen.
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);
		} else {
			showInfo();
		}
		// 启动对web服务的poll动作，以确定web是否正确服务。
		Heartbeat.startHeartbeat();
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
		description.setHTML(info.getDescription() + "  <<<<");

		if (affectHistory) {
			History.newItem(info.getName());
		}

		// 设置操作提示区域的背景颜色与info的颜色一致。
		// TODO: copyright 的背景颜色是否也该做一致性设置？
		DOM.setStyleAttribute(description.getElement(), "backgroundColor", info.getColor());

		// Display the new sink.
		curSink.setVisible(false);
		sinkContainer.add(curSink, DockPanel.CENTER);
		sinkContainer.setCellHorizontalAlignment(curSink,VerticalPanel.ALIGN_CENTER);
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
