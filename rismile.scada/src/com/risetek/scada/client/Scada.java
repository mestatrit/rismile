package com.risetek.scada.client;

public class Scada extends Entry {

	@Override
	public void loadSinks() {
		Entry.SinkHeight = "500px";
		list.addSink(RisetekHomeSink.init());
	}
}
