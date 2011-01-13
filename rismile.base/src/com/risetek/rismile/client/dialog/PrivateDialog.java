package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.Validity;

public class PrivateDialog extends CustomDialog {

	public final PasswordTextBox pwdBox = new PasswordTextBox();
	
	private final Grid gridFrame = new Grid(1, 2);
	public PrivateDialog()
	{
		
		label.setText("请在下面输入：");
		
		gridFrame.setWidget(0,0,new Label("密码：",false));
		gridFrame.setWidget(0,1,pwdBox);
		
		pwdBox.setTabIndex(1);
		mainPanel.add(gridFrame);
		this.setSize("274px", "168px");
	}
	@Override
	public void show()
	{
		setText("登录特权模式");
		super.show();
		if(null != pwdBox){
			pwdBox.setFocus(true);
		}
	}
	
	public Widget getFirstTabIndex() {
		return pwdBox;
	}

	public boolean isValid()
	{
		String check = Validity.validPassword(pwdBox.getText());
		if (null != check) {
			pwdBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
}
