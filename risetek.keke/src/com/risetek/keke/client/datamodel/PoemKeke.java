package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.Risetek_keke;

public class PoemKeke extends Kekes {

	public PoemKeke(Kekes parent, String name, String img) {
		super(parent, name, img);
	}

	public void ability()
	{
		super.ability();
		GWT.log("输出能力展示");
		Risetek_keke.logger.setInnerText("这是我写给您的话！");
	}
}
