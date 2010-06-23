package com.risetek.keke.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.ui.KekesComposite;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_keke implements EntryPoint {
	interface localUiBinder extends UiBinder<Widget, Risetek_keke> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	@UiField KekesComposite kekeComposite;
	int kekesIndex = 0;
	
	public void onModuleLoad() {
		Window.enableScrolling(false);
		Widget outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    root.add(outer);
	    /*
	    DOM.addEventPreview(new EventPreview(){

			@Override
			public boolean onEventPreview(Event event) {
				// TODO Auto-generated method stub
				return false;
			}});
	    */
	    
	    // 构造上下文，并将视图传递给上下文控制。
	    new PosContext(kekeComposite);
	}
}
