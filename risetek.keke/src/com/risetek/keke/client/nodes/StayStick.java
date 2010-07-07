package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class StayStick extends Stick {

	public StayStick(String promotion, String imgName) {
		super(promotion, imgName);
	}

	// 我们离开这个节点进入下一步的时候，执行该动作。
	@Override
	public int action(D3Context context) {
		super.action(context);
		return NODE_STAY;
	}
	
	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

}
