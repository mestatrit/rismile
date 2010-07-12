package com.risetek.keke.client.context;

import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.sticklet.Sticklet;

public class D3ControlHandler implements ClientEventBus.HIDControlHandler{

	private D3Context context;
	public D3ControlHandler(D3Context context) {
		this.context = context;
	}
	
	@Override
	public void onEvent(HIDControlEvent event) {
		Sticklet sticklet = context.getSticklet();
		Stick current = sticklet.getCurrentNode();
		
		int controlKey = event.getControlCode();
		switch (controlKey) {

		// --------------------- 键盘向下 -------------------------
		case ClientEventBus.CONTROL_KEY_DOWN:
			if (current.next != null) {
				current.onHide(context);
				current.next.enter(context);
			}
			break;

		// --------------------- 键盘向上 -------------------------
		case ClientEventBus.CONTROL_KEY_UP:
			if (sticklet.HistoryNodesStack.size() > 0) {
				Stick p = sticklet.HistoryNodesStack.peek();
				p = sticklet.getChildrenNode(p);
				if (p == current)
					break;

				while (p.next != current)
					p = p.next;

				current.onHide(context);
				p.enter(context);
			} else
				D3Context.Log("Fatal: broken move up history stack");
			break;

		// --------------------- 键盘向左，或者系统发送回溯消息 -------------------------
		case ClientEventBus.CONTROL_KEY_LEFT:
		case ClientEventBus.CONTROL_SYSTEM_ROLLBACK:
			if (sticklet.HistoryNodesStack.size() > 0) {
				
				Stick n = sticklet.HistoryNodesStack.pop();
				// TODO: FIXME: 在logout后，还能回溯？
				current.onHide(context);
				if( n.rollback(context) == Stick.NODE_CANCEL ) {
//					sticklet.HistoryNodesStack.push(n);
//					sticklet.HistoryNodesStack.push(current);
				}
			} else {
				// 这表明这是第一个节点，一定是NamedNode ?
				if( context.callerSticklets.size() > 1 ) {
					sticklet.Clean();
					context.callerSticklets.pop();
					ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ROLLBACK);
				}
				else
					ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ENGAGE);
			}
			break;
		// --------------------- 系统发送取消消息 -------------------------
		case ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL: {
			// 这个消息目前只在 NamedNode中发出，在NamedNode中，首先处理了当前运行的sticklet的撤销工作。
			D3Context.Log("Cancel sticklet:" + sticklet.aStickletName);
			int state = current.failed(context);
			if (state == Stick.NODE_EXIT) {
				// 上一个sticklet执行完毕。
				// PosContext.Log("Run out of sticklet:"+sticklet.aStickletName);
				// TODO: 这里应该退出程序。
				context.system_exit();
			} else if (state == Stick.NODE_CANCEL) {
				D3Context.Log("engage canceled.");
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL);
			}
			break;
		}
		case ClientEventBus.CONTROL_KEY_RIGHT:
		case ClientEventBus.CONTROL_SYSTEM_ENGAGE:
			int state = engagecontrol(context);
			if (state == Stick.NODE_EXIT) {
				// 上一个sticklet执行完毕。
				// PosContext.Log("Run out of sticklet:"+sticklet.aStickletName);
				// TODO: 这里应该退出程序。
				context.system_exit();
			} else if (state == Stick.NODE_CANCEL) {
				D3Context.Log("engage canceled.");
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL);
			}
			break;
		default:
			D3Context.Log("Control Code Overrun");
			break;
		}
	}
	

	public int engagecontrol(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		Stick current = sticklet.getCurrentNode();
		
		/*
		 * 从当前节点进入下一个节点的时候，执行该动作。
		 */
		// 记录运行的层次。
		sticklet.HistoryNodesStack.push(current);
		int code;
		// 我们这里决定widget的存在与否
		current.onHide(context);

		code = current.action(context);
		if (code == Stick.NODE_EXIT) {
			return code;
		}

		if (code == Stick.NODE_CANCEL) {
			// 这表明本sticklet没有成功执行。
			sticklet.HistoryNodesStack.pop();
			if( context.callerSticklets.size() > 1 ) {
				sticklet.Clean();
				context.callerSticklets.pop();
				current.ViewChanged(context);
			}
			return code;
		}

		if (code == Stick.NODE_OK) {
			if (sticklet.getChildrenNode(current) != null) {

				code = sticklet.getChildrenNode(current)
						.enter(context);
				return code;
			}
			// 当前节点的children节点没有了，我们得查询其是否被调用CallerNode的sticklet环境。
			else {
				if( context.callerSticklets.size() > 1 ) {
					sticklet.Clean();
					context.callerSticklets.pop();
					ClientEventBus.fireControlKey(ClientEventBus.CONTROL_SYSTEM_ENGAGE);
					current.ViewChanged(context);
					return code;
				} else {
					D3Context.Log("Tail stick.");
					// TODO: 临时性的解决办法，回到本sticklet的头。
					Stick s = sticklet.HistoryNodesStack.elementAt(0);
					sticklet.HistoryNodesStack.clear();
					sticklet.HistoryNodesStack.push(s);
					s.enter(context);
				}
			}
		}
		D3Context.Log("Fatal: " + current.Promotion
				+ " engage failed by " + code);
		sticklet.HistoryNodesStack.pop();

		return Stick.NODE_OK;
	}
	
}
