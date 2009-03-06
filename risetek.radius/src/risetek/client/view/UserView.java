package risetek.client.view;

import risetek.client.control.RadiusUserController;
import risetek.client.dialog.UserAddDialog;
import risetek.client.dialog.UserImsiModifyDialog;
import risetek.client.dialog.UserIpModifyDialog;
import risetek.client.dialog.UserNameModifyDialog;
import risetek.client.dialog.UserPasswordModifyDialog;

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

public class UserView extends RismileTableView {
	private Button clearButton = new Button("清除");
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改IMSI号码.",
		"点击修改用户名称.",
		"点击修改口令.",
		"点击修改分配地址."
	};
	private MouseEventGrid Tgrid = null;
	public  RadiusUserController userController = new RadiusUserController();

	
	public UserView(String[] columns, String[] columnStyles, int rowCount) {
		
		super(columns, columnStyles, rowCount);
		Button addNewUser = new Button("添加用户", new AddAction());
		super.addToolButton(addNewUser);
		addNewUser.addStyleName("rismile-Tool-Button");
		
		Button downloadButton = new Button("导出文件");
		super.addToolButton(downloadButton);
		downloadButton.addStyleName("rismile-Tool-Button");
		downloadButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				Window.open("forms/exportusers", "_self", "");
				
			}
			
		});
		super.addToolButton(clearButton);
		clearButton.addStyleName("rismile-Tool-Button");
		clearButton.addClickListener(new EmptyAction());
		
		//searchBar.searchListBox.addItem("用户名", "USER");
		//searchBar.searchListBox.addItem("IMSI号", "IMSI");
		//searchBar.searchListBox.addItem("IP地址", "ADDRESS");
		//searchBar.searchListBox.setSelectedIndex(0);
	}

	
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
		userController.load(rowCount, startRow, new LoadTableAction());
	}
	class AddAction extends ViewAction implements ClickListener{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			loadModel();
		}

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			UserAddDialog addDialog = new UserAddDialog(UserView.this);
			addDialog.show();
		}
		
	}
	class EmptyAction extends ViewAction implements ClickListener{

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if(Window.confirm("是否要清除所有用户?")){ 
				clearButton.setEnabled(false);
				userController.empty(this);
			}
		}

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			clearButton.setEnabled(true);
			loadModel();
		}
		
	}
	class TableAction extends ViewAction implements TableListener{

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			// TODO Auto-generated method stub
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			//focusID = grid.getText(row, 0);
			focusID = getRowId(row);
			focusValue = grid.getText(row, cell);
			rowID = Long.toString(row);
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if(Window.confirm("是否要删除该用户?\n" +
						"用户名:"+grid.getText(row, 2)+"\n"+
						"IMSI:  "+grid.getText(row, 1)+"\n"+
						"IP地址:"+grid.getText(row, 4))){
					//addCommand("function", "deluser");
					//addCommand("id", focusID);
					//String criteria = "ROWID="+focusID;
					userController.delRow(focusID, this);
				}
				break;
			case 1:
				// 修改IMSI号码。
				new UserImsiModifyDialog(UserView.this).show();
				break;
			case 2:
				// 修改用户名称。
				new UserNameModifyDialog(UserView.this).show();
				break;
			case 3:
				// 修改用户名称。
				new UserPasswordModifyDialog(UserView.this).show();
				break;
			case 4:
				new UserIpModifyDialog(UserView.this).show();
				break;
			default:
				break;
			}

		}

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			loadModel();
		}
		
	}
}
