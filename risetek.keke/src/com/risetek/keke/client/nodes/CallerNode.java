package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.PromotionComposite;

public class CallerNode extends VNode {
	String calledName;
	public CallerNode(String calledName) {
		super("CallerNode", "");
		this.calledName = calledName;
	}

	public int enter(AWidget widget) {
		super.enter(widget);
		// 取得被调用的执行树。挂接到本节点？
		Node n = this.namedNodesHash.get(calledName);
		if( n != null )
			this.addChildrenNode(n);

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
