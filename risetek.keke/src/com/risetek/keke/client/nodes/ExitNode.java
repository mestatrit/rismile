package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;

public class ExitNode extends VNode {

	public ExitNode() {
		super("ExitNode", "");
	}

	@Override
	public Composite getComposite() {
		GWT.log("Fatal: Named Node do not have composite");
		return null;
	}

	public int enter(AWidget widget) {
		super.enter(widget);
		return NODE_EXIT;
	}
	
}
