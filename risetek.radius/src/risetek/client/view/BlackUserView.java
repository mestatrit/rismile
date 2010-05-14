package risetek.client.view;

import risetek.client.control.RadiusBlackController;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class BlackUserView extends RismileTableView implements IRisetekView {
	private final static String[] columns = {"序号","终端号","用户名称"};
	private final static String[] columnStyles = {"uid","imsi","backusername"};
	private final static int rowCount = 20;	

	public Button clearButton = new Button("清除");
	public Button refreshButton = new Button("刷新");
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击导入该用户为合法用户.",
		"点击导入该用户为合法用户.",
	};
	
	public BlackUserView()
	{
		super(columns, columnStyles, rowCount);
		addToolButton(clearButton);
		clearButton.addClickHandler(new RadiusBlackController.EmptyAction());
		addToolButton(refreshButton);
		refreshButton.addClickHandler(new RadiusBlackController.refreshAction());
		grid.addClickHandler(new RadiusBlackController.TableAction());
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
		RadiusBlackController.load();
	}
	
	public void disablePrivate() {
		grid.unsinkEvents( Event.ONCLICK );
		grid.unsinkEvents( Event.ONMOUSEOVER );
		grid.unsinkEvents( Event.ONMOUSEOUT );
	}

	public void enablePrivate() {
		grid.sinkEvents( Event.ONCLICK );
		grid.sinkEvents( Event.ONMOUSEOVER );
		grid.sinkEvents( Event.ONMOUSEOUT );
	}
}
