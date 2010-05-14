package risetek.client.dialog;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserPasswordModifyDialog extends CustomDialog {
	public PasswordTextBox passwordbox = new PasswordTextBox();

	public String rowid;
	public UserPasswordModifyDialog() {
		label.setText("请输入新口令：");
		mainPanel.add(passwordbox);
		passwordbox.setWidth("200");
		passwordbox.setTabIndex(1);
	}

	public void show(String tips_id){
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		super.show();
		passwordbox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return passwordbox;
	}

	public boolean isValid()
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
