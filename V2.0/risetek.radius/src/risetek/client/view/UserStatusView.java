package risetek.client.view;

import risetek.client.control.RadiusUserStatusController;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.log.client.model.RismileTable;
import com.risetek.rismile.log.client.view.RismileTableView;

public class UserStatusView extends RismileTableView {

    private final static int rowCount = 20;
    public final static int colCount = 8;
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击查看设备详细信息."
	};
    public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public  RadiusUserStatusController control;

	public UserStatusView(RadiusUserStatusController control){
		this(new String[colCount], null, rowCount, control);
		grid.addClickHandler(control.new TableAction());
	}
	
	public UserStatusView(String[] columns, String[] columnStyles, int rowCount, RadiusUserStatusController control) {
		super(columns, columnStyles, rowCount, control);
		this.control = control;
		
//		Button FilterUser = new Button("过滤信息", control.new FilterUserAction());
//		addToolButton(FilterUser);
	}
	
	public void onLoad()
	{
		control.load();
	}

    public void render(RismileTable table)
	{
    	String[][] d = table.getData();
    	for(int row = 0;row < rowCount; row++){
    		for(int col = 0; col < colCount; col++){
    			int index = row*colCount + col;
    			if(index >= d.length){
    				break;
    			}
    			grid.setText(row, col, d[index][0]);
    			if("0".equalsIgnoreCase(d[index][1])){
    				grid.getCellFormatter().setStyleName(row, col, "green");
//    			} else if("1".equalsIgnoreCase(d[index][1])){
//    				grid.getCellFormatter().setStyleName(row, col, "green");
    			} else if("2".equalsIgnoreCase(d[index][1])){
    				grid.getCellFormatter().setStyleName(row, col, "red");
    			}
    		}
    	}
	}
    
	@Override
	public Grid getGrid() {
		if (grid != null){
			return grid;
		}
		return new MouseEventGrid();
	}
	
	class MouseEventGrid extends Grid {

		public MouseEventGrid(){
			super();
			// this.setBorderWidth(1);
			setCellPadding(2);
			setCellSpacing(0);
			// 增加鼠标事件。
			sinkEvents(Event.MOUSEEVENTS);
		}
		
		public void onBrowserEvent(Event event) {
			
			Element td = DOM.eventGetTarget(event);
	        Element tr = DOM.getParent(td);
	        Element body = DOM.getParent(tr);
	        
	        int row = DOM.getChildIndex(body, tr);
	        int col = DOM.getChildIndex(tr, td);

//	        String index = getText(row, 0);
//
//	        try{
//		        int id = Integer.parseInt(index);
//		        if( id <= 0) return;
//	        }catch( NumberFormatException nfe){
//	        	return;
//	        }
			
			switch (DOM.eventGetType(event))
	        {
	        case Event.ONMOUSEOVER:
	            onMouseOver(td, row, col);
	            break;
	        case Event.ONMOUSEOUT:
	       	// 处理这个事件是为了让弹出菜单下面的颜色恢复正常。
	        case Event.ONCLICK:
	        	onMouseOut(td, row, col);
	        	break;
	        }
	        super.onBrowserEvent(event);
		}



		public void onMouseOut(Element td, int row, int col) {
			DOM.setStyleAttribute(td, "color", "");
			DOM.setStyleAttribute(td, "cursor", "pointer");
			setInfo(banner_tips);
            
            String d[][] = control.getTable().getData();
            int index = row * colCount + col;
            if(index>=d.length){
            	return;
            }
            if( "0".equalsIgnoreCase(d[index][1])){
            	getCellFormatter().setStyleName(row, col, "green");
            } else if( "2".equalsIgnoreCase(d[index][1])){
            	getCellFormatter().setStyleName(row, col, "red");
            } else {
            	getCellFormatter().setStyleName(row, col, "normal");
            }
		
		}

		public void onMouseOver(Element td, int row, int col) {
			String d[][] = control.getTable().getData();
            int index = row * colCount + col;
            if(index>=d.length){
            	return;
            }
			getCellFormatter().setStyleName(row, col, "light");
			DOM.setStyleAttribute(td, "color", "red");
			DOM.setStyleAttribute(td, "cursor", "hand");
			setInfo(banner_text[0]);
		}
    	
    }
}
