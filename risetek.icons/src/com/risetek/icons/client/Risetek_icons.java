package com.risetek.icons.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.risetek.icons.client.ui.IconList;
import com.risetek.icons.client.ui.Icons;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons implements EntryPoint {
	private final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		Icons icon = new Icons();
		//IconList icon = new IconList();
		Window.enableScrolling(false);
		RootLayoutPanel root = RootLayoutPanel.get();

		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.addNorth(new HTML("Icons List"), 20);
		outer.add(icon);
		root.add(outer);
	
	}
}
