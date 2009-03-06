package com.risetek.rismile.system.client.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;
import com.risetek.rismile.system.client.view.SystemView;

public class AdminDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int DEL = 1;
	private DockPanel panel = new DockPanel();

	SystemView parent;
	Widget widget;
	private int code = ADD; 
	private final TextBox nameBox = new TextBox();
	private final PasswordTextBox pwdBox = new PasswordTextBox();
	private final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	private final Label nameLabel = new Label("管理员名称：",false);
	private final Label pwdLabel = new Label("密码：",false);
	private final Label pwdLabelSe = new Label("重复密码：",false);
	private final Grid gridFrame;
	public AdminDialog(final SystemView parent, final Widget widget, int code){

		addStyleName("dialog");
		this.parent = parent;
		this.widget = widget;
		
		this.code = code;
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(new Label("请在下面输入："),DockPanel.NORTH);
		if(code == ADD){
			gridFrame = new Grid(3, 2);
		}else{
			gridFrame = new Grid(2, 2);
		}
		gridFrame.setWidget(0,0,nameLabel);
		gridFrame.setWidget(1,0,pwdLabel);
		gridFrame.setWidget(0,1,nameBox);
		gridFrame.setWidget(1,1,pwdBox);
		
		nameBox.setTabIndex(1);
		pwdBox.setTabIndex(2);
		
		if(code == ADD){
			gridFrame.setWidget(2, 0, pwdLabelSe);
			gridFrame.setWidget(2, 1, pwdBoxSe);
			pwdBoxSe.setTabIndex(3);
		}
		panel.add(gridFrame, DockPanel.CENTER);
		
		
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		add(panel,DockPanel.CENTER);

		confirm.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				String text = nameBox.getText();
				String check = Validity.validAdminName(text);
				if (null != check){
					Window.alert(check);
					return;
				}
				text = pwdBox.getText();
				check = Validity.validPassword(text);
				if (null != check){
					Window.alert(check);
					return;
				}
				if(AdminDialog.this.code == ADD && !pwdBox.getText().equals(pwdBoxSe.getText())){
					pwdBox.setText("");
					pwdBoxSe.setText("");
					pwdBox.setFocus(true);
					Window.alert("两次输入的密码不符,请重新输入!");
					return;
				}
				if(AdminDialog.this.code == DEL){
					sendAdminDel(nameBox.getText(), pwdBox.getText(), confirm);
				}else if(AdminDialog.this.code == ADD){
					sendAdminAdd(nameBox.getText(), pwdBox.getText(), pwdBoxSe.getText(), confirm);
				}
				//unmask();
				//hide();
			}
			
		});
		setSize("280","200");
	}
	public void sendAdminAdd(String name, String password, String password2, Widget widget){
		setMessage("发送添加管理员的请求...");
		parent.systemAllController.addAdmin(name, password, password2, new DialogAction(this, parent));
		
	}
	public void sendAdminDel(String name, String password, Widget widget){
		setMessage("发送删除管理员的请求...");
		parent.systemAllController.delAdmin(name, password, new DialogAction(this, parent));
		
	}
	public void show(){
		if(AdminDialog.this.code == DEL){
			setText("删除管理员");
		}else{
			setText("添加管理员");
		}
		
		super.show();
		nameBox.setFocus(true);
		center();
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return nameBox;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub
		nameBox.setFocus(true);
	}
}
