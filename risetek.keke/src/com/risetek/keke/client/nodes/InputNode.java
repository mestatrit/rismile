package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.sticklet.ASticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class InputNode extends Node {
	String input = "";
	public InputNode(String promotion, String imgName) {
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
		sb.append(input);
		sb.append(c);
		input = sb.toString();
		myComposite.input.setText(input);
	}

	public int leave(ASticklet widget) {
		input = "";
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(input);
		return 0;
	}

	public int action(ASticklet widget) {
		widget.ParamStack.push(input);
		return 0;
	}
}
