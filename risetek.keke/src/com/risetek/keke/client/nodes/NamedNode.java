package com.risetek.keke.client.nodes;

import com.risetek.keke.client.data.AWidget;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class NamedNode extends Node {

	public NamedNode(String ticker, String promotion) {
		super(ticker, "NamedTicker");
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 
	 * 作为一个虚节点，我们跳过这一步，进入下一步。
	 */
			
	public int enter(AWidget widget) {
		super.enter(widget);
		children.enter(widget);
		return 0;
	}
	
}
