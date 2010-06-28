package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;


/*
 * 登录节点
 */
public class LoginNode extends Node {

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
		return 0;
	}

	public int enter(AWidget widget) {
		super.enter(widget);
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		final PromotionComposite myComposite = (PromotionComposite)getComposite();
		myComposite.brief.setText("登录中...");
		
		ILoginServiceAsync loginService = GWT.create(ILoginService.class);
		loginService.loginServer("username", "password", new AsyncCallback<String>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("login failed");
				myComposite.brief.setText("登录失败");
			}

			@Override
			public void onSuccess(String result) {
				GWT.log("login sucessed!");
				state = 0;
			}} );
		return 0;
	}

	public int finished() {
		return state;
	}
}
