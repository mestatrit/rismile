package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;

public abstract class VStick extends Stick {

	public VStick(String promotion) {
		super(promotion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.sticklet.ASticklet)
	 * 作为一个虚节点，我们跳过这一步，进入下一步。
	 */

	@Override
	public int enter(D3Context context) {
		int state = super.enter(context);
		// 作为一个虚节点，我们跳过这一步，进入下一步。
		// 这个进步会促使action动作。
		ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ENGAGE);
		return state;
	}
	
	@Override
	public int rollback(D3Context context) {
		int state = super.rollback(context);
		//作为一个虚节点，我们不能停留在这个节点，必须再rollback。
		ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ROLLBACK);
//		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE));
		return state;
	}
	
	// 我们假定虚拟节点还是有绘制能力，这也许能过渡一下。
	@Override
	public Composite getComposite() {
		/*
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
		*/
		return null;
	}
}
