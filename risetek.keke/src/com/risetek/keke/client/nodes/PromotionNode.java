package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PromotionNode extends Stick {

	public PromotionNode(String promotion, String imgName) {
		super(promotion, imgName);
	}

	// DEBUG only
	public int enter(ASticklet sticklet) {
		return super.enter(sticklet);
	}	
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		return super.action(widget);
	}
	
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

}
