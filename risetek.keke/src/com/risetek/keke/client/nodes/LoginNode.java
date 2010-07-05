package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;


/*
 * 登录节点
 * 这应该是一个虚节点，不接受用户的输入。
 */
public class LoginNode extends Node {
	
	private static RequestBuilder rqBuilder;
	
	

	public LoginNode(String promotion) {
		super(promotion, "Login");
	}

	public int leave(ASticklet widget) {
		return super.leave(widget);
	}

	/*
	 * (non-Javadoc)
	 * @see com.risetek.keke.client.nodes.Node#enter(com.risetek.keke.client.data.AWidget)
	 * 虚拟节点，这个步骤是一个过程，不会停留。
	 */
	public int enter(final ASticklet sticklet) {
		// 我们应该终止对rollback控制的响应。
		sticklet.control_mask_key();
		
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		String password = sticklet.ParamStack.pop();
		String username = sticklet.ParamStack.pop();
	
		// 压回数据，当重复本过程的时候，需要这些参数。
		sticklet.ParamStack.push(username);
		sticklet.ParamStack.push(password);

		String param ="<?xml version=\"1.0\" encoding=\"GB2312\"?><RemoteService name=\"Login\">" 
			+ "<username>" + username + "</username>" + "<password>" + password +"</password>"
			+ "</RemoteService>";
		
		IRemoteServiceAsync loginService = GWT.create(IRemoteService.class);
		loginService.remoteService(param, new AsyncCallback<String[][]>(){

			@Override
			public void onFailure(Throwable caught) {
				sticklet.control_unmask_key();
				Sticklet s = Sticklets.loadSticklet("epay.local.services.failed");
				sticklet.Call(s);
			}

			@Override
			public void onSuccess(String result[][]) {
				sticklet.control_unmask_key();
				Sticklet s = Sticklets.loadSticklet(result);
				sticklet.Call(s);
			}} );		
		
		
		return super.enter(sticklet);
	}

	public int action(final ASticklet sticklet) {

		return super.action(sticklet);
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
	public int failed(ASticklet sticklet) {
		super.failed(sticklet);
		if( sticklet.callerSticklet != null ) {
			sticklet.callerSticklet.calledSticklet.Clean();
			sticklet.callerSticklet.calledSticklet = null;
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL));
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		}
		return NODE_STAY;
	}
}
