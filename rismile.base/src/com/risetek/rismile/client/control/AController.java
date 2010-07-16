package com.risetek.rismile.client.control;

import com.risetek.rismile.client.view.IRisetekView;

public abstract class AController {

	public abstract void disablePrivate();
	public abstract void enablePrivate();
	
	public abstract IRisetekView getView();
	
	public abstract void doAction(int keyCode);

}
