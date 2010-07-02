package com.risetek.keke.client;

import com.google.gwt.core.client.EntryPoint;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.ui.D3View;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_keke implements EntryPoint {
	
	public void onModuleLoad() {

	    // 构造上下文，并将视图传递给上下文控制。
	    new PosContext(new D3View().kekeComposite);
		
	}
}
