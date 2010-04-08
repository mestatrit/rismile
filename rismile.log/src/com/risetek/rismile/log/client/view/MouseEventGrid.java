package com.risetek.rismile.log.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;

public abstract class MouseEventGrid extends Grid{

	public MouseEventGrid(){
		super();
		// this.setBorderWidth(1);
		setCellPadding(2);
		setCellSpacing(0);
		// 增加鼠标事件。
		sinkEvents(Event.MOUSEEVENTS);
	}
	
	public void onBrowserEvent(Event event)
    {
	    Element td = DOM.eventGetTarget(event);
/*		
		Element td = getEventTargetCell(event);
        if (td == null) {
          return;
        }
*/        
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
        	getRowFormatter().setStyleName(row, "light");
            onMouseOver(td, column);
            break;
        case Event.ONMOUSEOUT:
       	// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
        case Event.ONCLICK:
        	getRowFormatter().setStyleName(row, "normal");
        	onMouseOut(td, column);
        	break;
        }
        super.onBrowserEvent(event);
    }
	
	public abstract void onMouseOver(Element td, int column);
	
	public abstract void onMouseOut(Element td, int column);
}
