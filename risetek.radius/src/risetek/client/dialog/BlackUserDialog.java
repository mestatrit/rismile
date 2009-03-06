package risetek.client.dialog;

import risetek.client.view.BlackUserView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class BlackUserDialog extends CustomDialog {
	private final PasswordTextBox passwordBox = new PasswordTextBox();
	private final TextBox usernameBox = new TextBox();
	private final TextBox ipaddressBox = new TextBox();
	private final TextBox imsiBox = new TextBox();
	
	private final Label usernameboxLable = new Label("用户名称：",false);
	private final Label IMSILable = new Label("IMSI号码：",false);
	private final Label passwordLable = new Label("用户口令：",false);
	private final Label ipaddressLable = new Label("分配地址：",false);
	
	private BlackUserView parent;
	private Grid gridFrame = new Grid(4, 2);

	public BlackUserDialog(BlackUserView parent, String p_IMSI, String p_username ){
		this.parent = parent;
		add(new Label("请输入用户信息："),DockPanel.NORTH);
		
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

		
		confirm.addClickListener(new mClickListener());
		
		setSize("280","200");
	}
	
	public void show(){
		super.show();
		setText("导入该用户为合法用户");
		passwordBox.setFocus(true);
		super.center();
	}

	class mClickListener implements ClickListener {
        public void onClick(Widget sender) {
			String text; 
			String check; 
			
			text = imsiBox.getText();
			check = Validity.validIMSI(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			String imsicode = text;
			
			text = usernameBox.getText();
			String username = text;
			
			text = passwordBox.getText();
			check = Validity.validPassword(text);
			if (null != check){
				passwordBox.setFocus(true);
				Window.alert(check);
				return;
			}
			String password = text;
			
			text = ipaddressBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				ipaddressBox.setFocus(true);
				Window.alert(check);
				return;
			}
			String ipaddress = text;
			//String ipaddress = IPConvert.long2IPString(text);
			//String values = "('"+ imsicode +"','"+username+"','"+password+"',"+ipaddress+",0)";	
            //String addSql = parent.userController.getAddSql(null, values);
			
            //String criteria = "ROWID="+parent.focusID;
			//String delSql = parent.blackController.getDeleteSql(criteria);
			
			//String sqls = addSql + "&" + delSql;
			//parent.blackController.execteMultiple(sqls, new DialogAction(BlackUserDialog.this, parent));
			parent.blackController.add(parent.focusID, username, imsicode, password, ipaddress, 
					new DialogAction(BlackUserDialog.this, parent));
			confirm.setEnabled(false);
			
        }
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return passwordBox;
	}
	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub
		passwordBox.setFocus(true);
	}
	
}
