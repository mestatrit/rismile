package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class SecurityCheckNode extends VNode {

	public SecurityCheckNode() {
		super("安全检测");
	}
	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.sticklet.ASticklet)
	 * 我们检查安全问题。如果没有登录，那么需要调用登录sticklet。
	 */
	boolean isSecurity() {
		if( PosContext.Token == null )
			return false;
		else
			return true;
	}

	public int enter(ASticklet sticklet) {
		// 陷入被调用环境中去。
		if( !isSecurity() ) {
			ASticklet login = Sticklets.loadSticklet("epay.local.login");
			return sticklet.Call(login);
		}
		return super.enter(sticklet);
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet sticklet) {
		return super.action(sticklet);
	}
	
}
