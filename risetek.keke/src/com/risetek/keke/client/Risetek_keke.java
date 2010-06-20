package com.risetek.keke.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.events.PosInitEvent;
import com.risetek.keke.client.ui.KekesComposite;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_keke implements EntryPoint, UiKeke {
	interface localUiBinder extends UiBinder<Widget, Risetek_keke> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	@UiField public static KekesComposite kekeComposite;
	int kekesIndex = 0;
    PosContext context = new PosContext();
	
	public void onModuleLoad() {
		Window.enableScrolling(false);
		Widget outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    root.add(outer);
	    context.loadEvent(new PosInitEvent());
	    context.eventStack().nextEvent();
	}
}
