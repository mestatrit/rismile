package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.InputComposite;
import com.risetek.keke.client.sticklet.Sticklet;


/*
 * 命名节点，是大量重复使用的结构化节点组。
 */
public class InputNode extends Stick {
	String input = "";
	public InputNode(String promotion, String imgName) {
		super(promotion, imgName);
	}

	public Composite getComposite() {
		if( composite == null )
			composite = new InputComposite(this);;
		return composite;
	}
	
	@Override
	public int enter(D3Context context) {
		return super.enter(context);
	}
	
	@Override
	public void press(int keyCode) {
		InputComposite myComposite = (InputComposite)getComposite();

		StringBuffer sb = new StringBuffer();
		char c = "0123456789".charAt(keyCode);
		sb.append(input);
		sb.append(c);
		input = sb.toString();
		myComposite.input.setText(input);
	}

	@Override
	public int leave(D3Context context) {
		input = "";
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(input);
		return 0;
	}

	@Override
	public int action(D3Context context) {
		// 压入输入数据。
		Sticklet sticklet = context.getSticklet();
		sticklet.ParamStack.push(input);
		return super.action(context);
	}
	
	@Override
	public int rollback(D3Context context) {
		// 取消原来那个输入数据。
		Sticklet sticklet = context.getSticklet();
		sticklet.ParamStack.pop();
		return super.rollback(context);
	}
	
}
