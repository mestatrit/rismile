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
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;
import com.risetek.keke.client.ui.D3View;
import com.risetek.keke.client.ui.KekesComposite;

public class D3Context {

	// 我们用 key value pair 来维系系统级别的信息。
	public static HashMap<String, String> system = new HashMap<String, String>();

	public final Stack<Sticklet> callerSticklets = new Stack<Sticklet>();
	
	
	private final Stack<Sticklet> executeWidget = new Stack<Sticklet>();
	 // 表现层
	final private Presenter presenter;

	public static void Log(String message) {
		GWT.log(message);
		D3View.logger.logger.addItem(message);
	}

	/*
	 * 运行的Sticklet
	 */
	private Sticklet runningSticklet;

	public Sticklet getSticklet() {
		return callerSticklets.peek();
	}


	public D3Context(KekesComposite view) {
		ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(viewchangedhandler,ViewChangedEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(keyCodehandler, HIDNumberEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(controlCodehandler,HIDControlEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(callerhandler, CallerEvent.TYPE);

		presenter = new Presenter(view);
		Sticklet let = Sticklets.loadSticklet("epay.local.demo");
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(let));
	}

	void system_exit() {
		D3Context.Log("D3View GAMEOVER");
		callerSticklets.clear();
		Sticklet let = Sticklets.loadSticklet("epay.local.gameover");
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(let));
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
			Sticklet sticklet = getSticklet();
			int code = event.getKeyCode();
			sticklet.getCurrentNode().press(code);
		}
	};

	public void Caller(Sticklet sticklet) {
		callerSticklets.push(sticklet);
		sticklet.rootNode.enter(this);
	}
	
	CallerHandler callerhandler = new CallerHandler() {

		@Override
		public void onEvent(CallerEvent event) {
			Sticklet sticklet = event.getSticklet();
			callerSticklets.push(sticklet);
			sticklet.rootNode.enter(D3Context.this);
		}
	};

	HIDControlHandler controlCodehandler = new D3ControlHandler(this);
}
