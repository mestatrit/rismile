package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class LogoutNode extends PromotionNode {

	public LogoutNode() {
		super("注销", "Logout");
	}

	public int action(ASticklet widget) {
		PosContext.system.remove("token");
		return super.action(widget);
	}

	// DEBUG only
	public int enter(ASticklet sticklet) {
		return super.enter(sticklet);
	}	

	public int rollback(ASticklet sticklet) {
		super.rollback(sticklet);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ROLLBACK));
		return NODE_CANCEL;
	}
}
