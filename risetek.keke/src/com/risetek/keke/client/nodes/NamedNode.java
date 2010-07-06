package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 * 这个节点的特殊性在于其总是位于第一的位置。是标志一个sticklet的节点。
 * 
 */

// TODO:
// 命名节点应该注册到一个Hash表中，以备被按名调用。

public class NamedNode extends VStick {

	public NamedNode(String name) {
		super(name);
		PosContext.Log("Create named Node: "+name);
	}

	@Override
	public Composite getComposite() {
		return null;
	}

	public int failed(ASticklet sticklet) {
		super.failed(sticklet);
		if( sticklet.callerSticklet != null ) {
			sticklet.callerSticklet.calledSticklet.Clean();
			sticklet.callerSticklet.calledSticklet = null;
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL));
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		}
		return NODE_STAY;
	}

}
