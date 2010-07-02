package com.risetek.keke.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class D3View {
	interface localUiBinder extends UiBinder<Widget, D3View> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	public @UiField KekesComposite kekeComposite;
	public static @UiField LoggerComposite logger;

	public D3View() {
		Window.enableScrolling(false);
		Widget outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    root.add(outer);
	}
}
