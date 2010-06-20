package com.risetek.keke.client.PosEvents;

import com.risetek.keke.client.datamodel.Kekes;
import com.risetek.keke.client.datamodel.XinYunKeke;

public class PosCaipiaoEvent extends PosEvent {

	@Override
	public void clear() {

	}

	@Override
	public void engage(int value) throws PosException {
		Kekes begin;
	    begin = new Kekes(Kekes.grand, "试一试手气", "p4");
	    begin = new Kekes(begin, "随机生产幸运号码", "p5");
	    begin = new XinYunKeke(begin, "随机生产幸运号码", "p5");
	    new Kekes(begin, "可可卡支付", "p2");
	    (new Kekes(begin, "可可币支付", "p2")).setDefaultOption();
	    new Kekes(begin, "可可银行支付", "p2");
        context().eventStack().nextEvent();
	}

	@Override
	public boolean validTransition(String event) {
		return false;
	}

}
