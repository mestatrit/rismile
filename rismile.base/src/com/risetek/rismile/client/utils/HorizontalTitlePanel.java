package com.risetek.rismile.client.utils;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HorizontalTitlePanel extends Composite {
	public HorizontalTitlePanel(String title) {
		Style style;
		SimplePanel outer = new SimplePanel();
		HorizontalPanel titlePanel = new HorizontalPanel();
	    outer.add(titlePanel);
	    outer.setSize("100%", "100%");
	    initWidget(outer);

	    titlePanel.setWidth("100%");
	    titlePanel.setHeight("100%");
	    titlePanel.setSpacing(0);
	    //titlePanel.setBorderWidth(1);
	    titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    HTML htmlTitle = new HTML(title);
	    htmlTitle.getElement().getStyle().setMarginLeft(10, Unit.PX);
	    titlePanel.add(htmlTitle);
	    titlePanel.setStyleName("Stack_Title");
	    
	    style = titlePanel.getElement().getStyle();
	    style.setFontWeight(FontWeight.BOLD);
		
		style = getElement().getStyle();
		style.setBorderColor("#999");
		style.setBorderStyle(BorderStyle.SOLID);
		
	}
	
	public HorizontalTitlePanel(String title, boolean isHeader) {
		this(title);
		setStyleName("stacktitle");
		if( isHeader )
			addStyleDependentName("head");
	}

	public HorizontalTitlePanel(String title, boolean isHeader, String height) {
		this(title);
		setStyleName("title");
		if( isHeader )
			addStyleDependentName("head");
		setHeight(height);
	}

}