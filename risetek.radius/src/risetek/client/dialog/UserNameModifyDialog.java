package risetek.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserNameModifyDialog extends CustomDialog {
	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	public String rowid;

	public UserNameModifyDialog() {
		// 修改宽度，以适应显示需要。
		mainPanel.setWidth("380px");
		// 宽度修改后需要重新定位显示位置。
		center();
		
		label.setText("请输入新的用户名：");
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setWidget(0, 0, new Label("当前用户名：", false));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的用户名：", false));
		newValueBox.setWidth("240px");
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
		String check = Validity.validUserName(newValueBox.getText());
		if( null != check )
		{
			newValueBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
}
