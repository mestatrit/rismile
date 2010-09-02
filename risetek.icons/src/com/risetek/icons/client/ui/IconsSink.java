package com.risetek.icons.client.ui;

import libfeeder.client.sink.Sink;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class IconsSink extends Sink {
	public static final String Tag = "ICONSLIST";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "图标管理", "图标管理") {
			@Override
			public Sink createInstance() {
				return new IconsSink();
			}
		};
	}
	
	public IconsSink() {
		IconList iconlist = new IconList();
		final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.add(iconlist);
		initWidget(outer);
	}
}
