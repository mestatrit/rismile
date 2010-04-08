package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.Risetek_keke;

public class GameOverKeke extends Kekes {

	public GameOverKeke(Kekes parent, String name, String img) {
		super(parent, name, img);
	}

	public void ability()
	{
		GWT.log("输出能力展示");
		Risetek_keke.logger.setInnerText("这个过程完成了！");
		
		Kekes.current = Kekes.grand;
		
	}
}
