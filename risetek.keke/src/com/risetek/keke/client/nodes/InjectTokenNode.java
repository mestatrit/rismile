package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

public class InjectTokenNode extends VNode {
	private String token;
	public InjectTokenNode(String Token) {
		super(Token);
		token = Token;
	}

	public int enter(ASticklet widget) {
		PosContext.LogEnter(this);
		PosContext.Token = token;
		super.enter(widget);
		return 0;
	}

}
