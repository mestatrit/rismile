package com.risetek.icons.client.ui;

import libfeeder.client.sink.Sink;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class TreeUpSink extends Sink {
	public static final String Tag = "TREEUPICONS";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "丢失图标上传", "图标上传") {
			@Override
			public Sink createInstance() {
				return new TreeUpSink();
			}
		};
	}

	public TreeUpSink() {
		TreeUpIcons icon = new TreeUpIcons();
		final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.add(icon);
		initWidget(outer);
	}

}
