package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */

// TODO:
// 命名节点应该注册到一个Hash表中，以备被按名调用。

public class NamedNode extends VNode {

	public NamedNode(String name) {
		super("Named", "");
		GWT.log("Create named Node: "+name);
	}

}
