package com.risetek.keke.client.sticklet;

import java.util.Stack;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.nodes.Node;

public abstract class ASticklet {
	
	public static final int STICKLET_OK	= 0;
	public static final int STICKLET_EXIT = -1;
	
	public Stack<Node>	HistoryNodesStack = new Stack<Node>();
	
	public ASticklet	calledSticklet = null;
	public ASticklet	callerSticklet = null;

	Node currentNode;
	public Node rootNode;

	public Node getCurrentNode() {
		if( calledSticklet != null )
			return calledSticklet.getCurrentNode();
		return currentNode;
	}
	
	public void setCurrentNode(Node n) {
		if( calledSticklet != null )
			calledSticklet.setCurrentNode(n);
		else
			currentNode = n;
	}
	// 执行其间的运行参数堆栈。
	public Stack<String> ParamStack = new Stack<String>();
	
		
	public void clearHistory() {
		while( HistoryNodesStack.size() > 1 ) {
			HistoryNodesStack.pop();
		}
	}
	
	public void Execute() {
		HistoryNodesStack.push(rootNode);
		rootNode.enter(this);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
	}
	
	public void press(int keyCode) {
		if( calledSticklet != null ) {
			calledSticklet.press(keyCode);
			return;
		}
		
		if( currentNode != null )
			currentNode.press(keyCode);
	}
	
	public final static int STICKLET_UP 		= 0;
	public final static int STICKLET_DOWN 	= 1;
	public final static int STICKLET_ROLLBACK = 2;
	public final static int STICKLET_ENGAGE 	= 3;
	public final static int STICKLET_LEAVE 	= 4;
	
	int control_mask = 0;
	
	public void control_mask(int controlCode) {
		control_mask |= ( 1 << controlCode );
	}

	public void control_unmask(int controlCode) {
		control_mask &= ~( 1 << controlCode );
	}

	public int control(int controlCode) {
		if( calledSticklet != null )
			return calledSticklet.control(controlCode);
		
		// 判断本操作是否被屏蔽。
		if( (control_mask & ( 1 << controlCode )) != 0 )
			return STICKLET_OK;
		
		switch( controlCode )
		{
		case STICKLET_UP:
			if( HistoryNodesStack.size() > 0 ) {
				Node p = HistoryNodesStack.pop();
				HistoryNodesStack.push(p);
				p = getChildrenNode(p);
				if( p == currentNode )
					break;
				
				while( p.next != currentNode )
					p = p.next;
				
				p.enter(this);
				//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
			break;
			
		case STICKLET_DOWN:
			if( currentNode.next != null ) {
				currentNode.next.enter(this);
				//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
			}
			break;
			
		case STICKLET_ROLLBACK:
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
			
		case STICKLET_ENGAGE:
			/*
			 * 从当前节点进入下一个节点的时候，执行该动作。
			 */
			
			// 如果当前节点任务没有完成，不能进步。
			if( currentNode.finished() == 0 )
			{
				if( getChildrenNode(currentNode) != null ) {
					// 离开当前节点，进入下一个节点，这说明本步骤得到认可，需要获取该阶段的运行结果。
					currentNode.action(this);
					// 记录运行的层次。
					HistoryNodesStack.push(currentNode);
					// 我们这里决定widget的存在与否
					int code = getChildrenNode(currentNode).enter(this);
					if( code == Node.NODE_EXIT )
						return STICKLET_EXIT;
					//else ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
				}
				// 当前节点的children节点没有了，我们得查询其是否被调用CallerNode的sticklet环境。
				else
				{
					// 当前节点的children节点没有了，我们得查询
					return STICKLET_EXIT;
				}
			}
			break;
			
		case STICKLET_LEAVE:
			break;
			
		default:
			break;
		}
		return STICKLET_OK;
	}
	
	/*
	 * 我们或者得到节点的直接子孙。
	 * 或者得到了CallerNode的直接子孙。
	 */
	public Node getChildrenNode(Node p) {
		Node n = p.getChildren();
		if( n == null && callerSticklet != null )
			n = callerSticklet.getCurrentNode();
		return n;
	}
}
