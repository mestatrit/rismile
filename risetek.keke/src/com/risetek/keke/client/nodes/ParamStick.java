package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.sticklet.Sticklet;

public class ParamStick extends VStick {
	
	private String param;
	public ParamStick(String param) {
		super("参数压入节点");
		this.param = param;
	}

	@Override
	public int action(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		// 压入数据。
		sticklet.ParamStack.push(param);
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
