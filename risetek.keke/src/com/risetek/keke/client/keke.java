package com.risetek.keke.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.keke.client.PosEvents.PosEvent;

public class keke extends Composite {
	interface ProudceUiBinder extends UiBinder<Widget, keke> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	
	
	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
	}

	public static Images imgSrc = GWT.create(Images.class);
	
	@UiField SpanElement brief;
	@UiField Image img;

	public PosEvent event;
	public keke(String title, String imgName, PosEvent event) {
		this.event = event;
		Widget w = uiBinder.createAndBindUi(this);
		
		if( imgName.equalsIgnoreCase("p3"))
			img.setResource(imgSrc.p3());
		else if( imgName.equalsIgnoreCase("p2"))
			img.setResource(imgSrc.p2());
		else if( imgName.equalsIgnoreCase("p4"))
			img.setResource(imgSrc.p4());
		else
			img.setResource(imgSrc.p5());

		this.brief.setInnerText(title);
		initWidget(w);
	}
	
	public void onLoad() {
	}
}
