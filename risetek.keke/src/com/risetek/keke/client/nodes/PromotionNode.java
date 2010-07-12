package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PromotionNode extends Stick {
	
	public PromotionNode(String promotion, String imgName) {
		super(promotion, imgName);
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

	@Override
	public void onShow(D3Context context) {
		String Descript = StickParams.get("Descript");
		if( Descript != null )
		context.showTips(Descript);
	}
	
	@Override
	public void onHide(D3Context context) {
		context.hideTips();
	}
	
	
}
