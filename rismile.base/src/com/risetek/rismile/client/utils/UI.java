package com.risetek.rismile.client.utils;


public class UI {
/*	
	public static String createHeaderHTML(String caption) {
		Grid captionGrid = new Grid(1,2);
		captionGrid.setWidth("100%");

		HTML captionHtml = new HTML(caption);
		HTML figureHtml = new HTML("&nbsp;");
		figureHtml.setStyleName("stack-figure");

		captionGrid.setWidget(0, 0, captionHtml);
		captionGrid.getCellFormatter().setWidth(0, 0, "90%");
		captionGrid.setWidget(0, 1, figureHtml);
		captionGrid.getCellFormatter().setWidth(0, 1, "10%");

		String innerHtml = DOM.getInnerHTML(captionGrid.getElement());

		return "<table width='100%' cellpadding='0' cellspacing='0'><tr><td>" + innerHtml + "</td></tr></table>";
	}
	*/
	
	public static String createHeaderHTML(String caption) {
		return "<table width='100%' cellpadding='0' cellspacing='0'><tr><td class='Stack_Title'>" + caption + "</td></tr></table>";
	}
}
