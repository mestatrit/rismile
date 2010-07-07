package com.risetek.keke.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.risetek.keke.client.context.ClientEventBus;
/*
 * 一个用于人机界面的输入控制组件。
 */
public class ControlPanel extends Composite {
	
//	DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
	DockPanel dock = new DockPanel();
	Grid dirButton = new Grid(3,3);
	
	Button up = new Button("上");
	Button down = new Button("下");
	Button left = new Button("左");
	Button right = new Button("右");

	Button card = new Button("刷卡");

	public ControlPanel()
	{
		initWidget(dock);
		setStyleName("HID");
		dock.setSize("100%", "100%");
//		dock.setWidth("200px");
//		dock.setHeight("300px");
		
//		dock.addNorth(card, 3);
		dock.add(new HTML("控制台"), DockPanel.NORTH);
//		dock.addNorth(card, 60);
		
		dirButton.setWidget(0, 1, up);
		dirButton.setWidget(1, 0, left);
		dirButton.setWidget(1, 2, right);
		dirButton.setWidget(2, 1, down);

	    card.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDCARDEvent("1234567890"));
			}
	    });
		
		
	    up.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_KEY_UP);
			}
	    });
		
	    down.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_KEY_DOWN);
			}
	    });
		
	    left.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_KEY_LEFT);
			}
	    });
		
	    right.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ClientEventBus.fireControlKey(ClientEventBus.CONTROL_KEY_RIGHT);
			}
	    });
		
//		dock.add(dirButton);
		dock.add(dirButton, DockPanel.SOUTH);
	}
}
