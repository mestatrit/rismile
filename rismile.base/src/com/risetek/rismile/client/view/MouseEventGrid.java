package com.risetek.rismile.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;

public abstract class MouseEventGrid extends Grid{

	public MouseEventGrid(){
		super();
		// 增加鼠标事件。
		sinkEvents(Event.MOUSEEVENTS);
	}
	
	private void light(int row, boolean onoff){
    	getRowFormatter().setStyleName(row, onoff ? "light" : "normal");
	}
	
	public void onBrowserEvent(Event event)
    {
		Element td = getEventTargetCell(event);
        if (td == null) {
          return;
        }
        Element tr = DOM.getParent(td);
        Element body = DOM.getParent(tr);
        int row = DOM.getChildIndex(body, tr);
        if(row == 0) return;
        int column = DOM.getChildIndex(tr, td);

        String index = getText(row, 0);

        try{
	        int id = Integer.parseInt(index);
	        if( id <= 0) return;
        }catch( NumberFormatException nfe){
        	return;
        }
        
        switch (DOM.eventGetType(event))
        {
        case Event.ONMOUSEOVER:
        	light(row , true);
            onMouseOver(td, column);
            break;
        case Event.ONMOUSEOUT:
       	// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
        case Event.ONCLICK:
        	light(row , false);
        	onMouseOut(td, column);
        	break;
        }
        super.onBrowserEvent(event);
    }
	
	public abstract void onMouseOver(Element td, int column);
	
	public abstract void onMouseOut(Element td, int column);
}
