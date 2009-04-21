package com.risetek.scada.client.sink;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.scada.client.sink.Sink.SinkInfo;

/**
 * The top panel that contains all of the sinks, along with a short description
 * of each.
 */
public class SinkList extends Composite {
	
	private HorizontalPanel list = new HorizontalPanel();
	private ArrayList<SinkInfo> sinks = new ArrayList<SinkInfo>();

	private int selectedSink = -1;

	public SinkList() {
		list.setBorderWidth(1);
		initWidget(list);
	}

	public void addSink(final SinkInfo info) {
		String name = info.getName();
		int index = list.getWidgetCount();

		Hyperlink link = new Hyperlink(name, info.getTag());
		list.add(link);
		info.link_index = index;
		sinks.add(info);

		list.setCellVerticalAlignment(link, HorizontalPanel.ALIGN_BOTTOM);
		styleSink(index, false);
	}

	public void removeSink(final SinkInfo info) {
		list.remove(info.link_index);
		sinks.remove(info);
	}

	public SinkInfo find(String sinkName) {
		for (int i = 0; i < sinks.size(); ++i) {
			SinkInfo info = sinks.get(i);
			if (info.getTag().equals(sinkName)) {
				return info;
			}
		}
		return null;
	}

	public void setSinkSelection(String name) {
		if (selectedSink != -1) {
			styleSink(selectedSink, false);
		}

		for (int i = 0; i < sinks.size(); ++i) {
			SinkInfo info = sinks.get(i);
			if (info.getName().equals(name)) {
				selectedSink = i;
				styleSink(selectedSink, true);
				return;
			}
		}
	}

	private void colorSink(int index, boolean on) {
		String color = "";
		if (on) {
			color = "#2a8ebf";
		}

		Widget w = list.getWidget(index);
		DOM.setStyleAttribute(w.getElement(), "backgroundColor", color);
	}

	private void styleSink(int index, boolean selected) {
		String style = (index == 0) ? "ks-FirstSinkItem" : "ks-SinkItem";
		if (selected) {
			style += "-selected";
		}

		Widget w = list.getWidget(index);
		w.setStyleName(style);

		colorSink(index, selected);
	}
}
