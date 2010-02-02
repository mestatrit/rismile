package com.risetek.scada.client.sink;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.scada.client.sink.Sink.SinkInfo;

/**
 * The top panel that contains all of the sinks, along with a short description
 * of each.
 */
public class SinkList extends Composite {
	
	private VerticalPanel list = new VerticalPanel();
	private ArrayList<SinkInfo> sinks = new ArrayList<SinkInfo>();

	private int selectedSink = -1;

	public SinkList() {
		list.setWidth("100%");
		initWidget(list);
	}

	public void addSink(final SinkInfo info) {
		String name = info.getName();
		int index = list.getWidgetCount();

		Hyperlink link = new Hyperlink(name, info.getTag());
		list.add(link);
		info.link_index = index;
		sinks.add(info);

		list.setCellVerticalAlignment(link, VerticalPanel.ALIGN_MIDDLE);
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
		Widget w = list.getWidget(index);
//		String color = "";
		if (on) {
//			color = "#E0ECFF";
//			color = "#C3D9FF";
			w.setStyleName("listbackground");
		}else {
			w.setStyleName("");
		}
		if(index==0){
			if(w.getParent().getParent().getParent().getParent() instanceof Grid){
				Grid headPanel = (Grid)w.getParent().getParent().getParent().getParent();
				DecoratorPanel sinkContainerOut = (DecoratorPanel)headPanel.getWidget(0, 1);
				Element element0 = sinkContainerOut.getElement();
				Element element1 = DOM.getChild(element0, 0);
				Element element2 = DOM.getChild(element1, 0);
				Element element3 = DOM.getChild(element2, 0);
				DOM.getStyleAttribute(element3, "background");
				DOM.setStyleAttribute(element3, "background", "url(image/corner.jpg)");
			}
		} else {
			if(w.getParent().getParent().getParent().getParent() instanceof Grid){
				Grid headPanel = (Grid)w.getParent().getParent().getParent().getParent();
				DecoratorPanel sinkContainerOut = (DecoratorPanel)headPanel.getWidget(0, 1);
				Element element0 = sinkContainerOut.getElement();
				Element element1 = DOM.getChild(element0, 0);
				Element element2 = DOM.getChild(element1, 0);
				Element element3 = DOM.getChild(element2, 0);
				DOM.getStyleAttribute(element3, "background");
				DOM.setStyleAttribute(element3, "background", "url(image/corner.png) no-repeat 0px 0px");
			}
		}
//		DOM.setStyleAttribute(w.getElement(), "backgroundColor", color);
	}

	private void styleSink(int index, boolean selected) {
		colorSink(index, selected);
	}
}
