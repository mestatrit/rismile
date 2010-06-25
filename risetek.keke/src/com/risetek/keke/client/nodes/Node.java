package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;

/*
 * 这个结构用来表达一系列串联的节点。并能存储到数据库中。这是一种变异了的树形结构。
 */

public class Node implements INodeCallback {
//	Node parent;
	public Node children;
	public Node next;
	String Ticker;
	public String Promotion;
	public String imgName;

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
	 * 持久存储本节点
	 */
	public void save() {

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

	public void callback() {
		if( this instanceof NamedNode) {
			/*
			 * 命名节点，不在这里扩展表达。
			 * 这个命名节点应该注册到某个表中去，以便统一地作为独立的树完成表达。
			 */
			return;
		}
		GWT.log(this.toString());
	}
	/*
	 * 遍历节点，只针对本节点的子孙遍历
	 */
	void travelNode(INodeCallback nodecallback) {
		nodecallback.callback();
		if( children != null )
			children.travelNode(nodecallback);
		if( next != null)
			next.travelNode(nodecallback);
	}
}
