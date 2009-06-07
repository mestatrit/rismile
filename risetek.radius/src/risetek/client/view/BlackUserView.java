package risetek.client.view;

import risetek.client.control.RadiusBlackController;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class BlackUserView extends RismileTableView {
	private final static String[] columns = {"序号","IMSI","用户名称"};
	private final static String[] columnStyles = {"id","imsi","username"};
	private final static int rowCount = 20;	

	public Button clearButton = new Button("清除");
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击导入该用户为合法用户.",
		"点击导入该用户为合法用户.",
	};
	public RadiusBlackController  control;
	
	public BlackUserView(RadiusBlackController control)
	{
		this(columns, columnStyles, rowCount, control);
	}
	
	public BlackUserView(String[] columns, String[] columnStyles, int rowCount, RadiusBlackController control) {

		super(columns, columnStyles, rowCount, control);
		this.control = control;
		
		super.addToolButton(clearButton);
		//clearButton.addStyleName("toolbutton");
		grid.addClickHandler(control.new TableAction());

	}

	public Grid getGrid() {
		if (grid != null)
			return grid;

		return  new MouseEventGrid() {
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
