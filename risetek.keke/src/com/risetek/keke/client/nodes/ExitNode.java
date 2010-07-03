package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;

public class ExitNode extends Node {

	public ExitNode() {
		super("退出程序", "20090218213218568");
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

	public int enter(ASticklet widget) {
		return super.enter(widget);
	}
	
	public int action(ASticklet widget) {
		super.action(widget);
		return NODE_EXIT;
	}
}
