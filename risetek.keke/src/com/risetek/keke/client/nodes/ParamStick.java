package com.risetek.keke.client.nodes;

import com.risetek.keke.client.sticklet.ASticklet;

public class ParamStick extends VStick {
	
	private String param;
	public ParamStick(String param) {
		super("参数压入节点");
		this.param = param;
	}

	public int enter(ASticklet sticklet) {
		return super.enter(sticklet);
	}

	public int action(ASticklet sticklet) {
		// 压入数据。
		sticklet.ParamStack.push(param);
		return super.action(sticklet);
	}
	
	public int rollback(ASticklet sticklet) {
		// 取消原来那个输入数据。
		sticklet.ParamStack.pop();
		return super.rollback(sticklet);
	}
}
