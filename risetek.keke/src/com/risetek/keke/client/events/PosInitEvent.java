package com.risetek.keke.client.events;

import com.risetek.keke.client.keke;

public class PosInitEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
		context().kekes.add(new keke("从这里开始", "p2", new PosBeginEvent()));
		context().kekes.add(new keke("也可以从这里开始", "p5", new PosStartEvent()));
	    context().loadEvent(new PosRenderEvent());
		context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
