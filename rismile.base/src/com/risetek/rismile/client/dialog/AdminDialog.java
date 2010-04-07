package com.risetek.rismile.client.dialog;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.utils.UI;
import com.risetek.rismile.client.utils.Validity;
import com.risetek.rismile.client.utils.XMLDataParse;

public class AdminDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int DEL = 1;
	public static final int LOGIN = 2;
	private DockPanel panel = new DockPanel();

	//Widget widget;
	private int code = ADD; 
	public final TextBox nameBox = new TextBox();
	public final PasswordTextBox pwdBox = new PasswordTextBox();
	public final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	private final Label pwdLabelSe = new Label("重复密码：",false);
	private final Grid gridFrame;
	public AdminDialog(int code){
	//	addStyleName("dialog");
		this.code = code;
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(new Label("请在下面输入："),DockPanel.NORTH);
		if(code == ADD){
			gridFrame = new Grid(3, 2);
			gridFrame.setWidget(0,0,new Label("管理员名称：",false));
			gridFrame.setWidget(1,0,new Label("密码：",false));
			gridFrame.setWidget(0,1,nameBox);
			gridFrame.setWidget(1,1,pwdBox);
			gridFrame.setWidget(2, 0, pwdLabelSe);
			gridFrame.setWidget(2, 1, pwdBoxSe);
		}else if(code == DEL){
			gridFrame = new Grid(2, 2);
			gridFrame.setWidget(0,0,new Label("管理员名称：",false));
			gridFrame.setWidget(1,0,new Label("密码：",false));
			gridFrame.setWidget(0,1,nameBox);
			gridFrame.setWidget(1,1,pwdBox);
		} else {
			gridFrame = new Grid(1, 2);
			gridFrame.setWidget(0,0,new Label("特权密码：",false));
			gridFrame.setWidget(0,1,pwdBox);
		}
		
		
		nameBox.setTabIndex(1);
		pwdBox.setTabIndex(2);
		
		if(code == ADD){
			pwdBoxSe.setTabIndex(3);
		}
		panel.add(gridFrame, DockPanel.CENTER);
		
		
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	@Override
	public boolean processResponse(Response response) {
		if(AdminDialog.this.code == LOGIN){
			String resule = XMLDataParse.getElementText(response.getText(), "OK");
			if("EnablePass".equals(resule)){
				hide();
				return true;
			} else {
				setMessage("密码错误！");
				return false;
			}
		}
		return super.processResponse(response);
	}
	public void show(){
		if(AdminDialog.this.code == DEL){
			if(!Entry.login){
				Window.alert(UI.errInfo);
				return;
			}
			setText("删除管理员");
		}else if(AdminDialog.this.code == ADD){
			if(!Entry.login){
				Window.alert(UI.errInfo);
				return;
			}
			setText("添加管理员");
		} else {
			setText("特权登录");
		}
		super.show();
		if(AdminDialog.this.code == DEL){
			nameBox.setFocus(true);
		}else if(AdminDialog.this.code == ADD){
			nameBox.setFocus(true);
		} else {
			pwdBox.setFocus(true);
		}
	}
	
	public Widget getFirstTabIndex() {
		return nameBox;
	}

	public boolean isValid()
	{
		String check = Validity.validAdminName(nameBox.getText());
		if(AdminDialog.this.code == LOGIN){
			check = null;
		}
		if (null != check) {
			nameBox.setFocus(true);
			setMessage(check);
			return false;
		}
		check = Validity.validPassword(pwdBox.getText());
		if (null != check) {
			pwdBox.setFocus(true);
			setMessage(check);
			return false;
		}
		
		if (!pwdBox.getText().equals(pwdBoxSe.getText()) && (AdminDialog.this.code == ADD)) {
			pwdBox.setText("");
			pwdBoxSe.setText("");
			pwdBox.setFocus(true);
			setMessage("两次输入的密码不符,请重新输入!");
			return false;
		}
		
		return true;
	}
}
