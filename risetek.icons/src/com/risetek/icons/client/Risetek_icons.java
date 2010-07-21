package com.risetek.icons.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.risetek.icons.client.ui.Icons;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		Icons icon = new Icons();

		Window.enableScrolling(false);
		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(icon);
	
	}
}
