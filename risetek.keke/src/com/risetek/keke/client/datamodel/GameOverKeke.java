package com.risetek.keke.client.datamodel;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.PosContext;

public class GameOverKeke extends Kekes {

	public GameOverKeke(Kekes parent, String name, String img) {
		super(parent, name, img);
	}

	public void ability()
	{
		GWT.log("输出能力展示");
		PosContext.Log("这个过程完成了！");
		
		Kekes.current = Kekes.grand;
		
	}
}
