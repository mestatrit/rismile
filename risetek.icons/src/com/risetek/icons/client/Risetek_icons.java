package com.risetek.icons.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.risetek.icons.client.ui.IconList;
import com.risetek.icons.client.ui.Icons;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_icons implements EntryPoint {
	private final DockLayoutPanel outer = new DockLayoutPanel(Unit.PX);
	
	Icons icon = new Icons();
	IconList iconlist = new IconList();
	
	private Composite curPanel = null;
	
	private void showPanel(Composite newOne ) {
		if( curPanel != null ) {
			curPanel.setVisible(false);
			outer.remove(curPanel);
		}
		
		curPanel = newOne;
		outer.add(curPanel);
		curPanel.setSize("100%", "100%");
		curPanel.setVisible(true);
	}
	
	private final Button Toggle = new Button("Press to Change View", new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			if( curPanel == icon )
				showPanel(iconlist);
			else
				showPanel(icon);
		}});

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Window.enableScrolling(false);
		RootLayoutPanel root = RootLayoutPanel.get();
		outer.setHeight("100%");
		outer.setWidth("100%");
		outer.addNorth(Toggle, 40);
		showPanel(iconlist);
		root.add(outer);
	
	}
}
