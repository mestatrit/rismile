package risetek.client.view;

import risetek.client.control.RadiusUserController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserView extends RismileTableView {
	
	private final static String[] columns = {"序号","IMSI","用户名称","口令","分配地址","状态"};
	private final static String[] columnStyles = {"id","imsi","username","password","ipaddress","status"};
	private final static int rowCount = 20;	
	
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改IMSI号码.",
		"点击修改用户名称.",
		"点击修改口令.",
		"点击修改分配地址."
	};

	public  RadiusUserController control;

	public UserView(RadiusUserController control) {
		this(columns, columnStyles, rowCount, control);
		grid.addClickHandler(control.new TableAction());
	}
	
	private UserView(String[] columns, String[] columnStyles, int rowCount, RadiusUserController control) {
		super(columns, columnStyles, rowCount, control);
		this.control = control;
		Button addNewUser = new Button("添加用户", control.new AddUserAction());
		addToolButton(addNewUser);
		
		Button downloadButton = new Button("导出文件");
		super.addToolButton(downloadButton);
		downloadButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.open("forms/exportusers", "_self", "");
			}
		});
		
		Button clearButton = new Button("清除", control.new EmptyAction());
		addToolButton(clearButton);
	}
	
	public Grid getGrid() {
		if (grid != null)	return grid;

		return new MouseEventGrid() {
			public void onMouseOut(Element td, int column) {
				DOM.setStyleAttribute(td, "color", "");
				DOM.setStyleAttribute(td, "cursor", "pointer");
				setInfo("");
			}

			public void onMouseOver(Element td, int column) {
				DOM.setStyleAttribute(td, "color", "red");
				DOM.setStyleAttribute(td, "cursor", "hand");
				setInfo(banner_text[column]);
			}
		};
	}
	
	public void onLoad()
	{
		control.load();
	}
}
