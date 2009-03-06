package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserNameModifyDialog extends CustomDialog implements
		ClickListener {
	Label  oldNoteLabel = new Label();
	Label  oldValueLabel = new Label();
	TextBox newValueBox = new TextBox();
	Label newNoteLabel = new Label();
	private Grid gridFrame = new Grid(2, 2);
	private UserView parent;
	public UserNameModifyDialog(UserView parent) {

		this.parent = parent;
		add(new Label("请输入新的用户名："),DockPanel.NORTH);
		gridFrame.setWidget(0, 0, oldNoteLabel);
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, newNoteLabel);
		gridFrame.setWidget(1, 1, newValueBox);
		newValueBox.setTabIndex(1);
		add(gridFrame, DockPanel.CENTER);
		confirm.addClickListener(new mClickListener());
	}

	public void show() {
		setText("记录序号：" + parent.rowID);
		oldNoteLabel.setText("当前用户名：");
		oldValueLabel.setText(parent.focusValue);
		newNoteLabel.setText("新的用户名：");
		newValueBox.setText(parent.focusValue);
		
		super.show();
		newValueBox.setFocus(true);
	}

	class mClickListener implements ClickListener {
		public void onClick(Widget sender) {
			String username = newValueBox.getText();
			String check = username; //Validity.validUserName(username);
			if (null != check) {
				
				//String columns = "USER='"+username+"'";
				//String criteria = "ROWID="+parent.focusID;
				//parent.userController.updateRecord(columns, criteria, new DialogAction(UserNameModifyDialog.this, parent));
				parent.userController.modifyName(parent.focusID, username, new DialogAction(UserNameModifyDialog.this, parent));
				confirm.setEnabled(false);
				
			} else {
				newValueBox.setFocus(true);
				Window.alert("用户名不能为空！");
			}
		}
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return newValueBox;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub
		newValueBox.setFocus(true);
	}

	@Override
	public void onClick(Widget sender) {
		// TODO Auto-generated method stub
		
	}
}
