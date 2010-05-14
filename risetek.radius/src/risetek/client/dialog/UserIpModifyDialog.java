package risetek.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.utils.Validity;

public class UserIpModifyDialog extends CustomDialog {

	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	public String rowid;

	public UserIpModifyDialog() {
		Grid gridFrame = new Grid(2, 2);
		label.setText("请输入新的IP：");
		gridFrame.setWidget(0, 0, new Label("当前IP："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的IP："));
		gridFrame.setWidget(1, 1, newValueBox);
		
		newValueBox.setTabIndex(1);
		
		mainPanel.add(gridFrame);
	}

	public void show(String tips_id, String tips_value) {

		rowid = tips_id;
		setText("记录序号：" + tips_id);
		oldValueLabel.setText(tips_value);
		newValueBox.setText(tips_value);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}

	public boolean isValid()
	{
		String check = Validity.validIpAddress(newValueBox.getText());
		if (null != check) {
			SysLog.log(check);
			setMessage(check);
			return false;
		}
		return true;
	}
	
}
