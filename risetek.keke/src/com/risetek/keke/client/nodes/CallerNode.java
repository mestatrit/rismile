package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class CallerNode extends Stick {

	String calledSticklet = null;
	
	public CallerNode(String calledSticklet) {
		super("调用["+calledSticklet+"]","20090218213222605");
		this.calledSticklet = calledSticklet;
	}
	
	@Override
	public int enter(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		if( sticklet.calledSticklet != null ) {
			D3Context.Log("Fatal: calledSticklet is not null");
			return Stick.NODE_CANCEL;
		}
			
		int state = super.enter(context);
		// 陷入被调用环境中去。
		D3Context.Log("Call: "+calledSticklet);
		Sticklet called = Sticklets.loadSticklet(calledSticklet);
		if( sticklet.Call(called, context) != NODE_STAY )
			D3Context.Log("Fatal: Called sticklet can't stay.");
		return state;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	@Override
	public int action(D3Context context) {
		return super.action(context);
	}

	@Override
	public int rollback(D3Context context) {
		return super.rollback(context);
	}
	
	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
	@Override
	public int failed(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		Stick n = sticklet.HistoryNodesStack.pop();
		return n.rollback(context);
	}
}
