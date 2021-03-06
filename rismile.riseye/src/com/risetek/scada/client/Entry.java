package com.risetek.scada.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.scada.client.sink.Sink;
import com.risetek.scada.client.sink.SinkList;
import com.risetek.scada.client.sink.Sink.SinkInfo;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public abstract class Entry implements EntryPoint{

	public static String SinkHeight = "470px";

	/**
	 * An image provider to make available images to Sinks.
	 */
	/*
	public interface Images extends ImageBundle {
		AbstractImagePrototype gwtLogo();
	}
*/
	public interface Images extends ClientBundle  {
		@Source("gwtLogo.jpg")		ImageResource  gwtLogo();
	}	
	private static final Images images = (Images) GWT.create(Images.class);

	// 导航条
	public SinkList list = new SinkList();

	private SinkInfo curInfo;
	private Sink curSink;
	// 操作提示
	private HTML description = new HTML();

	private final HorizontalPanel descriptionPanel = new HorizontalPanel();

	private final VerticalPanel panel = new VerticalPanel();
	private final Grid titlePanel = new Grid(1,2);
	private final DockPanel sinkContainer = new DockPanel();
	private final DecoratorPanel sinkContainerOut = new DecoratorPanel();
	private final FlowPanel maskPanel = new FlowPanel();
	private final Grid headPanel = new Grid(1,2);
	private final HTML message = new HTML("hello");

	private final DockPanel nav = new DockPanel();
	
	
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
		
		Grid mainTable = new Grid(3, 1);
//		mainTable.setBorderWidth(1);
		
//		panel.setBorderWidth(1);
//		sinkContainer.setBorderWidth(1);
//		sinkContainerOut.setWidth("600px");
//		titlePanel.setBorderWidth(1);
		titlePanel.setWidget(0, 0, new Image(images.gwtLogo()));
		titlePanel.getCellFormatter().setStyleName(0, 0, "logo");
		message.setStyleName("http-message");
		DOM.setElementProperty(message.getElement(), "id", "message");
		titlePanel.setWidget(0,1,message);
		titlePanel.setWidth("100%");
		
		mainTable.setWidget(0, 0, titlePanel);
//		panel.add(titlePanel);

//		headPanel.setBorderWidth(1);
		headPanel.setCellPadding(0);
		headPanel.setCellSpacing(0);

		nav.setWidth("240px");
//		nav.add(new Image(images.gwtLogo()),DockPanel.NORTH);
		nav.add(list, DockPanel.CENTER);
		nav.setHeight("100%");
		headPanel.setWidget(0, 0, nav);
		headPanel.getCellFormatter().setStyleName(0, 0, "menuList");
		
//		description.setStyleName("description");
		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		descriptionPanel.setWidth("100%");
		descriptionPanel.add(description);
		maskPanel.setWidth("100%");
		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
		sinkContainer.add(descriptionPanel, DockPanel.NORTH);
		DOM.setStyleAttribute(sinkContainer.getElement(), "backgroundColor", "#E0ECFF");

		sinkContainerOut.add(sinkContainer);
		headPanel.setWidget(0, 1, sinkContainerOut);
		sinkContainer.setWidth("800px");
		sinkContainer.setHeight("600px");
		maskPanel.add(headPanel);
		
		panel.add(maskPanel);

		// Load all the sinks.
		loadSinks();
		
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

		mainTable.setWidget(1, 0, panel);
		
		RootPanel.get("root").add(mainTable);

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
		description.setText(info.getDescription() + "    ");

		if (affectHistory) {
			History.newItem(info.getName());
		}

		// Display the new sink.
		curSink.setVisible(false);
		sinkContainer.add(curSink, DockPanel.SOUTH);
		//sinkContainer.setCellHorizontalAlignment(curSink,VerticalPanel.ALIGN_CENTER);
		curSink.setVisible(true);
		curSink.onShow();
	}

	/**
	 * 载入各个sinks的实例，这与每个应用相关，所以在这里用abstract声明，具体载入的实例在其实现类里面去完成。
	 */
	abstract public void loadSinks();

	private void showInfo() {
		show(list.find("Home"), false);
	}
}
