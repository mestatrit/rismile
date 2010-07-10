package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;

public class ExitNode extends Stick {

	public ExitNode() {
		super("退出程序", "Exit");
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

	@Override
	public int action(D3Context context) {
		super.action(context);
		return NODE_EXIT;
	}
}
