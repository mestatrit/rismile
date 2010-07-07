package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.sticklet.Sticklet;

public class KVPairStick extends VStick {

	public KVPairStick() {
		super("键值设定");
	}

	@Override
	public int rollback(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		String value = sticklet.ParamStack.pop();
		String key = sticklet.ParamStack.pop();
		sticklet.ParamStack.push(key);
		sticklet.ParamStack.push(value);
		D3Context.system.remove(key);
		return super.rollback(context);
	}

	@Override
	public int action(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		// 最接近的参数区是键值对。
		String value = sticklet.ParamStack.pop();
		String key = sticklet.ParamStack.pop();
		sticklet.ParamStack.push(key);
		sticklet.ParamStack.push(value);
		D3Context.system.put(key,value);
		return super.action(context);
	}
}
