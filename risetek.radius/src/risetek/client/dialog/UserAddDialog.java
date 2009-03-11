package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.ui.DockPanel;
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
	
	private final Label usernameboxLable = new Label("用户名称：",false);
	private final Label IMSILable = new Label("IMSI号码：",false);
	private final Label passwordLable = new Label("用户口令：",false);
	private final Label ipaddressLable = new Label("分配地址：",false);
	private Grid gridFrame = new Grid(4, 2);
	private final FocusPanel focusPanel = new FocusPanel();
	
	public UserAddDialog(UserView parent){
		super(parent);
		add(new Label("请输入用户信息："),DockPanel.NORTH);
		
		gridFrame.setWidget(0,0,IMSILable);
		gridFrame.setWidget(1,0,usernameboxLable);
		gridFrame.setWidget(2,0,passwordLable);
		gridFrame.setWidget(3,0,ipaddressLable);
		
		gridFrame.setWidget(0,1,IMSI);
		gridFrame.setWidget(1,1,usernamebox);
		gridFrame.setWidget(2,1,passwordbox);
		gridFrame.setWidget(3,1,ipaddress);
		
		IMSI.setTabIndex(1);
		usernamebox.setTabIndex(2);
		passwordbox.setTabIndex(3);
		
		ipaddress.setTabIndex(4);
		
		focusPanel.add(gridFrame);
		add(focusPanel,DockPanel.CENTER);

		setSize("280","200");
	}
	
	public void show(){
		show("新加一个用户");
		IMSI.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return IMSI;
	}

	public boolean valid()
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
