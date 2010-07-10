package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.D3Context;

public class CancelNode extends PromotionNode {

	public CancelNode(String promotion) {
		super(promotion, "Cancel");
	}

	// 我们离开这个节点进入下一步的时候，执行该动作。
	@Override
	public int action(D3Context context) {
		super.action(context);
		return NODE_CANCEL;
	}
	
}
