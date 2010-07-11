package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.sticklet.Sticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PasswordNode extends Stick {
	String password = "";
	String passshow = "";
	public PasswordNode(String promotion) {
		super(promotion, "Password");
	}

	public Composite getComposite() {
		if( composite == null )
			composite = new InputComposite(this);;
		return composite;
	}

	@Override
	public void press(int keyCode) {
		InputComposite myComposite = (InputComposite)getComposite();

		StringBuffer sb = new StringBuffer();
		char c = "0123456789".charAt(keyCode);
		sb.append(password);
		sb.append(c);
		password = sb.toString();
		
		passshow = passshow.concat("*");
		myComposite.input.setText(passshow);
	}

	@Override
	public int leave(D3Context context) {
		password = "";
		passshow = "";
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(passshow);
		return 0;
	}
	
	@Override
	public int action(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		// 压入密码数据。
		sticklet.ParamStack.push(password);
		return super.action(context);
	}
	
	@Override
	public int rollback(D3Context context) {
		// 取消原来那个密码数据。
		Sticklet sticklet = context.getSticklet();
		sticklet.ParamStack.pop();
		return super.rollback(context);
	}
	
	@Override
	public int hasKeyPad() {
		return 1;
	}
}
