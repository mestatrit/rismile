package com.risetek.keke.client.data;

import java.util.Stack;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.Node;

public class LoginWidget extends AWidget {
	
	private LoginWidget() {
		widget = new NamedNode("PromotionTicker", "Root ePay");

		Node n = widget.addChildrenNode(new Node("PromotionTicker", "登录ePay", "p3"));
		widget.addChildrenNode(new Node("PromotionTicker", "送时间", "p4"));
		widget.addChildrenNode(new Node("PromotionTicker", "送生活"));
		widget.addChildrenNode(new Node("PromotionTicker", "送安全"));

		n = n.addChildrenNode(new Node("UsernameTicker", "输入用户名称"));
		n = n.addChildrenNode(new Node("PasswordTicker", "输入登录密码"));
		n = n.addChildrenNode(new Node("PromotionTicker", "登录ePay"));
		n.addChildrenNode(new Node("LoginTicker", "链接网络"));
	}
	
	public static LoginWidget INSTANCE = new LoginWidget();
	
	public void Execute(PosContext context) {
		this.context = context;
		NodesStack = new Stack<Node>();
		NodesStack.push(widget);
		current = widget.children;
		context.updateView();		
	}
}
