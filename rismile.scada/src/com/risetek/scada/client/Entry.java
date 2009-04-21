package com.risetek.scada.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
	public interface Images extends ImageBundle {
		AbstractImagePrototype gwtLogo();
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
	private final DockPanel sinkContainer = new DockPanel();
	private final DecoratorPanel sinkContainerOut = new DecoratorPanel();
	private final FlowPanel maskPanel = new FlowPanel();
	private final Grid headPanel = new Grid(1,2);

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
		
		//panel.setBorderWidth(1);
		//sinkContainer.setBorderWidth(1);
		//sinkContainerOut.setWidth("600px");
		// Load all the sinks.
		loadSinks();
		panel.add(images.gwtLogo().createImage());
		
		headPanel.setWidth("100%");
		headPanel.getCellFormatter().setWidth(0, 0, "20%");
		headPanel.getCellFormatter().setWidth(0, 1, "80%");
		headPanel.setWidget(0, 0, list);

		//headPanel.setBorderWidth(1);
		headPanel.setCellPadding(0);
		headPanel.setCellSpacing(0);
		
		sinkContainer.setWidth("100%");

		// description.setStyleName("description");
		description.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		descriptionPanel.setWidth("100%");
		descriptionPanel.add(description);
		maskPanel.setWidth("100%");
		DOM.setStyleAttribute(maskPanel.getElement(), "position", "relative");
		DOM.setElementProperty(maskPanel.getElement(), "id", "maskPanel");
		sinkContainer.add(descriptionPanel, DockPanel.NORTH);
		sinkContainerOut.add(sinkContainer);

		DOM.setStyleAttribute(sinkContainer.getElement(), "backgroundColor", "#E0ECFF");

		
		headPanel.setWidget(0, 1, sinkContainerOut);
		sinkContainer.setWidth("640px");
		maskPanel.add(headPanel);
		
		panel.add(maskPanel);
		panel.setWidth("100%");

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

		RootPanel.get("root").add(panel);

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

	private void showInfo() {
		show(list.find("Home"), false);
	}
}
