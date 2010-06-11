package com.risetek.keke.client.ui.event;

import com.google.gwt.event.shared.GwtEvent;

public class UiControlKeyEvent extends GwtEvent<UiControlKeyHandler> {
	private static Type<UiControlKeyHandler> TYPE;

	public static Type<UiControlKeyHandler> getType() {
		return TYPE != null ? TYPE
				: (TYPE = new Type<UiControlKeyHandler>());
	}

	@Override
	protected void dispatch(UiControlKeyHandler handler) {
		handler.onUiControlKeyPressed(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UiControlKeyHandler> getAssociatedType() {
		return getType();
	}

}
