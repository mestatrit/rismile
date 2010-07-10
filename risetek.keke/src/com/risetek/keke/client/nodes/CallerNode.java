package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;


public class CallerNode extends Stick {

	private final String called;
	
	public CallerNode(String called) {
		super("调用["+called+"]","Caller");
		this.called = called;
	}
	
	@Override
	public int enter(D3Context context) {
		int state = super.enter(context);
		// 陷入被调用环境中去。
		D3Context.Log("Call: "+called);
		Sticklet sticklet = Sticklets.loadSticklet(called);
		D3Context.CallSticklet(sticklet);
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
