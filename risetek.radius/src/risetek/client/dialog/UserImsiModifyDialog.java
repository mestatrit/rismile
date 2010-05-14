package risetek.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserImsiModifyDialog extends CustomDialog {

	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();

	public String rowid;

	public UserImsiModifyDialog() {
		Grid gridFrame = new Grid(2, 2);
		//gridFrame.setBorderWidth(1);
		label.setText("请输入新的终端号：");
		gridFrame.setWidget(0, 0, new Label("当前终端号："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的终端号："));
		gridFrame.setWidget(1, 1, newValueBox);
		newValueBox.setWidth("220");
		newValueBox.setTabIndex(1);
		
		mainPanel.add(gridFrame);
	}

	public void show(String tips_id, String tips_imsi) {
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		oldValueLabel.setText(tips_imsi);
		newValueBox.setText(tips_imsi);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}

	public boolean isValid()
	{
		String check = Validity.validIMSI(newValueBox.getText());
		if (null != check) {
			setMessage(check);
			newValueBox.setFocus(true);
			return false;
		}
		return true;
	}
}
