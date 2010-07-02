package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggerComposite extends Composite {
	VerticalPanel d = new VerticalPanel();
	TextArea	logger = new TextArea();
	public LoggerComposite() {
		d.setWidth("100%");
		d.setHeight("100%");
		logger.setWidth("100%");
		logger.setHeight("320px");
		logger.setReadOnly(true);
		logger.setText("this is");
		logger.setText("line one");
		logger.setText("line tow");
		d.add(logger);
		initWidget(d);
	}
}
