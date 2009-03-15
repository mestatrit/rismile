package com.risetek.rismile.client.utils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class UI {
	public static String createHeaderHTML(String caption) {
		Grid captionGrid = new Grid(1,2);
		captionGrid.setWidth("100%");

		HTML captionHtml = new HTML(caption);
		captionHtml.setStyleName("stack-caption");
		HTML figureHtml = new HTML("&nbsp;");
		figureHtml.setStyleName("stack-figure");

		captionGrid.setWidget(0, 0, captionHtml);
		captionGrid.getCellFormatter().setWidth(0, 0, "90%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_TOP);
		captionGrid.setWidget(0, 1, figureHtml);
		captionGrid.getCellFormatter().setWidth(0, 1, "10%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);

		String innerHtml = DOM.getInnerHTML(captionGrid.getElement());

		String captionInnerHtml = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>"
				+ innerHtml + "</b></td></tr></table>";

		return "<table align='left' cellpadding='0' cellspacing='0'><tbody>"
				+ "<tr><td class='box-10'>&nbsp;</td>" + "<td class='box-10'>&nbsp;</td>"
				+ "<td class='box-10'>&nbsp;</td>" + "</tr><tr>" + "<td></td>"
				+ "<td class='box-11'>" + captionInnerHtml + "</td>"
				+ "<td></td>" + "</tr></tbody></table>";
	}
}
