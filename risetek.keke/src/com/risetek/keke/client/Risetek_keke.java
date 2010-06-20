package com.risetek.keke.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
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
	/**
	 * This is the entry point method.
	 */
	
	@UiField HTMLPanel skins;
	@UiField public static KekesComposite kekeComposite;

	@UiField FocusPanel focus;
	
	int kekesIndex = 0;
    PosContext context = new PosContext();
	
	public void onModuleLoad() {
		Window.enableScrolling(false);

		Widget outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
		
//	    root.add(new KeyAcceptedWidget(outer.getElement()));
	    root.add(outer);
	    context.loadEvent(new PosInitEvent());
	    context.eventStack().nextEvent();
	    focus.addKeyDownHandler(new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				GWT.log("key down");
				
			}});
	}
}
