package risetek.client.view;

import risetek.client.control.RadiusUserController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserView extends RismileTableView {
	
	private final static String[] columns = {"序号","终端号","用户名称","口令","分配地址","备注"};
	private final static String[] columnStyles = {"uid","imsi","username","password","ipaddress","note"};
	
	private final static int rowCount = 20;	
	
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改IMSI号码.",
		"点击修改用户名称.",
		"点击修改口令.",
		"点击修改分配地址.",
		"点击修改备注信息."
	};

	public  RadiusUserController control;

	public UserView(RadiusUserController control) {
		this(columns, columnStyles, rowCount, control);
		grid.addClickHandler(control.new TableAction());
	}
	
	private UserView(String[] columns, String[] columnStyles, int rowCount, RadiusUserController control)
	{
		super(columns, columnStyles, rowCount, control);
		this.control = control;
		
		Button LocalUser = new Button("定位用户", control.new AddUserAction());
		addToolButton(LocalUser);
		
		Button addNewUser = new Button("添加用户", control.new AddUserAction());
		addToolButton(addNewUser);
		
		Button downloadButton = new Button("导出文件");
		super.addToolButton(downloadButton);
		downloadButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event) {
				Window.open("forms/exportusers", "_self", "");
			}
		});
		
		Button clearButton = new Button("用户总清", control.new EmptyAction());
		addToolButton(clearButton);
	}
	
	public Grid getGrid() {
		if (grid != null)	return grid;

		return new GreenMouseEventGrid();
	}
	
	public void onLoad()
	{
		control.load();
	}

    public void render(RismileTable table)
	{
    	super.render(table);
    	String[][] d = table.getData();
    	for(int loop = 0; loop < d.length; loop++)
    		if("1".equalsIgnoreCase(d[loop][6]))
    			grid.getRowFormatter().setStyleName(loop+1, "green");
	}
    
    class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOut(Element td, int column) {
			DOM.setStyleAttribute(td, "color", "");
			DOM.setStyleAttribute(td, "cursor", "pointer");
			setInfo("");

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            String d[][] = control.getTable().getData();

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
}
