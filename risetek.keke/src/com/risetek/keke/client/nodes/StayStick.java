package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.D3Context;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class StayStick extends PromotionNode {

	public StayStick(String promotion, String imgName) {
		super(promotion, imgName);
	}

	// 我们离开这个节点进入下一步的时候，执行该动作。
	// 告诉控制器，我们节点不转移到下一个节点去，仍然
	// 停留在这个节点上。这对于仅仅显示给用户看信息的
	// 节点是有用的，不会对流程产生冲突。
	@Override
	public int action(D3Context context) {
		super.action(context);
		return NODE_STAY;
	}

}
