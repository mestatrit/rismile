package com.risetek.keke.client.sticklet;

import com.risetek.keke.client.nodes.Node;


public class LoginSticklet extends ASticklet {
	public LoginSticklet() {
		rootNode = Node.loadNodes(LocalSticklets.a);
	}
}
