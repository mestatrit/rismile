package com.risetek.keke.client.sticklet;

import java.util.Stack;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.Node;

public abstract class ASticklet {
	public String aStickletName = null;
	public ASticklet(String name) {
		aStickletName = name;
	}
	
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
	
	public int Execute() {
		return rootNode.enter(this);
	}
	
	public void press(int keyCode) {
		if( currentNode != null )
			currentNode.press(keyCode);
	}
	
	public final static int STICKLET_UP 				= 0;
	public final static int STICKLET_DOWN 				= 1;
	public final static int STICKLET_ROLLBACK 			= 2;
	public final static int STICKLET_ENGAGE 			= 3;
	public final static int STICKLET_ENGAGE_BY_CANCEL 	= 4;
	
	int control_mask = 0;
	
	public void control_mask(int controlCode) {
		control_mask |= ( 1 << controlCode );
	}

	public void control_unmask(int controlCode) {
		control_mask &= ~( 1 << controlCode );
	}

	public void control_mask_key() {
		control_mask(STICKLET_UP);
		control_mask(STICKLET_DOWN);
		control_mask(STICKLET_ROLLBACK);
		control_mask(STICKLET_ENGAGE);
	}

	public void control_unmask_key() {
		control_unmask(STICKLET_UP);
		control_unmask(STICKLET_DOWN);
		control_unmask(STICKLET_ROLLBACK);
		control_unmask(STICKLET_ENGAGE);
	}
	
	
	public int control(int controlCode) {
		// 判断本操作是否被屏蔽。
		if( (control_mask & ( 1 << controlCode )) != 0 )
			return Node.NODE_OK;
		
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
				
				return p.enter(this);
			}
			else
				PosContext.Log("Fatal: broken move up history stack");
			break;
			
		case STICKLET_DOWN:
			if( currentNode.next != null ) {
				return currentNode.next.enter(this);
			}
			break;
			
		case STICKLET_ROLLBACK:
			if( HistoryNodesStack.size() > 0 ) {
				Node n = HistoryNodesStack.pop();
				return n.rollback(this);
			}
			else
			{
				// 这表明这是第一个节点，一定是NamedNode ?
				if( callerSticklet != null ) {
					callerSticklet.calledSticklet.Clean();
					callerSticklet.calledSticklet = null;
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ROLLBACK));
				}
				else
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE));
			}
			break;
			
		case STICKLET_ENGAGE:
			/*
			 * 从当前节点进入下一个节点的时候，执行该动作。
			 */
			// 记录运行的层次。
			HistoryNodesStack.push(currentNode);
			int code;
			// 我们这里决定widget的存在与否
			code = currentNode.action(this);
			if( code == Node.NODE_EXIT ) {
				return code;
			}

			if( code == Node.NODE_CANCEL) {
				// 这表明本sticklet没有成功执行。
				if( callerSticklet != null ) {
					callerSticklet.calledSticklet.Clean();
					callerSticklet.calledSticklet = null;

					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
					return code;
				}
				return code;
			}
			
			if( code == Node.NODE_OK)
			{
				if( getChildrenNode(currentNode) != null ) {
	
					code = getChildrenNode(currentNode).enter(this);
					return code;
				}
				// 当前节点的children节点没有了，我们得查询其是否被调用CallerNode的sticklet环境。
				else
				{
					if( callerSticklet != null ) {
						callerSticklet.calledSticklet.Clean();
						callerSticklet.calledSticklet = null;
	
						ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE));
	
						ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
						return code;
					}
				}
			}
			PosContext.Log("Fatal: engage failed");
			HistoryNodesStack.pop();
			break;
		case STICKLET_ENGAGE_BY_CANCEL:
			PosContext.LogFaild(currentNode);
			return currentNode.failed(this);
			//break;
		default:
			break;
		}
		return Node.NODE_OK;
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
	public int Call(ASticklet called) {
		calledSticklet = called;
		called.callerSticklet = this;
		return called.Execute();
	}
	
	public int Clean() {
		PosContext.Log(aStickletName+" Clean up");
		return 0;
	}
}
