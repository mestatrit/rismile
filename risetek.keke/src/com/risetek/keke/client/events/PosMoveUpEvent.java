package com.risetek.keke.client.events;


public class PosMoveUpEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
		context().upKeke(value);
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
