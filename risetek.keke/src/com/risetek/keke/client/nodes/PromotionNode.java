package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PromotionNode extends Node {

	public PromotionNode(String promotion, String imgName) {
		super("Promotion", promotion, imgName);
	}

	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}

}
