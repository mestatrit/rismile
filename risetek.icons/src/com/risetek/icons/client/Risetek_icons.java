package com.risetek.icons.client;

import libfeeder.client.LibFeeder;

import com.risetek.icons.client.ui.IconUpSink;
import com.risetek.icons.client.ui.IconsSink;
import com.risetek.icons.client.ui.TreeSink;
import com.risetek.icons.client.ui.TreeUpSink;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons extends LibFeeder {


	@Override
	public void loadSinks() {
//		LibFeeder.list.addSink(IconsSink.init());
//		LibFeeder.list.addSink(IconUpSink.init());
		LibFeeder.list.addSink(TreeSink.init());
		LibFeeder.list.addSink(TreeUpSink.init());
	}
}
