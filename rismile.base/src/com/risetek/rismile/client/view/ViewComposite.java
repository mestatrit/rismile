package com.risetek.rismile.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.conf.UIConfig;

public class ViewComposite extends DockLayoutPanel {

	public ViewComposite() {
		super(Unit.PX);
		setHeight(Entry.SinkHeight);
		setWidth("100%");
	}
	
	public void addLeftSide(Widget widget) {
		addWest(widget, UIConfig.LeftSiderWidth);
		widget.setSize("100%", "100%");
	}
	public void addRightSide(Widget widget) {
		addEast(widget, UIConfig.RightSiderWidth);
		widget.setSize("100%", "100%");
		widget.getElement().getStyle().setBackgroundColor(UIConfig.OuterLine);
		widget.getElement().getStyle().setColor("#E8EEF7");
		
	}
}

