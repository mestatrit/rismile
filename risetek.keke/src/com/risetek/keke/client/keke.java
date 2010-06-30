package com.risetek.keke.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.keke.client.context.UiKeke;
import com.risetek.keke.client.resources.IconManage;

public class keke extends Composite {
	interface ProudceUiBinder extends UiBinder<Widget, keke> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	
	@UiField SpanElement brief;
	@UiField Image img;

	public keke(String title, String imgName) {
		Widget w = uiBinder.createAndBindUi(this);
		
		img.setResource(IconManage.getIcon(imgName));

//		img.setPixelSize(UiKeke.iconWidth, UiKeke.iconHeight);
		this.brief.setInnerText(title);
		w.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		initWidget(w);
	}
}
