package com.risetek.keke.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.keke.client.sticklet.Sticklet;

public class CallerStackComposite extends Composite {
	
	private final VerticalPanel outPanel = new VerticalPanel();
	private final VerticalPanel	logger = new VerticalPanel();
	public void ShowStack(Stack<Sticklet> caller) {
		logger.clear();
		for(int loop = 0; loop < caller.size(); loop++) {
			logger.add(new HTML(caller.elementAt(loop).aStickletName+"["+caller.elementAt(loop).getCurrentNode().getClassName()
					+":"+caller.elementAt(loop).getCurrentNode().Promotion+"]"));
		}
	}
	public CallerStackComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		outPanel.setBorderWidth(1);
		logger.setWidth("100%");
		outPanel.add(logger);
		initWidget(outPanel);
	}
	
}
