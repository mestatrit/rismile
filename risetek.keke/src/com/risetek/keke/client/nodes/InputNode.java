package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberHandler;
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
		input = "";
		press(-1);	// 用来更新显示。
		ClientEventBus.INSTANCE.addHandler(keyCodehandler, HIDNumberEvent.TYPE);
		return super.enter(context);
	}
	
	public void press(int keyCode) {
		if( keyCode >= 0 ) {
			StringBuffer sb = new StringBuffer();
			char c = "0123456789".charAt(keyCode);
			sb.append(input);
			sb.append(c);
			input = sb.toString();
		}
		InputComposite myComposite = (InputComposite)getComposite();
		myComposite.input.setText(input);
	}

	@Override
	public int leave(D3Context context) {
		ClientEventBus.INSTANCE.removeHandler(keyCodehandler, HIDNumberEvent.TYPE);
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

		input = "";
		press(-1);	// 用来更新显示。
		ClientEventBus.INSTANCE.addHandler(keyCodehandler, HIDNumberEvent.TYPE);

		return super.rollback(context);
	}
	
	@Override
	public void onShow(D3Context context) {
		context.showKeyPad();
	}
	
	@Override
	public void onHide(D3Context context) {
		context.hideKeyPad();
	}
	
	
	private final HIDNumberHandler keyCodehandler = new HIDNumberHandler() {
		@Override
		public void onEvent(HIDNumberEvent event) {
			int code = event.getKeyCode();
			press(code);
		}
	};
	
}
