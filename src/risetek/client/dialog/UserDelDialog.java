package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserDelDialog extends CustomDialog {

	Label  info = new Label("您确定删除该用户？");

	public String rowid;

	public UserDelDialog() {
		// Formater Debug
		//gridFrame.setBorderWidth(1);
		add(info,DockPanel.NORTH);
		setSize("200","120");
	}

	public void show(String tips_id, String tips_imsi) {
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
