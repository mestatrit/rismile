package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.Risetek_keke;

public class XinYunKeke extends Kekes {

	public XinYunKeke(Kekes parent, String name, String img) {
		super(parent, "11232321321312", img);
	}

	public void ability()
	{
		super.ability();
		GWT.log("输出能力展示");
		Risetek_keke.logger.setInnerText("我一直都是幸运的！");
	}
}
