package com.risetek.keke.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.keke.client.nodes.Stick;

public class HistoryStackComposite extends Composite {
	private final VerticalPanel outPanel = new VerticalPanel();
	private final VerticalPanel	logger = new VerticalPanel();
	public void ShowHistoryStack(Stack<Stick> caller) {
		logger.clear();
		for(int loop = 0; loop < caller.size(); loop++) {
			logger.add(new HTML(caller.elementAt(loop).Promotion));
		}
	}
	public HistoryStackComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		outPanel.setBorderWidth(1);
		logger.setWidth("100%");
		outPanel.add(logger);
		initWidget(outPanel);
	}
}
