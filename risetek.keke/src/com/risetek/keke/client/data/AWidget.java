package com.risetek.keke.client.data;

import java.util.Stack;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.nodes.InputNode;
import com.risetek.keke.client.nodes.LoginNode;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.nodes.PasswordNode;
import com.risetek.keke.client.nodes.PromotionNode;

public abstract class AWidget {
	
	public static final int WIDGET_OK	= 0;
	public static final int WIDGET_EXIT = -1;
	
	public Stack<Node>	NodesStack;
	public Node current;
	Node rootNode;
	
	private Node createNode(String[] nodeDesc) {
		Node node = null;
		if( "NamedNode".equals(nodeDesc[1]))
			node = new NamedNode(nodeDesc[2]);
		else if ( "Promotion".equals(nodeDesc[1]))
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		else if( "Username".equals(nodeDesc[1]))
			node = new InputNode(nodeDesc[2], nodeDesc[3]);
		else if( "Password".equals(nodeDesc[1]))
			node = new PasswordNode(nodeDesc[2], nodeDesc[3]);
		else if( "Login".equals(nodeDesc[1]))
			node = new LoginNode(nodeDesc[2], nodeDesc[3]);
		else
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		return node;
	}

	private class NodeDegree {
		int degree;
		Node node;
		public NodeDegree(int degree, Node node) {
			this.degree = degree;
			this.node = node;
		}
	}
	
	Node loadNodes(String[][] datas) {
		Vector<NodeDegree> s = new Vector<NodeDegree>();
		NodeDegree dn = new NodeDegree(Integer.parseInt(datas[0][0]), createNode(datas[0]));
		if( dn.degree <= 0 ) {
			GWT.log("root node degree error");
			return null;
		}
		s.add(dn);
		NodeDegree top = dn;
		
		int loop = 1;
		while( loop < datas.length )
		{
			GWT.log("Load node: "+ datas[loop][1] +" " + datas[loop][2]);
			dn = new NodeDegree(Integer.parseInt(datas[loop][0]), createNode(datas[loop]));
			if( s.size() > 0  ) {
				NodeDegree topdn = s.elementAt(0);
				topdn.node.addChildrenNode(dn.node);
				topdn.degree--;
				if( topdn.degree <= 0 )
					s.remove(0);
			}
			else
				GWT.log("load notes error.");
			
			if( dn.degree > 0 )
				s.add(dn);
			loop++;
		}
		
		return top.node;
	}
		
	public void clearHistory() {
		while( NodesStack.size() > 1 ) {
			NodesStack.pop();
		}
	}
	
	public void Execute() {
		NodesStack = new Stack<Node>();
		NodesStack.push(rootNode);
		rootNode.enter(this);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
	}
	
	public int engage() {
		// 如果当前节点任务没有完成，不能进步。
		if( current.finished() == 0 )
		{
			if( current.children != null ) {
				NodesStack.push(current);
				// 我们这里决定widget的存在与否
				int code = current.children.enter(this);
				if( code == Node.NODE_EXIT )
				{
					exit();
					return WIDGET_EXIT;
				}
				else
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
		}
		return WIDGET_OK;
	}
	
	public void rollback() {
		if( NodesStack.size() > 1 ) {
			Node n = NodesStack.pop();
			if( n.rollbackable() )
			{
				n.enter(this);
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
			else
				NodesStack.push(n);
				
		}
	}
	
	public void move_down() {
		if( current.next != null ) {
			current.next.enter(this);
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
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
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			return 0;
		}
		return -1;
	}
	
	public void press(int keyCode) {
		if( current != null )
			current.press(keyCode);
	}
	
	/*
	 * widget执行结束。
	 */
	public void exit() {
		GWT.log("widget exit");
	}
}
