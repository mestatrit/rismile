package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 登录节点
 * 这应该是一个虚节点，不接受用户的输入。
 */
public class LoginNode extends VNode {

	int state = -1;
	
	public LoginNode(String promotion, String imgName) {
		super("Input", promotion, imgName);
	}

	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);;
		return composite;
	}

	public int leave(AWidget widget) {
		// 取消链接
		state = -1;
		return 0;
	}

	public void addFailedNode() {
		Node node = new PromotionNode("登录失败", "p2");
		node = addChildrenNode(node);
		node.addChildrenNode(new ExitNode());
	}

	public void addSucessedNode() {
		Node node = new PromotionNode("登录成功", "p3");
		node = addChildrenNode(node);
		node.addChildrenNode(new ExitNode());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 虚拟节点，这个步骤是一个过程，不会停留。
	 */
	
	public int enter(final AWidget widget) {
		
		super.enter(widget);
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		
		ILoginServiceAsync loginService = GWT.create(ILoginService.class);
		loginService.loginServer("username", "password", new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("login failed");
				addFailedNode();
				widget.engage();
			}

			@Override
			public void onSuccess(String result) {
				GWT.log("login sucessed!");
				state = 0;
				addSucessedNode();
				widget.engage();
			}} );
		return 0;
	}

	public int finished() {
		return state;
	}
}
