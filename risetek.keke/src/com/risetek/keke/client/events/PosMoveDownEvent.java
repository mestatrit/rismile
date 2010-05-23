package com.risetek.keke.client.events;


public class PosMoveDownEvent extends PosEvent {

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void engage(int value) throws PosException {
		context().currentKeke++;
		
		if( context().currentKeke >= context().kekes.size() )
			context().currentKeke = context().kekes.size() - 1;
		
	    context().loadEvent(new PosRenderEvent());
	    context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		// TODO Auto-generated method stub
		return false;
	}

}
