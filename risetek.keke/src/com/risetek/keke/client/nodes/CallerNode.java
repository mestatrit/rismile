package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class CallerNode extends Node {

	String calledSticklet = null;
	
	public CallerNode(String calledSticklet) {
		super("调用["+calledSticklet+"]","20090218213222605");
		this.calledSticklet = calledSticklet;
	}
	
	public int enter(ASticklet sticklet) {
		if( sticklet.calledSticklet != null ) {
			PosContext.Log("Fatal: calledSticklet is not null");
			return Node.NODE_CANCEL;
		}
			
		int state = super.enter(sticklet);
		// 陷入被调用环境中去。
		PosContext.Log("Call: "+calledSticklet);
		ASticklet called = Sticklets.loadSticklet(calledSticklet);
		if( sticklet.Call(called) != NODE_STAY )
			PosContext.Log("Fatal: Called sticklet can't stay.");
		return state;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet sticklet) {
		return super.action(sticklet);
	}

	public int rollback(ASticklet sticklet) {
		return super.rollback(sticklet);
	}
	
	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
	public int failed(ASticklet sticklet) {
		Node n = sticklet.HistoryNodesStack.pop();
		return n.rollback(sticklet);
	}
}
