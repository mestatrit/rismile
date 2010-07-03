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
		if( sticklet.calledSticklet == null )
		{
			super.enter(sticklet);
			// 陷入被调用环境中去。
			PosContext.Log("Call: "+calledSticklet);
			ASticklet called = Sticklets.loadSticklet(calledSticklet);
			return sticklet.Call(called);
		}
		else
		{
			PosContext.Log("Caller engage.");
			return sticklet.control(ASticklet.STICKLET_ENGAGE);
		}
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet sticklet) {
		return super.action(sticklet);
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
}
