package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.ui.InputComposite;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class PasswordNode extends Node {
	String password = "";
	String passshow = "";
	public PasswordNode(String promotion, String imgName) {
		super("Input", promotion, imgName);
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

	public int leave(AWidget widget) {
		password = "";
		passshow = "";
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(passshow);
		return 0;
	}
	
	public int action(AWidget widget) {
		widget.ParamStack.push(password);
		return 0;
	}
	
}
