package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;

public class InjectTokenNode extends VNode {

	public InjectTokenNode(String Token) {
		super("InjectTokenNode", "");
		PosContext.Token = Token;
	}

}
