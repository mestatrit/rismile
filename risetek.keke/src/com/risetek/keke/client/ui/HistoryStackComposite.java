package com.risetek.keke.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.keke.client.nodes.Stick;

public class HistoryStackComposite extends Composite {
	private final VerticalPanel outPanel = new VerticalPanel();
	private final ListBox	logger = new ListBox();
	public void ShowHistoryStack(Stack<Stick> caller) {
		logger.clear();
		for(int loop = 0; loop < caller.size(); loop++) {
			logger.addItem(caller.elementAt(loop).Promotion);
		}
	}
	public HistoryStackComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		logger.setWidth("100%");
		logger.setHeight("100%");
		outPanel.add(logger);
		logger.setVisibleItemCount(20);
		logger.setEnabled(false);
		initWidget(outPanel);
	}
}
