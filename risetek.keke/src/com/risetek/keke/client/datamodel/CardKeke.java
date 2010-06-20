package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.PosContext;

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
			PosContext.Log("刷卡完成了！");
		}
		else
		{
			PosContext.Log("您还没完成刷卡！");
		}
	}

	public void card()
	{
		name = "可可银行卡";
		carded = true;
	}

}
