package com.risetek.keke.client.data;

import java.util.Stack;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.Node;

public abstract class AWidget {
	public Stack<Node>	NodesStack;
	public Node current;
	Node rootNode;
	
	public abstract void Execute();
	
	public void engage() {
		if( current.children != null ) {
			NodesStack.push(current);
			current.children.enter(this);
		}
	}
	
	public void rollback() {
		if( NodesStack.size() > 1 ) {
			NodesStack.pop().enter(this);
		}
	}
	
	public void move_down() {
		if( current.next != null ) {
			current.next.enter(this);
		}
	}
	public int move_up() {
		if( NodesStack.size() > 0 ) {
			Node p = NodesStack.pop();
			NodesStack.push(p);
			p = p.children;
			if( p == current )
				return -1;
			while( p.next != current )
				p = p.next;
			
			p.enter(this);
			return 0;
		}
		return -1;
	}
	
}
