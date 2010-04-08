package com.risetek.keke.client.events;

import com.risetek.keke.client.Risetek_keke;
import com.risetek.keke.client.datamodel.Kekes;

public class PosInitDialogEvent extends PosDialogEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
	    Risetek_keke.renderKekes(Kekes.grand);
	}

	@Override
	public boolean validTransition(String event) {
		// TODO Auto-generated method stub
		return false;
	}

}
