package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;

public abstract class VNode extends Node {

	public VNode(String promotion) {
		super(promotion);
	}

	// 虚节点是不能回滚的。
	// 是否能记录历史：
	public boolean rollbackable() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.sticklet.ASticklet)
	 * 作为一个虚节点，我们跳过这一步，进入下一步。
	 */

	public int enter(ASticklet sticklet) {
		super.enter(sticklet);
		//作为一个虚节点，我们跳过这一步，进入下一步。
		return sticklet.control(ASticklet.STICKLET_ENGAGE);
	}
	
	public int rollback(ASticklet sticklet) {
		super.rollback(sticklet);
		//作为一个虚节点，我们不能停留在这个节点，必须再进步。
		return sticklet.control(ASticklet.STICKLET_ENGAGE);
	}
	
	// 我们假定虚拟节点还是有绘制能力，这也许能过渡一下。
	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
}
