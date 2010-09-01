package com.risetek.icons.client;

import libfeeder.client.LibFeeder;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		LibFeeder.list.addSink(IconsSink.init());
	}
}
