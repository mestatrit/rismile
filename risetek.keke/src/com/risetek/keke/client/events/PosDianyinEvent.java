package com.risetek.keke.client.events;

import com.risetek.keke.client.datamodel.Kekes;

public class PosDianyinEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
		Kekes begin;
	    begin = new Kekes(Kekes.grand, "爱看电影吗？", "p3");
	    Kekes yinpian = new Kekes(begin, "影片？", "p2");
	    yinpian.setDefaultOption();
	    new Kekes(yinpian, "拯救大兵可可", "p2");
	    new Kekes(yinpian, "看不见的战线", "p3");
	    (new Kekes(yinpian, "英雄白跑腿", "p4")).setDefaultOption();
	    new Kekes(yinpian, "未来时代", "p5");
	    
	    begin = new Kekes(begin, "院线？", "p3");
	    new Kekes(begin, "红星路坝坝电影院", "p5");
	    (new Kekes(begin, "火星叔叔的小屋", "p3")).setDefaultOption();
	    new Kekes(begin, "高级大广场", "p4");
	    new Kekes(begin, "路边店", "p2");
        context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
