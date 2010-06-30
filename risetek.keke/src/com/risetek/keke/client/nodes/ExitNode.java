package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;

public class ExitNode extends VNode {

	public ExitNode() {
		super("ExitNode", "退出中...", "ExitIcon");
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

	public int enter(ASticklet widget) {
		super.enter(widget);
		return 0;
	}
	
	public int action(ASticklet widget) {
		return NODE_EXIT;
	}
}
