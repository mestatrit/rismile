package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.events.PosEvent;

public class KekeComposite extends Composite {
	
	Label brief = new Label();
	Image img;
	HTMLPanel outPanel = new HTMLPanel("");
	public PosEvent event;

	public KekeComposite(String title, String imgName, PosEvent event) {
		this.event = event;
		brief.setText(title);
		outPanel.add(brief);
		initWidget(outPanel);
	}
}
