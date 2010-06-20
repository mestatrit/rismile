package com.risetek.keke.client.ui;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.risetek.keke.client.context.ClientEventBus;

public class KeyboardOuterPanel extends FocusPanel {

	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
	    return addDomHandler(handler, KeyDownEvent.getType());
	}

	public KeyboardOuterPanel() {
	    addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if( event.isDownArrow() )
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDDOWNEvent());
				else if( event.isUpArrow())
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDUPEvent());
				else if( event.isRightArrow() )
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDRIGHTEvent());
				else if( event.isLeftArrow())
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDLEFTEvent());
			}});
	}
	
}
