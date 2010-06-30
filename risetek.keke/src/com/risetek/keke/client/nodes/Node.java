package com.risetek.keke.client.nodes;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.sticklet.ASticklet;

/*
 * 这个结构用来表达一系列串联的节点。并能存储到数据库中。这是一种变异了的树形结构。
 */

public abstract class Node {
	
	public static final HashMap<String, Node> namedNodesHash = new HashMap<String, Node>(); 
	
	public final static int NODE_OK	= 0;
	public final static int NODE_EXIT	= -1;
	
	private Node children;
	public Node next;
	public String Ticker;
	public String Promotion;
	public String imgName;

	Composite composite = null;
	
	public Node getChildren() {
		return children;
	}
	
	public abstract Composite getComposite();

	public Node(String ticker, String promotion) {
		Ticker = ticker;
		Promotion = promotion;
		imgName = "p2";
	}

	public Node(String ticker, String promotion, String imgName) {
		Ticker = ticker;
		Promotion = promotion;
		this.imgName = imgName;
	}
	
	/*
	 * 链接一个节点到本节点的子孙
	 */
	public Node addChildrenNode(Node node) {
		if( children == null )
			children = node;
		else
			children.addNextNode(node);
		return node;
	}
	
	/*
	 * 链接一个弟兄到本节点
	 */
	
	private void addNextNode(Node node) {
		if( next == null )
			next = node;
		else
			next.addNextNode(node);
	}

	public int enter(ASticklet sticklet) {
		if( sticklet.getCurrentNode() != null )
			sticklet.getCurrentNode().leave(sticklet);
		sticklet.setCurrentNode(this);
		
		if( getComposite() != null )
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		return 0;
	}
	
	public int leave(ASticklet widget) {
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		return 0;
	}
	
	public void press(int keyCode) {
		
	}
	
	public int finished() {
		return 0;
	}
	// 是否能记录历史：
	public boolean rollbackable() {
		return true;
	}
	
	
	
	private static Node createNode(String[] nodeDesc) {
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
		else if( "Logout".equals(nodeDesc[1]))
			node = new LogoutNode();
		else if( "Exit".equals(nodeDesc[1]))
			node = new ExitNode();
		else if( "SecurityCheck".equals(nodeDesc[1]))
			node = new SecurityCheckNode();
		else if( "InjectToken".equals(nodeDesc[1]))
			node = new InjectTokenNode(nodeDesc[2]);
		else
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		return node;
	}
	
	public static Node loadNodes(String[][] datas) {

		class NodeDegree {
			int degree;
			Node node;
			public NodeDegree(int degree, Node node) {
				this.degree = degree;
				this.node = node;
			}
		}
		
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
//			GWT.log("Load node: "+ datas[loop][1] +" " + datas[loop][2]);
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
	
	
}
