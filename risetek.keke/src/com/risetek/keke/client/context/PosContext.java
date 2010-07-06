package com.risetek.keke.client.context;

import java.util.HashMap;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus.CallerEvent;
import com.risetek.keke.client.context.ClientEventBus.CallerHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDControlHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberHandler;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedEvent;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedHandler;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklets;
import com.risetek.keke.client.ui.D3View;
import com.risetek.keke.client.ui.KekesComposite;

public class PosContext {

	// 我们用 key value pair 来维系系统级别的信息。
	public static HashMap<String, String> system = new HashMap<String, String>();

	public static void Log(String message) {
		GWT.log(message);
		D3View.logger.logger.addItem(message);
	}

	/*
	 * 表现层
	 */
	final private Presenter presenter;
	/*
	 * 运行的Sticklet
	 */
	private ASticklet runningSticklet;

	public ASticklet getSticklet() {
		return runningSticklet.getActiveSticklet();
	}

	Stack<ASticklet> executeWidget = new Stack<ASticklet>();

	public PosContext(KekesComposite view) {
		ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(viewchangedhandler,ViewChangedEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(keyCodehandler, HIDNumberEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(controlCodehandler,HIDControlEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(callerhandler, CallerEvent.TYPE);

		presenter = new Presenter(view);
		executeWidget.push(Sticklets.loadSticklet("epay.local.demo"));
		Executer();
	}

	void Executer() {
		if (executeWidget.size() > 0) {
			runningSticklet = executeWidget.pop();
			runningSticklet.Execute();
		} else {
			PosContext.Log("D3View GAMEOVER");
			executeWidget.push(Sticklets.loadSticklet("epay.local.gameover"));
			Executer();
		}
	}

	private void updateView() {
		presenter.upDate(this);
	}

	HIDCARDHandler cardhandler = new HIDCARDHandler() {
		@Override
		public void onEvent(HIDCARDEvent event) {
		}
	};

	ViewChangedHandler viewchangedhandler = new ViewChangedHandler() {

		@Override
		public void onEvent(ViewChangedEvent event) {
			updateView();
		}
	};

	HIDNumberHandler keyCodehandler = new HIDNumberHandler() {

		@Override
		public void onEvent(HIDNumberEvent event) {
			ASticklet sticklet = getSticklet();
			int code = event.getKeyCode();
			sticklet.getCurrentNode().press(code);
		}
	};

	CallerHandler callerhandler = new CallerHandler() {

		@Override
		public void onEvent(CallerEvent event) {
			ASticklet sticklet = getSticklet();
			sticklet.Call(event.getSticklet());
		}
	};


	HIDControlHandler controlCodehandler = new HIDControlHandler() {

		@Override
		public void onEvent(HIDControlEvent event) {
			ASticklet sticklet = getSticklet();
			Stick current = sticklet.getCurrentNode();
			int controlKey = event.getControlCode();
			switch (controlKey) {

			// --------------------- 键盘向下 -------------------------
			case ClientEventBus.CONTROL_KEY_DOWN:
				if (current.next != null) {
					current.next.enter(sticklet);
				}
				break;

			// --------------------- 键盘向上 -------------------------
			case ClientEventBus.CONTROL_KEY_UP:
				if (sticklet.HistoryNodesStack.size() > 0) {
					Stick p = sticklet.HistoryNodesStack.pop();
					sticklet.HistoryNodesStack.push(p);
					p = sticklet.getChildrenNode(p);
					if (p == current)
						break;

					while (p.next != current)
						p = p.next;

					p.enter(sticklet);
				} else
					PosContext.Log("Fatal: broken move up history stack");
				break;

			// --------------------- 键盘向左，或者系统发送回溯消息 -------------------------
			case ClientEventBus.CONTROL_KEY_LEFT:
			case ClientEventBus.CONTROL_SYSTEM_ROLLBACK:
				if (sticklet.HistoryNodesStack.size() > 0) {
					
					Stick n = sticklet.HistoryNodesStack.pop();
					// TODO: FIXME: 在logout后，还能回溯？
					if( n.rollback(sticklet) == Stick.NODE_CANCEL ) {
//						sticklet.HistoryNodesStack.push(n);
//						sticklet.HistoryNodesStack.push(current);
					}
				} else {
					// 这表明这是第一个节点，一定是NamedNode ?
					if (sticklet.callerSticklet != null) {
						sticklet.callerSticklet.calledSticklet.Clean();
						sticklet.callerSticklet.calledSticklet = null;
						ClientEventBus.INSTANCE.fireEvent(
								new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ROLLBACK));
					} else
						ClientEventBus.INSTANCE.fireEvent(
								new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE));
				}
				break;
			// --------------------- 系统发送取消消息 -------------------------
			case ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL: {
				// 这个消息目前只在 NamedNode中发出，在NamedNode中，首先处理了当前运行的sticklet的撤销工作。
				PosContext.Log("Cancel sticklet:" + sticklet.aStickletName);
				int state = current.failed(sticklet);
				if (state == Stick.NODE_EXIT) {
					// 上一个sticklet执行完毕。
					// PosContext.Log("Run out of sticklet:"+sticklet.aStickletName);
					// TODO: 这里应该退出程序。
					Executer();
				} else if (state == Stick.NODE_CANCEL) {
					PosContext.Log("engage canceled.");
					ClientEventBus.INSTANCE.fireEvent(
							new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL));
				}
				break;
			}
			case ClientEventBus.CONTROL_KEY_RIGHT:
			case ClientEventBus.CONTROL_SYSTEM_ENGAGE:
				int state = engagecontrol();
				if (state == Stick.NODE_EXIT) {
					// 上一个sticklet执行完毕。
					// PosContext.Log("Run out of sticklet:"+sticklet.aStickletName);
					// TODO: 这里应该退出程序。
					Executer();
				} else if (state == Stick.NODE_CANCEL) {
					PosContext.Log("engage canceled.");
					ClientEventBus.INSTANCE.fireEvent(
							new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL));
				}
				break;
			default:
				PosContext.Log("Control Code Overrun");
				break;
			}
		}
	};

	public int engagecontrol() {
		ASticklet sticklet = getSticklet();
		Stick current = sticklet.getCurrentNode();
		
		/*
		 * 从当前节点进入下一个节点的时候，执行该动作。
		 */
		// 记录运行的层次。
		sticklet.HistoryNodesStack.push(current);
		int code;
		// 我们这里决定widget的存在与否
		code = current.action(sticklet);
		if (code == Stick.NODE_EXIT) {
			return code;
		}

		if (code == Stick.NODE_CANCEL) {
			// 这表明本sticklet没有成功执行。
			if (sticklet.callerSticklet != null) {
				sticklet.callerSticklet.calledSticklet.Clean();
				sticklet.callerSticklet.calledSticklet = null;

				ClientEventBus.INSTANCE.fireEvent(
						new ClientEventBus.ViewChangedEvent());
				return code;
			}
			return code;
		}

		if (code == Stick.NODE_OK) {
			if (sticklet.getChildrenNode(current) != null) {

				code = sticklet.getChildrenNode(current)
						.enter(sticklet);
				return code;
			}
			// 当前节点的children节点没有了，我们得查询其是否被调用CallerNode的sticklet环境。
			else {
				if (sticklet.callerSticklet != null) {
					sticklet.callerSticklet.calledSticklet.Clean();
					sticklet.callerSticklet.calledSticklet = null;

					ClientEventBus.INSTANCE.fireEvent(
							new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE));

					ClientEventBus.INSTANCE.fireEvent(
							new ClientEventBus.ViewChangedEvent());
					return code;
				} else {
					PosContext.Log("Tail stick.");
					// TODO: 临时性的解决办法，回到本sticklet的头。
					Stick s = sticklet.HistoryNodesStack.elementAt(0);
					sticklet.HistoryNodesStack.clear();

					sticklet.HistoryNodesStack.push(s);
					s.enter(sticklet);

				}
			}
		}
		PosContext.Log("Fatal: " + current.Promotion
				+ " engage failed by " + code);
		sticklet.HistoryNodesStack.pop();

		return Stick.NODE_OK;
	}

}
