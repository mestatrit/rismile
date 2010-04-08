package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.Risetek_keke;

public class CardKeke extends Kekes {

	public CardKeke(Kekes parent, String name, String img) {
		super(parent, name, img);
	}

	boolean carded = false;
	
	public void ability()
	{
		GWT.log("输出能力展示");
		if( carded )
		{
			super.ability();
			Risetek_keke.logger.setInnerText("刷卡完成了！");
		}
		else
		{
			Risetek_keke.logger.setInnerText("您还没完成刷卡！");
		}
	}

	public void card()
	{
		name = "可可银行卡";
		carded = true;
	}

}
