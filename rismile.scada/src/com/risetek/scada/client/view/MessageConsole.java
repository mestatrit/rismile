package com.risetek.scada.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class MessageConsole{
	
	private static Element message = DOM.getElementById("message");

	public static void setText(String text){
		DOM.setInnerText(message, text);
	}
}
