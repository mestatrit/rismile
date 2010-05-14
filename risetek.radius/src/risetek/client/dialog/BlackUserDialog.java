package risetek.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class BlackUserDialog extends CustomDialog {
	public final PasswordTextBox passwordBox = new PasswordTextBox();
	public final TextBox usernameBox = new TextBox();
	public final TextBox ipaddressBox = new TextBox();
	public final TextBox imsiBox = new TextBox();
	
	public String rowid;
	public BlackUserDialog(){
		Grid gridFrame = new Grid(4, 2);
		label.setText("请输入用户信息：");
		// debug format
		//gridFrame.setBorderWidth(1);
		gridFrame.setWidget(0,0,new Label("终端号码：",false));
		gridFrame.setWidget(1,0,new Label("用户名称：",false));
		gridFrame.setWidget(2,0,new Label("用户口令：",false));
		gridFrame.setWidget(3,0,new Label("分配地址：",false));
		
		gridFrame.getCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(2, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(3, 0, HorizontalPanel.ALIGN_RIGHT);
		
		passwordBox.setWidth("220");
		usernameBox.setWidth("220");
		ipaddressBox.setWidth("220");
		imsiBox.setWidth("220");
		
		imsiBox.setReadOnly(true);
		gridFrame.setWidget(0,1,imsiBox);
		usernameBox.setReadOnly(true);
		gridFrame.setWidget(1,1,usernameBox);
		
		gridFrame.setWidget(2,1,passwordBox);
		gridFrame.setWidget(3,1,ipaddressBox);

		passwordBox.setTabIndex(1);
		ipaddressBox.setTabIndex(2);
		mainPanel.add(gridFrame);
	}
	
	public void show(String tips_id, String tips_imsi, String tips_username){
		rowid = tips_id;
		usernameBox.setText(tips_username);
		imsiBox.setText(tips_imsi);
		setText("导入该用户为合法用户");
		super.show();
		passwordBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return passwordBox;
	}

	public boolean isValid()
	{
		String check;

		check = Validity.validIMSI(imsiBox.getText());
		if (null != check) {
			setMessage(check);
			imsiBox.setFocus(true);
			return false;
		}

		check = Validity.validUserName(usernameBox.getText());
		if (null != check) {
			setMessage(check);
			usernameBox.setFocus(true);
			return false;
		}

		
		
		check = Validity.validPassword(passwordBox.getText());
		if (null != check) {
			setMessage(check);
			passwordBox.setFocus(true);
			return false;
		}

		
		check = Validity.validIpAddress(ipaddressBox.getText());
		if (null != check) {
			setMessage(check);
			ipaddressBox.setFocus(true);
			return false;
		}
		
		return true;
	}
}
