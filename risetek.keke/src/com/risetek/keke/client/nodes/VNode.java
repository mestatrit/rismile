package com.risetek.keke.client.nodes;

public abstract class VNode extends Node {

	public VNode(String ticker, String promotion) {
		super(ticker, promotion);
	}

	public VNode(String ticker, String promotion, String imgName) {
		super(ticker, promotion, imgName);
	}
}
