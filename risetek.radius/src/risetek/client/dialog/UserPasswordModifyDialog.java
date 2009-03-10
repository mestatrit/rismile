package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserPasswordModifyDialog extends CustomDialog {
	public PasswordTextBox passwordbox = new PasswordTextBox();
	private UserView parent;
	public UserPasswordModifyDialog(UserView parent) {
		super(parent);
		this.parent = parent;

		add(new Label("请输入新口令："),DockPanel.NORTH);
		add(passwordbox,DockPanel.CENTER);
		passwordbox.setTabIndex(1);
		setSize("200","140");
	}

	public void show(){
		setText("记录序号：" + parent.focusID);
		super.show();
		passwordbox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return passwordbox;
	}

	public int getParentHeihgt() {
		return parent.getHeight();
	}

	public void setFirstFocus() {
		passwordbox.setFocus(true);
	}
	
	public boolean valid()
	{
		String check = Validity.validPassword(passwordbox.getText());
		if (null != check) {
			setMessage(check);
			passwordbox.setFocus(true);
			return false;
		}
		
		return true;
	}
}
