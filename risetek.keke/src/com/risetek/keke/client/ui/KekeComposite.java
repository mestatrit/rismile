package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.PosEvents.PosEvent;
import com.risetek.keke.client.context.UiKeke;

public class KekeComposite extends Composite {
	
	Label brief = new Label();
	Image img;
	HTMLPanel outPanel = new HTMLPanel("");
	Grid g = new Grid(2,1);
	public PosEvent event;

	public KekeComposite(String title, String imgName, String eventName) {
		brief.setText(title);
		outPanel.add(g);
		g.setWidget(0, 0, img);
		g.setWidget(1, 0, brief);
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		initWidget(outPanel);
	}
}
