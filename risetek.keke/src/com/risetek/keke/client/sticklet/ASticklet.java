package com.risetek.keke.client.sticklet;

import java.util.Stack;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.nodes.Node;

public abstract class ASticklet {
	String aStickletName = null;
	public ASticklet(String name) {
		aStickletName = name;
	}
	
	public static final int STICKLET_OK	= 0;
	public static final int STICKLET_EXIT = -1;
	
	/*
	 * 节点移动路径记录表
	 * 用来获取当前节点的上一级节点信息，并通过上级节点获取本级节点的首项节点信息。
	 * 也用于rollback中。
	 */
	
	public Stack<Node>	HistoryNodesStack = new Stack<Node>();
	/*
	 * 标志当前活动中的节点。 
	 */
	Node currentNode;
	public Node getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNode(Node n) {
			currentNode = n;
	}
	/*
	 * 本Sticklet的入口节点。
	 */
	public Node rootNode;
	
	/*
	 * 双链路，钩挂调用者和被调用者的层级关系。
	 */
	public ASticklet	calledSticklet = null;
	public ASticklet	callerSticklet = null;

	/*
	 * 获取Sticklet调用层级关系中的当前活动目标。
	 */
	public ASticklet getActiveSticklet() {
		if( calledSticklet != null )
			return calledSticklet.getActiveSticklet();
		return this;
	}
	
	// 执行其间的运行参数堆栈。
	public Stack<String> ParamStack = new Stack<String>();
	
	public void Execute() {
		HistoryNodesStack.push(rootNode);
		rootNode.enter(this);
		//ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
	}
	
	public void press(int keyCode) {
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
				// 离开当前节点，进入下一个节点，这说明本步骤得到认可，需要获取该阶段的运行结果。
				int result = currentNode.action(this);
				if( result == Node.NODE_EXIT )
					return STICKLET_EXIT;

				if( getChildrenNode(currentNode) != null ) {
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
					if( callerSticklet != null ) {
						callerSticklet.calledSticklet = null;
						return callerSticklet.control(STICKLET_ENGAGE);
					}
					// 当前节点的children节点没有了，我们得查询
					// 还是直接返回？
					// 还是根本不理睬？
					// return STICKLET_EXIT;
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
		return p.getChildren();
	}

	/*
	 * 调用执行。
	 * 调用者的参数是不是也应该压入子sticklet中，以便执行？
	 */
	public void Call(ASticklet called) {
		calledSticklet = called;
		called.callerSticklet = this;
		called.Execute();
	}
	
}
