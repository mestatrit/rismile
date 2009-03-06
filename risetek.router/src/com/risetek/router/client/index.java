package com.risetek.router.client;

import com.risetek.rismile.client.Entry;
import com.risetek.rismile.system.client.RisetekHomeSink;
import com.risetek.rismile.system.client.RisetekSystemSink;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class index  extends  Entry{

	public void loadSinks() {
		list.addSink(RisetekHomeSink.init());
		//list.addSink(SystemToolSink.init());
		list.addSink(RisetekSystemSink.init());
		//list.addSink(EthSink.init());
		//list.addSink(DialerSink.init());
		list.addSink(InterfaceSink.init());
		//list.addSink(LinkSink.init());
		//list.addSink(WirelessSink.init());
		
	}
	protected void showInfo() {
		show(list.find("Home"), false);
	}
}
