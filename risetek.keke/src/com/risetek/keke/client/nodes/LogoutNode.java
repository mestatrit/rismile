package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;

public class LogoutNode extends PromotionNode {

	public LogoutNode() {
		super("注销", "Logout");
	}

	@Override
	public int action(D3Context context) {
		D3Context.system.remove("token");
		return super.action(context);
	}

	@Override
	public int rollback(D3Context context) {
		super.rollback(context);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ROLLBACK));
		return NODE_CANCEL;
	}
}
