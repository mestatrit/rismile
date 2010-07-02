package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class LoaderNode extends VNode {

	String loadWidgetName;
	
	public LoaderNode(String newWidget) {
		super("LoaderNode", "");
		loadWidgetName = newWidget;
	}

	@Override
	public Composite getComposite() {
		PosContext.Log("Fatal: Named Node do not have composite");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 
	 * 这个节点的功用是载入新的widget并告诉系统去执行新的widget。
	 * 
	 */
	public int enter(ASticklet widget) {
		super.enter(widget);
		
		return 0;
	}

}
