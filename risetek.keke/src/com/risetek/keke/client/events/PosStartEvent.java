package com.risetek.keke.client.events;

import com.risetek.keke.client.keke;

public class PosStartEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
		context().clearKekes();
		context().kekes.add(new keke("您不会厌倦的...", "p3", new PosBeginEvent()));
		context().kekes.add(new keke("我们来展现一种能力", "p2", new PosStartEvent()));
	    context().loadEvent(new PosRenderEvent());
		context().eventStack().nextEvent();
		
/*		
	    Kekes begin = new Kekes(Kekes.grand, "也可以从这里开始", "p5");
	    begin.setDefaultOption();
	    (begin = new Kekes(begin, "我们来试一试充值？", "p3")).setDefaultOption();
	    (begin = new CardKeke(begin, "刷卡吧！", "p4")).setDefaultOption();
	    (begin = new GameOverKeke(begin, "您被耍掉了100元哦！", "p2")).setDefaultOption();
        context().eventStack().nextEvent();
*/        
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
