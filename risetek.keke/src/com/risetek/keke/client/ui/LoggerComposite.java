package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggerComposite extends Composite {
	VerticalPanel d = new VerticalPanel();
	public ListBox	logger = new ListBox();
	public LoggerComposite() {
		d.setWidth("100%");
		d.setHeight("100%");
		logger.setWidth("100%");
		logger.setHeight("320px");
		logger.addItem("Line one");
		logger.addItem("Line tow");
		logger.addItem("Line three");
		logger.setVisibleItemCount(20);
		logger.setEnabled(false);
		d.add(logger);
		initWidget(d);
	}
}
