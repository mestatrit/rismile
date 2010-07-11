package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.sticklet.Sticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PasswordNode extends InputNode {

	String passshow = "";

	public PasswordNode(String promotion) {
		super(promotion, "Password");
	}

	@Override
	public int enter(D3Context context) {
		passshow = "";
		return super.enter(context);
	}
	
	@Override
	public void press(int keyCode) {
		if( keyCode >= 0 ) {
			StringBuffer sb = new StringBuffer();
			char c = "0123456789".charAt(keyCode);
			sb.append(input);
			sb.append(c);
			input = sb.toString();
			passshow = passshow.concat("*");
		}
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(passshow);
	}

	@Override
	public int action(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		// 压入密码数据。
		sticklet.ParamStack.push(input);
		return super.action(context);
	}

	@Override
	public int rollback(D3Context context) {
		passshow = "";
		return super.rollback(context);
	}
}
