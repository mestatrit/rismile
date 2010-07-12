package com.risetek.keke.client.ui;

import java.util.Vector;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoggerComposite extends Composite {
	VerticalPanel outPanel = new VerticalPanel();
	VerticalPanel	logger = new VerticalPanel();
	ScrollPanel		scroll = new ScrollPanel();
	
	Vector<String> logItems = new Vector<String>(); 
	
	public void Log(String message) {
		logger.add(new HTML(message));
		scroll.scrollToBottom();
	}

	
	public LoggerComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		outPanel.setBorderWidth(1);
		
		logger.setWidth("100%");
		outPanel.add(scroll);
		scroll.setSize("100%", "100%");
		scroll.add(logger);
		initWidget(outPanel);
	}
}
