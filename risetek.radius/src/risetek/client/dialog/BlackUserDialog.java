package risetek.client.dialog;

import risetek.client.view.BlackUserView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class BlackUserDialog extends CustomDialog {
	public final PasswordTextBox passwordBox = new PasswordTextBox();
	public final TextBox usernameBox = new TextBox();
	public final TextBox ipaddressBox = new TextBox();
	public final TextBox imsiBox = new TextBox();
	
	private final Label usernameboxLable = new Label("用户名称：",false);
	private final Label IMSILable = new Label("IMSI号码：",false);
	private final Label passwordLable = new Label("用户口令：",false);
	private final Label ipaddressLable = new Label("分配地址：",false);
	
	private BlackUserView parent;
	private Grid gridFrame = new Grid(4, 2);

	public BlackUserDialog(BlackUserView parent, String p_IMSI, String p_username ){
		super(parent);
		this.parent = parent;
		add(new Label("请输入用户信息："),DockPanel.NORTH);
		// debug format
		gridFrame.setBorderWidth(1);
		gridFrame.setWidget(0,0,IMSILable);
		gridFrame.setWidget(1,0,usernameboxLable);
		gridFrame.setWidget(2,0,passwordLable);
		gridFrame.setWidget(3,0,ipaddressLable);
		
		
		imsiBox.setReadOnly(true);
		imsiBox.setText(p_IMSI);
		gridFrame.setWidget(0,1,imsiBox);
		
		usernameBox.setReadOnly(true);
		usernameBox.setText(p_username);
		gridFrame.setWidget(1,1,usernameBox);
		
		gridFrame.setWidget(2,1,passwordBox);
		gridFrame.setWidget(3,1,ipaddressBox);

		passwordBox.setTabIndex(1);
		
		ipaddressBox.setTabIndex(2);
		
		add(gridFrame,DockPanel.CENTER);

		
		setSize("280","200");
	}
	
	public void show(){
		super.show();
		setText("导入该用户为合法用户");
		passwordBox.setFocus(true);
		super.center();
	}


	public Widget getFirstTabIndex() {
		return passwordBox;
	}
	public int getParentHeihgt() {
		return parent.getHeight();
	}

	public void setFirstFocus() {
		passwordBox.setFocus(true);
	}
	
}
