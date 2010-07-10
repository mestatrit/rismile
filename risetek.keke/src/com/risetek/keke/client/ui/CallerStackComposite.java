package com.risetek.keke.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.keke.client.sticklet.Sticklet;

public class CallerStackComposite extends Composite {
	private final VerticalPanel outPanel = new VerticalPanel();
	private final ListBox	logger = new ListBox();
	public void ShowStack(Stack<Sticklet> caller) {
		logger.clear();
		for(int loop = 0; loop < caller.size(); loop++) {
			logger.addItem(caller.elementAt(loop).aStickletName+"["+caller.elementAt(loop).getCurrentNode().getClassName()
					+":"+caller.elementAt(loop).getCurrentNode().Promotion+"]");
		}
	}
	public CallerStackComposite() {
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");
		logger.setWidth("100%");
		logger.setHeight("100%");
		outPanel.add(logger);
		logger.setVisibleItemCount(2);
		logger.setEnabled(false);
		initWidget(outPanel);
	}
}
