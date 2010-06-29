package com.risetek.keke.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.risetek.keke.client.context.ClientEventBus;
/*
 * 一个用于人机界面的输入控制组件。
 */
public class ControlPanel extends Composite {
	
	DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
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

		dock.setWidth("300px");
		dock.setHeight("400px");
		
//		dock.addNorth(card, 3);
		dock.addNorth(new HTML("控制台"), 4);
		dock.addNorth(card, 4);
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
//				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDUPEvent());
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_KEY_UP));
			}
	    });
		
	    down.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDDOWNEvent());
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_KEY_DOWN));
			}
	    });
		
	    left.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDLEFTEvent());
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_KEY_LEFT));
			}
	    });
		
	    right.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDRIGHTEvent());
				ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_KEY_RIGHT));
			}
	    });
		
		dock.add(dirButton);
	}
}
