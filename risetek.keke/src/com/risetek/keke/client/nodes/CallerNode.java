package com.risetek.keke.client.nodes;

import com.risetek.keke.client.sticklet.ASticklet;

public class CallerNode extends VNode {
	String calledName;
	public CallerNode(String calledName) {
		super("CallerNode", "");
		this.calledName = calledName;
	}

	public int enter(ASticklet sticklet) {
		
		// 取得被调用的执行树。挂接到本节点？
		Node n = Node.namedNodesHash.get(calledName);
		if( n != null )
			this.addChildrenNode(n);

		super.enter(sticklet);
//		sticklet.control(ASticklet.STICKLET_ENGAGE);
		return 0;
	}
	
}
