package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.sticklet.ASticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PasswordNode extends Node {
	String password = "";
	String passshow = "";
	public PasswordNode(String promotion, String imgName) {
		super(promotion, "Password");
	}

	public Composite getComposite() {
		if( composite == null )
			composite = new InputComposite(this);;
		return composite;
	}

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

	public int leave(ASticklet widget) {
		password = "";
		passshow = "";
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(passshow);
		return 0;
	}
	
	public int action(ASticklet sticklet) {
		// 压入密码数据。
		sticklet.ParamStack.push(password);
		return super.action(sticklet);
	}
	
	public int rollback(ASticklet sticklet) {
		// 取消原来那个密码数据。
		sticklet.ParamStack.pop();
		return super.rollback(sticklet);
	}
	
}
