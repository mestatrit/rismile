package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class LogoutNode extends PromotionNode {

	public LogoutNode() {
		super("注销", "Logout");
	}

	public int action(ASticklet widget) {
		PosContext.Token = null;
		super.action(widget);
		return 0;
	}

	// DEBUG only
	public int enter(ASticklet sticklet) {
		return super.enter(sticklet);
	}	

}
