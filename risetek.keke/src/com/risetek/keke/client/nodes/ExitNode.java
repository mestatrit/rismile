package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.PromotionComposite;

public class ExitNode extends VNode {

	public ExitNode() {
		super("ExitNode", "");
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

	public int enter(AWidget widget) {
		super.enter(widget);
		return NODE_EXIT;
	}
	
}
