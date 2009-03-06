package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserPasswordModifyDialog extends CustomDialog {
	PasswordTextBox passwordbox = new PasswordTextBox();
	private UserView parent;
	public UserPasswordModifyDialog(UserView parent) {
		this.parent = parent;

		add(new Label("请输入新口令："),DockPanel.NORTH);
		add(passwordbox,DockPanel.CENTER);
		confirm.addClickListener(new mClickListener());
		passwordbox.setTabIndex(1);
		setSize("200","140");
	}

	public void show(){
		setText("记录序号：" + parent.rowID);
		super.show();
		passwordbox.setFocus(true);
	}

	class mClickListener implements ClickListener {
        public void onClick(Widget sender) {
			String text = passwordbox.getText();
			String check = Validity.validPassword(text);
			if (null == check) {

				//String columns = "PASSWORD='"+text+"'";
				//String criteria = "ROWID="+parent.focusID;
				//parent.userController.updateRecord(columns, criteria, new DialogAction(UserPasswordModifyDialog.this, parent));
				parent.userController.modifyPassword(parent.focusID, text, new DialogAction(UserPasswordModifyDialog.this, parent));
				confirm.setEnabled(false);
				
			} else {
				passwordbox.setFocus(true);
				Window.alert(check);
			}
        }
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return passwordbox;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub
		passwordbox.setFocus(true);
	}
}
