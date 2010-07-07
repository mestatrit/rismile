package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class SecurityCheckNode extends VStick {

	public SecurityCheckNode() {
		super("安全检测");
	}
	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.sticklet.ASticklet)
	 * 我们检查安全问题。如果没有登录，那么需要调用登录sticklet。
	 */
	boolean isSecurity() {
//		if( PosContext.Token == null )
		if( D3Context.system.get("token") == null )
			return false;
		else
			return true;
	}

	// 我们离开这个节点进入下一步的时候，执行该动作。
	@Override
	public int action(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		// 陷入被调用环境中去。
		if( !isSecurity() ) {
			Sticklet login = Sticklets.loadSticklet("epay.local.login");
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(login));
			return NODE_STAY;
		}
		return super.action(context);
	}
	
	@Override
	public int failed(D3Context context) {
		Stick n = context.getSticklet().HistoryNodesStack.pop();
		return n.failed(context);
	}
}
