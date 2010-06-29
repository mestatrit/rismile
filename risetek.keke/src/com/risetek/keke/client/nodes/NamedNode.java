package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class NamedNode extends Node {

	String name;
	public NamedNode(String name) {
		super("Named", "NamedNode");
		this.name = name;
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

	@Override
	public Composite getComposite() {
		GWT.log("Fatal: Named Node do not have composite");
		return null;
	}
	
}
