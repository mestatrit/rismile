package risetek.client.view;

import risetek.client.control.RadiusUserController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserView extends RismileTableView  implements IRisetekView {
	
	private final static String[] columns = {"序号","终端号码","用户名称","口令","分配地址","备注"};
	private final static String[] columnStyles = {"uid","imsi","username","password","ipaddress","note"};
	
	private final static int rowCount = 20;
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改终端号码.",
		"点击修改用户名称.",
		"点击修改用户口令.",
		"点击修改分配地址.",
		"点击修改备注信息."
	};

	
	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	private final Button FilterUser = new Button("过滤信息", new RadiusUserController.FilterUserAction());
	private final Button addNewUser = new Button("添加用户", new RadiusUserController.AddUserAction());
	private final Button downloadButton = new Button("导出文件", new ClickHandler()
	{
		public void onClick(ClickEvent event) {
			Window.open("forms/exportusers", "_self", "");
		}
	});
	private final Button clearButton = new Button("用户总清", new RadiusUserController.EmptyAction());
//	private final Button refreshButton = new Button("刷新", new RadiusUserController.refreshAction());
	
	public UserView() {
		super(columns, columnStyles, rowCount);
		addToolButton(FilterUser);
		addToolButton(addNewUser);
		addToolButton(downloadButton);
		addToolButton(clearButton);
		// addToolButton(refreshButton);
		grid.addClickHandler(new RadiusUserController.TableAction());
	}
	
	public Grid getGrid() {
		if (grid != null)	return grid;

		return new GreenMouseEventGrid();
	}
	
	public void onLoad()
	{
		RadiusUserController.load();
	}

    public void render(RismileTable table, boolean level)
	{
    	super.render(table);
    	String[][] d = table.getData();
    	for(int loop = 0; loop < d.length; loop++) {
    		if( level ) {
    			if("0".equalsIgnoreCase(d[loop][6]))
        			grid.getRowFormatter().setStyleName(loop+1, "green");
    		}
    		else if("1".equalsIgnoreCase(d[loop][6]))
    			grid.getRowFormatter().setStyleName(loop+1, "green");
    	}
	}
    
    class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOut(Element td, int column) {
			DOM.setStyleAttribute(td, "color", "");
			DOM.setStyleAttribute(td, "cursor", "pointer");
			setInfo(banner_tips);

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            String d[][] = RadiusUserController.INSTANCE.getTable().getData();

            if( RadiusUserController.INSTANCE.getTable().LEVEL ) {
                if( "0".equalsIgnoreCase(d[row-1][6]))
                	getRowFormatter().setStyleName(row, "green");
            } else
            if( "1".equalsIgnoreCase(d[row-1][6]))
            	getRowFormatter().setStyleName(row, "green");
		
		}

		@Override
		public void onMouseOver(Element td, int column) {
			DOM.setStyleAttribute(td, "color", "red");
			DOM.setStyleAttribute(td, "cursor", "hand");
			setInfo(banner_text[column]);
		}
    	
    }
    
	public void disablePrivate() {
		addNewUser.setEnabled(false);
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);

		grid.unsinkEvents( Event.ONCLICK );
		grid.unsinkEvents( Event.ONMOUSEOVER );
		grid.unsinkEvents( Event.ONMOUSEOUT );
	}

	public void enablePrivate() {
		addNewUser.setEnabled(true);
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);

		grid.sinkEvents( Event.ONCLICK);
		grid.sinkEvents( Event.ONMOUSEOVER );
		grid.sinkEvents( Event.ONMOUSEOUT );
	}
    
}
