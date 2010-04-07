package risetek.client.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.UI;
import com.risetek.rismile.client.utils.Validity;

public class UserNameModifyDialog extends CustomDialog {
	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	public String rowid;

	public UserNameModifyDialog() {
		add(new Label("请输入新的用户名："),DockPanel.NORTH);
		Grid gridFrame = new Grid(2, 2);
		gridFrame.setWidget(0, 0, new Label("当前用户名："));
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, new Label("新的用户名："));
		gridFrame.setWidget(1, 1, newValueBox);
		newValueBox.setTabIndex(1);
		add(gridFrame, DockPanel.CENTER);
	}

	public void show(String tips_id, String tips_value) {
		if(!Entry.login){
			Window.alert(UI.errInfo);
			return;
		}
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
