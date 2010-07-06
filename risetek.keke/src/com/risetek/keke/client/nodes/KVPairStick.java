package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class KVPairStick extends VStick {

	public KVPairStick() {
		super("键值设定");
	}

	public int enter(ASticklet widget) {
		return super.enter(widget);
	}

	public int rollback(ASticklet sticklet) {
		String value = sticklet.ParamStack.pop();
		String key = sticklet.ParamStack.pop();
		sticklet.ParamStack.push(key);
		sticklet.ParamStack.push(value);
		PosContext.system.remove(key);
		return super.rollback(sticklet);
	}

	public int action(ASticklet sticklet) {
		// 最接近的参数区是键值对。
		String value = sticklet.ParamStack.pop();
		String key = sticklet.ParamStack.pop();
		sticklet.ParamStack.push(key);
		sticklet.ParamStack.push(value);
		PosContext.system.put(key,value);
		return super.action(sticklet);
	}
}
