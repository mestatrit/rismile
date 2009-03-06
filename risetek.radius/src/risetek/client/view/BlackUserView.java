package risetek.client.view;

import risetek.client.control.RadiusBlackController;
import risetek.client.control.RadiusUserController;
import risetek.client.dialog.BlackUserDialog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class BlackUserView extends RismileTableView {

	private Button clearButton = new Button("清除");
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击导入该用户为合法用户.",
		"点击导入该用户为合法用户.",
	};
	public RadiusBlackController blackController = new RadiusBlackController();
	public RadiusUserController  userController = new RadiusUserController();
	
	public BlackUserView(String[] columns, String[] columnStyles, int rowCount) {

		super(columns, columnStyles, rowCount);
		
		super.addToolButton(clearButton);
		clearButton.addStyleName("rismile-Tool-Button");
		clearButton.addClickListener(new EmptyAction());
		
		//searchBar.searchListBox.addItem("用户名", "USER");
		//searchBar.searchListBox.addItem("IMSI号", "IMSI");
		//searchBar.searchListBox.setSelectedIndex(0);
	}

	private MouseEventGrid Tgrid = null;
	public Grid getGrid() {
		if (Tgrid != null)
			return Tgrid;

		Tgrid = new MouseEventGrid() {
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

		Tgrid.addTableListener(new TableAction());
		return Tgrid;
	}

	public void loadModel() {
		// TODO Auto-generated method stub
		blackController.load(rowCount, startRow, new LoadTableAction());
	}
	
	class EmptyAction extends ViewAction implements ClickListener{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			clearButton.setEnabled(true);
			loadModel();
		}

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if(Window.confirm("是否要清除所有不明用户?")){ 
				clearButton.setEnabled(false);

				blackController.empty(this);
			}
		}
		
	}
	class TableAction extends ViewAction implements TableListener{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			loadModel();
		}

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			//focusID = grid.getText(row, 0);
			focusID = getRowId(row);
			focusValue = grid.getText(row, cell);
			rowID = Long.toString(row);
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if(Window.confirm("是否要删除?\n" +
						"用户名:"+grid.getText(row, 2)+"\n"+
						"IMSI: "+grid.getText(row, 1))){
					
					//String criteria = "ROWID="+focusID;
					
					blackController.delRow(focusID, this);
				}
				break;
			case 1:
			case 2:
				// 导入用户为合法用户。
				BlackUserDialog dialog = new BlackUserDialog(BlackUserView.this, grid.getText(row, 1), grid.getText(row, 2));

				dialog.show();
				break;
			default:
				break;
			}

		}
	}
}
