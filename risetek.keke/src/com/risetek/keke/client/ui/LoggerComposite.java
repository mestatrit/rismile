package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggerComposite extends Composite {
	VerticalPanel outPanel = new VerticalPanel();
	public ListBox	logger = new ListBox();
	public LoggerComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		logger.setWidth("100%");
		logger.setHeight("100%");
		// 这个值会影响界面，主要是布局比例。
		logger.setVisibleItemCount(2);
		logger.setEnabled(false);
		outPanel.add(logger);
		initWidget(outPanel);
	}
}
