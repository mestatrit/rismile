package com.risetek.keke.client.PosEvents;


public class PosMoveDownEvent extends PosEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
		context().downKeke(value);
	}

	@Override
	public boolean validTransition(String event) {
		// TODO Auto-generated method stub
		return false;
	}

}
