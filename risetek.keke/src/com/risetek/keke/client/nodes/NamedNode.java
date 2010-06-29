package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */

// TODO:
// 命名节点应该注册到一个Hash表中，以备被按名调用。

public class NamedNode extends VNode {

	public NamedNode(String name) {
		super("Named", "NamedNode");
		GWT.log("Create named Node: "+name);
		namedNodesHash.put(name, this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 
	 * 作为一个虚节点，我们跳过这一步，进入下一步。
	 */
			
	public int enter(AWidget widget) {
		super.enter(widget);
		widget.control(AWidget.WIDGET_ENGAGE);
		return 0;
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
}
