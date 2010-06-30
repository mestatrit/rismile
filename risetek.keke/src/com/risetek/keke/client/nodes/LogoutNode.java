package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class LogoutNode extends PromotionNode {

	public LogoutNode() {
		super("LogoutNode", "注销");
	}

	public int action(ASticklet widget) {
		PosContext.Token = null;
		return 0;
	}
}
