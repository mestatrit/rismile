package com.risetek.keke.client.nodes;

import com.risetek.keke.client.sticklet.ASticklet;

public class CancelNode extends PromotionNode {

	public CancelNode(String promotion, String imgName) {
		super(promotion, imgName);
	}

	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		super.action(widget);
		return NODE_CANCEL;
	}
	
}
