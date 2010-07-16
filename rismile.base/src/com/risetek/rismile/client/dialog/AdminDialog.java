package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.Validity;

public class AdminDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int DEL = 1;
//	private DockPanel panel = new DockPanel();

	//Widget widget;
	private int code = ADD; 
	public final TextBox nameBox = new TextBox();
	public final PasswordTextBox pwdBox = new PasswordTextBox();
	public final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	private final Label pwdLabelSe = new Label("重复密码：",false);
	private final Grid gridFrame;
	public AdminDialog(int code) {
		this.code = code;
		label.setText("请在下面输入：");
		if(code == ADD){
			gridFrame = new Grid(3, 2);
		}else{
			gridFrame = new Grid(2, 2);
		}
		nameBox.setWidth("240px");
		pwdBox.setWidth("240px");
		pwdBoxSe.setWidth("240px");
		gridFrame.setWidget(0,0,new Label("管理员名称：",false));
		gridFrame.setWidget(1,0,new Label("密码：",false));
		gridFrame.setWidget(0,1,nameBox);
		gridFrame.setWidget(1,1,pwdBox);
		gridFrame.getCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_RIGHT);
		nameBox.setTabIndex(1);
		pwdBox.setTabIndex(2);
		
		if(code == ADD){
			gridFrame.setWidget(2, 0, pwdLabelSe);
			gridFrame.getCellFormatter().setHorizontalAlignment(2, 0, HorizontalPanel.ALIGN_RIGHT);
			gridFrame.setWidget(2, 1, pwdBoxSe);
			pwdBoxSe.setTabIndex(3);
		}
		mainPanel.add(gridFrame);
		
		
//		panel.setSpacing(10);
//		panel.setBorderWidth(0);
//		add(panel,DockPanel.CENTER);
//		setSize("280","200");
	}
	public void show(){
		if(AdminDialog.this.code == DEL){
			setText("删除管理员");
			submit.setText("删除");
		}else{
			setText("添加管理员");
			submit.setText("添加");
		}
		
		super.show();
		if(null != nameBox){
			nameBox.setFocus(true);
		}
	}
	
	public Widget getFirstTabIndex() {
		return nameBox;
	}

	public boolean isValid()
	{
		String check = Validity.validAdminName(nameBox.getText());
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
		
		if((code == ADD) && !pwdBox.getText().equals(pwdBoxSe.getText())) {
			pwdBox.setText("");
			pwdBoxSe.setText("");
			pwdBox.setFocus(true);
			setMessage("两次输入的密码不符,请重新输入!");
			return false;
		}
		
		return true;
	}
}
