package com.risetek.keke.client.data;

import java.util.Stack;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.nodes.ExitNode;
import com.risetek.keke.client.nodes.InputNode;
import com.risetek.keke.client.nodes.LoginNode;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.nodes.PasswordNode;
import com.risetek.keke.client.nodes.PromotionNode;

public abstract class AWidget {
	
	public static final int WIDGET_OK	= 0;
	public static final int WIDGET_EXIT = -1;
	
	public Stack<Node>	HistoryNodesStack;
	public Node current;
	Node rootNode;

	// 执行其间的运行参数堆栈。
	public Stack<String> ParamStack;
	
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
		else if( "Exit".equals(nodeDesc[1]))
			node = new ExitNode();
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
	
	public Node loadNodes(String[][] datas) {
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
		
	public void clearHistory() {
		while( HistoryNodesStack.size() > 1 ) {
			HistoryNodesStack.pop();
		}
	}
	
	public void Execute() {
		ParamStack = new Stack<String>();
		HistoryNodesStack = new Stack<Node>();
		
		HistoryNodesStack.push(rootNode);
		rootNode.enter(this);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
	}
	
	public void press(int keyCode) {
		if( current != null )
			current.press(keyCode);
	}
	
	public final static int WIDGET_UP 		= 0;
	public final static int WIDGET_DOWN 	= 1;
	public final static int WIDGET_ROLLBACK = 2;
	public final static int WIDGET_ENGAGE 	= 3;
	public final static int WIDGET_LEAVE 	= 4;
	
	int control_mask = 0;
	
	public void control_mask(int controlCode) {
		control_mask |= ( 1 << controlCode );
	}

	public void control_unmask(int controlCode) {
		control_mask &= ~( 1 << controlCode );
	}

	public int control(int controlCode) {

		// 判断本操作是否被屏蔽。
		if( (control_mask & ( 1 << controlCode )) != 0 )
			return WIDGET_OK;
		
		switch( controlCode )
		{
		case WIDGET_UP:
			if( HistoryNodesStack.size() > 0 ) {
				Node p = HistoryNodesStack.pop();
				HistoryNodesStack.push(p);
				p = getChildrenNode(p);
				if( p == current )
					break;
				
				while( p.next != current )
					p = p.next;
				
				p.enter(this);
				//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
			break;
			
		case WIDGET_DOWN:
			if( current.next != null ) {
				current.next.enter(this);
				//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
			break;
			
		case WIDGET_ROLLBACK:
			if( HistoryNodesStack.size() > 1 ) {
				Node n = HistoryNodesStack.pop();
				if( n.rollbackable() )
				{
					n.enter(this);
					//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
				}
				else
					HistoryNodesStack.push(n);
					
			}
			break;
			
		case WIDGET_ENGAGE:
			/*
			 * 从当前节点进入下一个节点的时候，执行该动作。
			 */
			
			// 如果当前节点任务没有完成，不能进步。
			if( current.finished() == 0 )
			{
				if( getChildrenNode(current) != null ) {
					// 离开当前节点，进入下一个节点，这说明本步骤得到认可，需要获取该阶段的运行结果。
					current.action(this);
					// 记录运行的层次。
					HistoryNodesStack.push(current);
					// 我们这里决定widget的存在与否
					int code = getChildrenNode(current).enter(this);
					if( code == Node.NODE_EXIT )
						return WIDGET_EXIT;
					//else ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
				}
			}
			break;
			
		case WIDGET_LEAVE:
			break;
			
		default:
			break;
		}
		return WIDGET_OK;
	}
	
	Stack<Node> callerNodeStack = new Stack<Node>();
	
	/*
	 * 我们或者得到节点的直接子孙。
	 * 或者得到了CallerNode的直接子孙。
	 */
	public Node getChildrenNode(Node p) {
		Node n = p.getChildren();
		return n;
	}
}
