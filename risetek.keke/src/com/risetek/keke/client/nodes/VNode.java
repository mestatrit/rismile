package com.risetek.keke.client.nodes;

public abstract class VNode extends Node {

	public VNode(String ticker, String promotion) {
		super(ticker, promotion);
	}

	public VNode(String ticker, String promotion, String imgName) {
		super(ticker, promotion, imgName);
	}
	
	// 虚节点是不能回滚的。
	// 是否能记录历史：
	public boolean rollbackable() {
		return false;
	}
	
}
