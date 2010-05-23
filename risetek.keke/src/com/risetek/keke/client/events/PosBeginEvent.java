package com.risetek.keke.client.events;

import com.risetek.keke.client.keke;

public class PosBeginEvent extends PosEvent {

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
	    Kekes begin = new Kekes(Kekes.grand, "从这里开始", "p2");
	    (begin = new Kekes(begin, "您不会厌倦的...", "p3")).setDefaultOption();
	    (begin = new PoemKeke(begin, "我们来展现一种能力", "p2")).setDefaultOption();
	    (begin = new GameOverKeke(begin, "看见我的写给您的话了吗？现在结束了！", "p3")).setDefaultOption();
        context().eventStack().nextEvent();
*/        
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
