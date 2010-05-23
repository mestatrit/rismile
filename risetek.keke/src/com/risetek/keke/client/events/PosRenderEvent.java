package com.risetek.keke.client.events;


public class PosRenderEvent extends PosEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
		context().renderKekes();
	    //Risetek_keke.renderKekes(Kekes.grand);
	}

	@Override
	public boolean validTransition(String event) {
		// TODO Auto-generated method stub
		return false;
	}

}
