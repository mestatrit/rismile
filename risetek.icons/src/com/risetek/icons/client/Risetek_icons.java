package com.risetek.icons.client;

import libfeeder.client.LibFeeder;

import com.risetek.icons.client.ui.IconUpSink;
import com.risetek.icons.client.ui.IconsSink;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons extends LibFeeder {


	@Override
	public void loadSinks() {
		LibFeeder.list.addSink(IconsSink.init());
		LibFeeder.list.addSink(IconUpSink.init());
	}
}
