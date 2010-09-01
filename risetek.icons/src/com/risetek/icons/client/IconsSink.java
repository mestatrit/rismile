package com.risetek.icons.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.risetek.icons.client.ui.IconList;
import com.risetek.icons.client.ui.Icons;

import libfeeder.client.sink.Sink;

public class IconsSink extends Sink {
	public static final String Tag = "Icons";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "Icons", "Icons") {
			@Override
			public Sink createInstance() {
				return new IconsSink();
			}
		};
	}
	
	private void showPanel(Composite newOne ) {
		if( curPanel != null ) {
			curPanel.setVisible(false);
			outer.remove(curPanel);
		}
		curPanel = newOne;
		outer.add(curPanel);
		curPanel.setSize("100%", "100%");
		curPanel.setVisible(true);
	}

	Icons icon = new Icons();
	private Composite curPanel = null;
	IconList iconlist = new IconList();
	private final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
	private final Button Toggle = new Button("Press to Change View", new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			if( curPanel == icon )
				showPanel(iconlist);
			else
				showPanel(icon);
		}});

	
	public IconsSink() {
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.addNorth(Toggle, 40);
		showPanel(iconlist);
		initWidget(outer);
	}
}
