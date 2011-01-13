package risetek.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserDelDialog extends CustomDialog {

	Label  info = new Label("您确定删除用户？");

	public String rowid;

	public UserDelDialog() {
		//gridFrame.setBorderWidth(1);
		mainPanel.add(info);
	}

	public void show(String tips_id) {
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		super.show();
	}

	public Widget getFirstTabIndex() {
		return info;
	}

	public boolean isValid()
	{
		return true;
	}
}
