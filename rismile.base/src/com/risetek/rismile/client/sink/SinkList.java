package com.risetek.rismile.client.sink;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.sink.Sink.SinkInfo;

/**
 * The top panel that contains all of the sinks, along with a short description
 * of each.
 */
public class SinkList extends Composite {
	
	public interface Images extends ClientBundle {
		public static final Images INSTANCE = GWT.create(Images.class);
		@Source("gwtLogo.jpg")		ImageResource  gwtLogo();
		@Source("tongfaLogo.jpg")	ImageResource  tongfaLogo();
	}
	
	
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

	private VerticalPanel list = new VerticalPanel();
	private ArrayList<SinkInfo> sinks = new ArrayList<SinkInfo>();

	private int selectedSink = 0;

	public SinkList() {
		initWidget(list);
		//list.add(image);
//		if( Entry.OEMFlag == Entry.OEM.risetek )
//			list.add(new Image(Images.INSTANCE.gwtLogo()));
//		else
//			list.add(new Image(Images.INSTANCE.tongfaLogo()));
		// setStyleName("rismile");
		list.setStyleName("SinkList");
	}
/*
	public void addExternalLink(Widget w) {
		w.setStyleName("ks-External");
		list.add(w);
		list.setCellVerticalAlignment(w, HorizontalPanel.ALIGN_BOTTOM);
	}
	*/
	public void addSink(final SinkInfo info) {
		String name = info.getName();
		int index = list.getWidgetCount();

		MouseLink link = new MouseLink(name, info.getTag(), index);
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

//	private void colorSink(int index, boolean on) {
//		String color = "";
//		if (on) {
//			color = "#6694E3";
//		}
//
//		Widget w = list.getWidget(index);
//		DOM.setStyleAttribute(w.getElement(), "backgroundColor", color);
//	}

	private void mouseOut(int index) {
		if (index != selectedSink) {
//			colorSink(index, false);
			Widget w = list.getWidget(index);
			w.setStyleName("ks-SinkItem");
		}
	}

	private void mouseOver(int index) {
		if (index != selectedSink) {
//			colorSink(index, true);
			Widget w = list.getWidget(index);
			w.setStyleName("ks-SinkItem-show");
			DOM.setStyleAttribute(w.getElement(), "color", "#FFFFFF");
		}
	}

	private void styleSink(int index, boolean selected) {
		String style = "ks-SinkItem";
		if (selected) {
			style += "-selected";
		}

		Widget w = list.getWidget(index);
		w.setStyleName(style);

//		colorSink(index, selected);
	}
}
