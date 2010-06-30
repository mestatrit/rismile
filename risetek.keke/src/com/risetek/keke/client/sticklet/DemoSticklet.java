package com.risetek.keke.client.sticklet;

import com.risetek.keke.client.nodes.Node;

public class DemoSticklet  extends ASticklet {

	public DemoSticklet() {
		rootNode = Node.loadNodes(LocalSticklets.demoSticklet);
	}
}
