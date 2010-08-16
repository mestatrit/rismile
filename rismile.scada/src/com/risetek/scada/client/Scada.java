package com.risetek.scada.client;

public class Scada extends Entry {

	@Override
	public void loadSinks() {
		Entry.SinkHeight = "500px";
		list.addSink(RisetekHomeSink.init());
		//list.addSink(dataPointsSink.init());
		//list.addSink(cameraSink.init());
		//list.addSink(mapsSink.init());
		list.addSink(licenseSink.init());
	}
}
