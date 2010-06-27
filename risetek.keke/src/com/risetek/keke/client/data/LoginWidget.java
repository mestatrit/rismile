package com.risetek.keke.client.data;

import java.util.Stack;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.Node;

public class LoginWidget extends AWidget {
	
	private LoginWidget() {
		rootNode = new NamedNode("PromotionTicker", "Root ePay");

		Node n = rootNode.addChildrenNode(new Node("PromotionTicker", "登录ePay", "p3"));
		rootNode.addChildrenNode(new Node("PromotionTicker", "送时间", "p4"));
		rootNode.addChildrenNode(new Node("PromotionTicker", "送生活"));
		rootNode.addChildrenNode(new Node("PromotionTicker", "送安全"));

		n = n.addChildrenNode(new Node("UsernameTicker", "输入用户名称"));
		n = n.addChildrenNode(new Node("PasswordTicker", "输入登录密码"));
		n = n.addChildrenNode(new Node("PromotionTicker", "登录ePay"));
		n.addChildrenNode(new Node("LoginTicker", "链接网络"));
	}
	
	public static LoginWidget INSTANCE = new LoginWidget();
	
	public void Execute() {
		NodesStack = new Stack<Node>();
		NodesStack.push(rootNode);
		rootNode.enter(this);
	}
}
