package com.risetek.keke.client.context;

import java.util.HashMap;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus.CallerEvent;
import com.risetek.keke.client.context.ClientEventBus.CallerHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDControlHandler;
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
	 // 表现层
	final private Presenter presenter;
	
	public Sticklet getSticklet() {
		return callerSticklets.peek();
	}

	public static void addListener(D3Context context) {
		ClientEventBus.INSTANCE.addHandler(context.viewchangedhandler,ViewChangedEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(context.controlCodehandler,HIDControlEvent.TYPE);
		ClientEventBus.INSTANCE.addHandler(context.callerhandler, CallerEvent.TYPE);
	}

	public static void removeListener(D3Context context) {
		ClientEventBus.INSTANCE.removeHandler(context.viewchangedhandler,ViewChangedEvent.TYPE);
		ClientEventBus.INSTANCE.removeHandler(context.controlCodehandler,HIDControlEvent.TYPE);
		ClientEventBus.INSTANCE.removeHandler(context.callerhandler, CallerEvent.TYPE);
	}
	
	
	public D3Context(KekesComposite view) {

		presenter = new Presenter(view);
		Sticklet let = Sticklets.loadSticklet("epay.local.demo");
		addListener(this);
		CallSticklet(let);
	}

	void system_exit() {
		D3Context.Log("D3View GAMEOVER");
		callerSticklets.clear();
		Sticklet let = Sticklets.loadSticklet("epay.local.gameover");
		CallSticklet(let);
	}

	public static void CallSticklet(Sticklet sticklet) {
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(sticklet));
	}
	
	private void updateView() {
		D3View.caller.ShowStack(callerSticklets);
		D3View.history.ShowHistoryStack(getSticklet().HistoryNodesStack);
		presenter.upDate(this);
	}

	ViewChangedHandler viewchangedhandler = new ViewChangedHandler() {

		@Override
		public void onEvent(ViewChangedEvent event) {
			updateView();
		}
	};

	CallerHandler callerhandler = new CallerHandler() {

		@Override
		public void onEvent(CallerEvent event) {
			Sticklet sticklet = event.getSticklet();
			callerSticklets.push(sticklet);
			sticklet.rootNode.enter(D3Context.this);
		}
	};

	HIDControlHandler controlCodehandler = new D3ControlHandler(this);

	public static void Log(String message) {
		GWT.log(message);
		D3View.logger.Log(message);
	}
}
