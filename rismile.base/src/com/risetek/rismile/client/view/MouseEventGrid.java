package com.risetek.rismile.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.conf.UIConfig;

public abstract class MouseEventGrid extends Grid {

	public MouseEventGrid(){
		super();
//		setBorderWidth(1);
//		setCellPadding(2);
		setCellSpacing(0);
		setSize("100%", "100%");
		sinkEvents(Event.MOUSEEVENTS);
	}
	
	@Override
	public void onBrowserEvent(Event event)
    {
	    Element td = getEventTargetCell(event);
        if (null == td )   return;

        Element tr = DOM.getParent(td);
        if( null == tr ) return;
        
        Element body = DOM.getParent(tr);
        if( null == body ) return;

        int row = DOM.getChildIndex(body, tr);
        // 开始行是标题
        if(row == 0) return;

//        GWT.log("Row: " +row);
        // 这是为了确保该行存在数据。
        String index = getText(row, 0);
        try{
	        int id = Integer.parseInt(index);
	        if( id <= 0) return;
        }catch( NumberFormatException nfe){
        	return;
        }
        
        int column = DOM.getChildIndex(tr, td);
        switch (DOM.eventGetType(event))
        {
        case Event.ONMOUSEOVER:
        	getRowFormatter().getElement(row).getStyle().setBackgroundColor(UIConfig.GRIDTABLEOVERCOLOE);
        	onMouseOver(td, column);
            break;
        case Event.ONMOUSEOUT:
       	// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
        case Event.ONCLICK:
        	getRowFormatter().getElement(row).getStyle().clearBackgroundColor();
        	onMouseOut(td, column);
        	break;
        }
        super.onBrowserEvent(event);
    }
	
	public abstract void onMouseOver(Element td, int column);
	
	public abstract void onMouseOut(Element td, int column);
}
