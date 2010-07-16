package com.risetek.rismile.client.sink;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.sink.Sink.SinkInfo;

/**
 * The top panel that contains all of the sinks, along with a short description
 * of each.
 */
public class SinkList extends Composite {
	
	private class MouseLink extends Hyperlink {

		private int index;

		public MouseLink(String name, String tag, int index) {
			super(name, tag);
			this.index = index;
			sinkEvents(Event.MOUSEEVENTS);
		}

		public void onBrowserEvent(Event event) {
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEOVER:
				mouseOver(index);
				break;

			case Event.ONMOUSEOUT:
				mouseOut(index);
				break;
			}

			super.onBrowserEvent(event);
		}
	}

	private HorizontalPanel list = new HorizontalPanel();
	public ArrayList<SinkInfo> sinks = new ArrayList<SinkInfo>();

	private int selectedSink = -1;

	public SinkList(Image image) {
		initWidget(list);
		list.add(image);
		setStyleName("rismile");
		list.setStyleName("SinkList");
	}

	public void addExternalLink(Widget w) {
		w.setStyleName("ks-External");
		list.add(w);
		list.setCellVerticalAlignment(w, HorizontalPanel.ALIGN_BOTTOM);
	}
	
	public void addSink(final SinkInfo info) {
		String name = info.getName();
		int index = list.getWidgetCount() - 1;

		MouseLink link = new MouseLink(name, info.getTag(), index);
		list.add(link);
		info.link_index = index + 1;
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
	
	public SinkInfo getSinkSelection(){
		if(selectedSink != -1){
			return sinks.get(selectedSink);
		} else {
			return sinks.get(0);
		}
	}
	

	private void colorSink(int index, boolean on) {
		String color = "";
		if (on) {
			color = "#2a8ebf";
		}

		Widget w = list.getWidget(index + 1);
		DOM.setStyleAttribute(w.getElement(), "backgroundColor", color);
	}

	private void mouseOut(int index) {
		if (index != selectedSink) {
			colorSink(index, false);
		}
	}

	private void mouseOver(int index) {
		if (index != selectedSink) {
			colorSink(index, true);
		}
	}

	private void styleSink(int index, boolean selected) {
		String style = (index == 0) ? "ks-FirstSinkItem" : "ks-SinkItem";
		if (selected) {
			style += "-selected";
		}

		Widget w = list.getWidget(index + 1);
		w.setStyleName(style);

		colorSink(index, selected);
	}
	
	// TODO: FIXME: urgly
	public void addWidget(Widget w){
		list.add(w);
	}

}
