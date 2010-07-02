package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.sticklet.ASticklet;

/*
 * 这个结构用来表达一系列串联的节点。并能存储到数据库中。这是一种变异了的树形结构。
 */

public abstract class Node {
	
	public final static int NODE_OK	= 0;
	public final static int NODE_EXIT	= -1;
	
	private Node children;
	public Node next;
	public String Ticker;
	public String Promotion;
	public String imgName;

	Composite composite = null;
	
	public Node getChildren() {
		return children;
	}
	
	public abstract Composite getComposite();

	public Node(String ticker, String promotion) {
		Ticker = ticker;
		Promotion = promotion;
		imgName = "p2";
	}

	public Node(String ticker, String promotion, String imgName) {
		Ticker = ticker;
		Promotion = promotion;
		this.imgName = imgName;
	}
	
	/*
	 * 链接一个节点到本节点的子孙
	 */
	public Node addChildrenNode(Node node) {
		if( children == null )
			children = node;
		else
			children.addNextNode(node);
		return node;
	}
	
	/*
	 * 链接一个弟兄到本节点
	 */
	
	private void addNextNode(Node node) {
		if( next == null )
			next = node;
		else
			next.addNextNode(node);
	}

	public int enter(ASticklet sticklet) {
		if( sticklet.getCurrentNode() != null )
			sticklet.getCurrentNode().leave(sticklet);
		sticklet.setCurrentNode(this);
		
		if( getComposite() != null )
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		return 0;
	}
	
	public int leave(ASticklet widget) {
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		return 0;
	}
	
	public void press(int keyCode) {
		
	}
	
	public int finished() {
		return 0;
	}
	// 是否能记录历史：
	public boolean rollbackable() {
		return true;
	}
	
	

	
}
