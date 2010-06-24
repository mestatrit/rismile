package com.risetek.keke.server.test;

import com.risetek.keke.server.node.NamedNode;
import com.risetek.keke.server.node.Node;
/*
 * 测试用途：
 * 创建一个命名树。
 * 存储这颗树。
 * 从持久层中构造这棵树。
 */

public class InitNodeDB {

	
	public void initTestNodes1() {
		Node node = new NamedNode("测试1");
		node.addChildrenNode(new Node("第一节点"));
	}
	
	
	/*
	 * 构造一系列测试用的节点，并组成树。
	 */
	public void initNodes() {
		initTestNodes1();
	}
	
	public void initDB() {
		
	}
}
