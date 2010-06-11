package com.risetek.keke.server.node;

import com.google.gwt.core.client.GWT;

public class Node implements INodeCallback {
	Node parent;
	Node children;
	Node brother;
	/*
	 * 持久存储本节点
	 */
	public void save() {
		
	}
	/*
	 * 链接一个节点到本节点的子孙
	 */
	public void addChildrenNode(Node node) {
		if( children == null )
			children = node;
		else
			children.addBrotherNode(node);
	}
	
	/*
	 * 链接一个弟兄到本节点
	 */
	
	public void addBrotherNode(Node node) {
		if( brother == null )
			brother = node;
		else
			brother.addBrotherNode(node);
	}

	/*
	 * 构造一个新的节点，并关联到父节点
	 */
	public static Node registeNode(Node node) {
		Node n = new Node();
		n.parent = node;
		node.addChildrenNode(n);
		return n;
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
		if( brother != null)
			brother.travelNode(nodecallback);
	}
}
