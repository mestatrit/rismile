package com.risetek.keke.client.events;

import com.risetek.keke.client.keke;

public class PosMoveRightEvent extends PosEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
		
		keke k = context().kekes.elementAt(context().currentKeke);
		
	    context().loadEvent(k.event);
	    context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		// TODO Auto-generated method stub
		return false;
	}

}
