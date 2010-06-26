package com.risetek.keke.client.nodes;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class NamedNode extends Node {

	public NamedNode(String ticker, String promotion) {
		super(ticker, "NamedTicker");
	}

	@Override
	public int engage() {
		if( children != null )
			return 1;
		return 0;
	}
}
