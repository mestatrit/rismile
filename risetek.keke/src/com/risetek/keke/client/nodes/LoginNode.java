package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;


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

	public int leave(ASticklet widget) {
		// 取消链接
		state = -1;
		return 0;
	}

	public void addFailedNode() {
		Node node = new PromotionNode("服务失败", "p2");
		node = addChildrenNode(node);
		node.addChildrenNode(new ExitNode());
	}

	public void addSucessedNode(Node result) {
		/*
		Node node = new PromotionNode("登录成功:"+result, "p3");
		node = addChildrenNode(node);
		node.addChildrenNode(new ExitNode());
		*/
		this.addChildrenNode(result);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 虚拟节点，这个步骤是一个过程，不会停留。
	 */
	
	public int enter(final ASticklet widget) {
		
		super.enter(widget);
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		String password = widget.ParamStack.pop();
		String username = widget.ParamStack.pop();
		
		ILoginServiceAsync loginService = GWT.create(ILoginService.class);
		loginService.loginServer(username, password, new AsyncCallback<String[][]>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("login failed");
				addFailedNode();
				widget.control(ASticklet.STICKLET_ENGAGE);
			}

			@Override
			public void onSuccess(String result[][]) {
				GWT.log("login sucessed!");
				state = 0;
				Node following = Node.loadNodes(result);
				addSucessedNode(following);
//				addSucessedNode(result[1][2]);
				
				widget.control(ASticklet.STICKLET_ENGAGE);
			}} );
		return 0;
	}

	public int finished() {
		return state;
	}
}
