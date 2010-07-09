package com.risetek.keke.client.ui;

import java.util.Stack;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.risetek.keke.client.sticklet.Sticklet;

public class CallerStackComposite extends Composite {
	private final DockPanel outPanel = new DockPanel();
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
		outPanel.add(logger, DockPanel.NORTH);
		logger.setVisibleItemCount(20);
		logger.setEnabled(false);
		initWidget(outPanel);
	}
}
