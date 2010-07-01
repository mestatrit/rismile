package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class CallerNode extends Node {

	String calledSticklet = null;
	
	public CallerNode(String calledSticklet) {
		super("Caller", "系统调用");
		this.calledSticklet = calledSticklet;
	}
	
	public int enter(ASticklet sticklet) {
		GWT.log("CallerNode");
		if( sticklet.calledSticklet == null )
		{
			super.enter(sticklet);
			// 陷入被调用环境中去。
			GWT.log("Call: "+calledSticklet);
			ASticklet called = Sticklets.loadSticklet(calledSticklet);
			sticklet.Call(called);
		}
		else
			sticklet.control(ASticklet.STICKLET_ENGAGE);
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet sticklet) {
		return 0;
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
}
