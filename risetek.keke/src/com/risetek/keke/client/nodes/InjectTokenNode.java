package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class InjectTokenNode extends VStick {
	private String token;
	public InjectTokenNode(String name, String value) {
		super(value);
		token = value;
	}

	public int enter(ASticklet widget) {
		PosContext.Token = token;
		return super.enter(widget);
	}

}
