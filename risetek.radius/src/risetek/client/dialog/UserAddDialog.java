package risetek.client.dialog;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserAddDialog extends CustomDialog {
	public final PasswordTextBox passwordbox = new PasswordTextBox();
	public final TextBox usernamebox = new TextBox();
	public final TextBox ipaddress = new TextBox();
	public final TextBox IMSI = new TextBox();
	
	private final FocusPanel focusPanel = new FocusPanel();
	
	public UserAddDialog(){
		label.setText("请输入用户信息：");
		
		Grid gridFrame = new Grid(4, 2);
		gridFrame.setWidget(0,0,new Label("终端号码：",false));
		gridFrame.setWidget(1,0,new Label("用户名称：",false));
		gridFrame.setWidget(2,0,new Label("用户口令：",false));
		gridFrame.setWidget(3,0,new Label("分配地址：",false));
		
		gridFrame.setWidget(0,1,IMSI);
		gridFrame.setWidget(1,1,usernamebox);
		gridFrame.setWidget(2,1,passwordbox);
		gridFrame.setWidget(3,1,ipaddress);
		
		passwordbox.setWidth("240px");
		usernamebox.setWidth("240px");
		ipaddress.setWidth("240px");
		IMSI.setWidth("240px");
		
		IMSI.setTabIndex(1);
		usernamebox.setTabIndex(2);
		passwordbox.setTabIndex(3);
		ipaddress.setTabIndex(4);
		
		focusPanel.add(gridFrame);
		mainPanel.add(focusPanel);
	}
	
	public void show(){
		setText("新加一个用户");
		super.show();
		if(null != IMSI){
			IMSI.setFocus(true);
		}
	}

	public Widget getFirstTabIndex() {
		return IMSI;
	}

	public boolean isValid()
	{
		String check;
		check = Validity.validIMSI(IMSI.getText());
		if( null != check )
		{
			setMessage(check);
			IMSI.setFocus(true);
			return false;
		}

		check = Validity.validUserName(usernamebox.getText());
		if( null != check )
		{
			usernamebox.setFocus(true);
			setMessage(check);
			return false;
		}
		
		check = Validity.validPassword(passwordbox.getText());
		if( null != check )
		{
			passwordbox.setFocus(true);
			setMessage(check);
			return false;
		}
		
		check = Validity.validIpAddress(ipaddress.getText());
		if (null != check) {
			ipaddress.setFocus(true);
			setMessage(check);
			return false;
		}
		
			
		return true;
	}
}
