package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ControlPanel extends Composite {
	
	private final static VerticalPanel controlPanel = new VerticalPanel();
	
	public ControlPanel() {
		initWidget(controlPanel);
		controlPanel.add(new HTML("测试"));
	}
}
