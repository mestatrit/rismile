package com.risetek.rismile.client.utils;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.HeartbeatEvent;
import com.risetek.rismile.client.RismileContext.RismileHandler;
import com.risetek.rismile.client.conf.UIConfig;

public class MessageConsole extends Composite {
	
	private final VerticalPanel panel = new VerticalPanel();
	
	private int fullspeed = (UIConfig.NETWORKSPEED_UP - UIConfig.NETWORKSPEED_LOW) / UIConfig.NETWORKSPEED_DELTA;
	private final Grid netspeedPanel = new Grid(1, fullspeed);
	
	private final static HTML hbMessage = new HTML();
	private final static HTML message = new HTML();
	public MessageConsole() {
		//panel.setBorderWidth(1);
		panel.setSpacing(0);
		panel.setWidth("100%");
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Style style = panel.getElement().getStyle(); 
		style.setColor("crimson");
		style.setFontSize(10, Unit.PX);
		
		for(int loop = 0; loop < fullspeed; loop++) {
			netspeedPanel.setWidget(0, loop, new SimplePanel());
		}

		netspeedPanel.setCellPadding(0);
		netspeedPanel.setCellSpacing(1);
		netspeedPanel.setWidth("260px");
		netspeedPanel.setHeight("6px");
		RismileContext.addHeartbeatHandler(new RismileHandler(){
			@Override
			public void onHearbeatOK(HeartbeatEvent event) {
				showSpeed(Heartbeat.networkspeed);
			}
		});
		
		panel.add(netspeedPanel);
		panel.add(hbMessage);
		panel.add(message);

		panel.setCellHeight(hbMessage, "18px");
		panel.setCellHeight(message, "18px");
		
		initWidget(panel);
	}
	
	static {
		RismileContext.addHeartbeatHandler(new RismileHandler(){
			@Override
			public void onHearbeatOK(HeartbeatEvent event) {
				MessageConsole.setHbText(event.getResults());
			}
		});
	}
	
	private static Timer clearTimer; 

	public static void setText(String text){
		message.setText(text);
		startTimer();
	}
	public static void startTimer(){
		if(clearTimer != null) clearTimer.cancel();
		clearTimer = new Timer(){
			@Override
			public void run() {
				setText(" ");
			}
		};
		clearTimer.schedule(UIConfig.mesageDelayHideTime);
	}
	private static void setHbText(String text){
		hbMessage.setText(text);
	}

	public void showSpeed(int speed) {
		int delta = (UIConfig.NETWORKSPEED_UP - speed) / UIConfig.NETWORKSPEED_DELTA;
		for( int loop = 0; loop < fullspeed; loop++ ) {
			Style style = netspeedPanel.getCellFormatter().getElement(0, loop).getStyle();
			if( loop < delta )
				style.setBackgroundColor(UIConfig.SPEED_GREE_COLOR);
			else
				style.setBackgroundColor("#D0C0B0");
		}
	}
}
