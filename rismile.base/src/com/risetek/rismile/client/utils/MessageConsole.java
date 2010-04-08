package com.risetek.rismile.client.utils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;

public class MessageConsole{
	
	private static Element message = DOM.getElementById("message");
	private static Element hbMessage = DOM.getElementById("hbMessage");
	private static Timer clearTimer; 

	public static void setText(String text){
		DOM.setInnerText(message, text);
		startTimer();
	}
	public static void startTimer(){
		if(clearTimer != null) clearTimer.cancel();
		clearTimer = new Timer(){
			public void run() {
				setText("");;
			}
		};
		clearTimer.schedule(20000);
	}
	public static void setHbText(String text){
		DOM.setInnerText(hbMessage, text);
	}

}
