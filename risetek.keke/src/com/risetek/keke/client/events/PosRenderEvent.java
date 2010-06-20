package com.risetek.keke.client.events;


public class PosRenderEvent extends PosEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
		context().renderKekes();
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
