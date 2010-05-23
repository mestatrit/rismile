package com.risetek.keke.client.events;


public class PosMoveUpEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
			context().currentKeke--;
		
		if( context().currentKeke < 0 )
			context().currentKeke = 0;
		
	    context().loadEvent(new PosRenderEvent());
	    context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
