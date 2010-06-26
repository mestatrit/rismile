package com.risetek.keke.client.data;

import java.util.Stack;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.Node;

public abstract class AWidget {
	public Stack<Node>	NodesStack;
	PosContext context;
	public Node current;
	
	Node widget;
	public Node getNode() {
		return widget;
	}
	
	public abstract void Execute(PosContext context);
}
