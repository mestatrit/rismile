package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;


/*
 * 登录节点
 * 这应该是一个虚节点，不接受用户的输入。
 */
public class LoginNode extends Node {

	int state = -1;
	
	public LoginNode(String promotion) {
		super("Login", promotion);
	}

	public int leave(ASticklet widget) {
		// 取消链接
		state = -1;
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 虚拟节点，这个步骤是一个过程，不会停留。
	 */
	
	public int enter(final ASticklet widget) {
		// 我们应该终止对rollback控制的响应。
		super.enter(widget);

		if( state == -1 ) {
		
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		String password = widget.ParamStack.pop();
		String username = widget.ParamStack.pop();
		
		ILoginServiceAsync loginService = GWT.create(ILoginService.class);
		loginService.loginServer(username, password, new AsyncCallback<String[][]>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("login failed");
				Sticklet s = Sticklets.loadSticklet("epay.local.services.failed");
				widget.Call(s);
			}

			@Override
			public void onSuccess(String result[][]) {
				GWT.log("login sucessed!");
				state = 0;
				Sticklet s = Sticklets.loadSticklet(result);
				widget.Call(s);
			}} );
		}
		else
			widget.control(ASticklet.STICKLET_ENGAGE);
		return 0;
	}

	public int finished() {
		return state;
	}
	
	public int action(final ASticklet widget) {
		GWT.log("Login action");
		return 0;
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
}
