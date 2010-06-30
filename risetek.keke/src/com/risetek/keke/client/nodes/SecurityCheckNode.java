package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.LoginSticklet;


public class SecurityCheckNode extends VNode {

	public SecurityCheckNode() {
		super("SecurityCheck", "");
	}
	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.sticklet.ASticklet)
	 * 我们检查安全问题。如果没有登录，那么需要调用登录sticklet。
	 */
	boolean isSecurity() {
		return false;
	}
	
	public int enter(ASticklet sticklet) {
		GWT.log("SecurityCheckNode");
		//Node n = Node.namedNodesHash.get(calledName);
		// 陷入被调用环境中去。
		if( isSecurity() ) {
			// TODO: 需节点无法停留在当前节点，咋办呢？
			super.enter(sticklet);
		}
		else
		{
			ASticklet login = new LoginSticklet();
			sticklet.Call(login);
		}
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		return 0;
	}
	
}
