package com.risetek.icons.client.ui;

import libfeeder.client.sink.Sink;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class IconUpSink extends Sink {
	public static final String Tag = "UPICONS";
	
	public static SinkInfo init() {
		return new SinkInfo(Tag, "UPICONS", "�ϴ�ͼ�깦����") {
			@Override
			public Sink createInstance() {
				return new IconUpSink();
			}
		};
	}

	public IconUpSink() {
		Icons icon = new Icons();
		final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.add(icon);
		initWidget(outer);
	}
}
