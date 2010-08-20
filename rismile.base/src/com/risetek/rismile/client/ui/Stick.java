package com.risetek.rismile.client.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

public class Stick extends Composite {
	public Stick(String imgName, String message) {
		final Grid outgrid = new Grid(1, 1);
		outgrid.setCellPadding(5);
		outgrid.setCellSpacing(5);
		outgrid.setWidth("100%");
		outgrid.setWidget(0, 0, new HTML(message));
		initWidget(outgrid);
		
		Style style = outgrid.getCellFormatter().getElement(0,0).getStyle();
//		style.setColor("black");
		style.setBorderColor("yellow");
		style.setBorderStyle(BorderStyle.SOLID);
		style.setBorderWidth(1, Unit.PX);
		style.setOverflow(Overflow.HIDDEN);
//		style.setBackgroundColor(UIConfig.StickBackground);
		
		//style.setPadding(3, Unit.PX);
		style.setFontSize(14, Unit.PX);
		//style.setOpacity(0.9);
	}
}
