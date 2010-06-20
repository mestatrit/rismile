package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.PosContext;

public class PoemKeke extends Kekes {

	public PoemKeke(Kekes parent, String name, String img) {
		super(parent, name, img);
	}

	public void ability()
	{
		super.ability();
		GWT.log("输出能力展示");
		PosContext.Log("这是我写给您的话！");
	}
}
