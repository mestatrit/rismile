package com.risetek.rismile.system.client.dialog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.Validity;
import com.risetek.rismile.system.client.view.SystemView;




public class AddOrModifyIpDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int MODIFY = 1;
	private DockPanel panel = new DockPanel();

	SystemView parent;
	Widget widget;
	private int code = ADD; 
	private final TextBox ipBox = new TextBox();
	private final TextBox maskBox = new TextBox();
	
	private final Label ipLable = new Label("IP地址：",false);
	private final Label maskLable = new Label("子网掩码：",false);
	private final Grid gridFrame = new Grid(2, 2);

	public AddOrModifyIpDialog(final SystemView parent, final Widget widget, int code){

		addStyleName("dialog");
		this.parent = parent;
		this.widget = widget;
		
		this.code = code;
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(new Label("请在下面输入IP："),DockPanel.NORTH);
		
		gridFrame.setWidget(0,0,ipLable);
		gridFrame.setWidget(1,0,maskLable);
		gridFrame.setWidget(0,1,ipBox);
		gridFrame.setWidget(1,1,maskBox);
		
		ipBox.setTabIndex(1);
		maskBox.setTabIndex(2);
		
		if(code == MODIFY){
			ipBox.setText(parent.ip_address);
			maskBox.setText(parent.ip_mask);
		}
		panel.add(gridFrame, DockPanel.CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		add(panel,DockPanel.CENTER);

		confirm.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				String text = ipBox.getText();
				String check = Validity.validIpAddress(text);
				if (null != check){
					ipBox.setFocus(true);
					Window.alert("IP"+check);
					return;
				}
				text = maskBox.getText();
				check = Validity.validIpAddress(text);
				if (null != check){
					maskBox.setFocus(true);
					Window.alert("子网掩码"+check);
					return;
				}
				if(AddOrModifyIpDialog.this.code == MODIFY){
					sendModifyIp(IPConvert.long2IPString(ipBox.getText()), 
						IPConvert.long2IPString(maskBox.getText()), confirm);
				}else if(AddOrModifyIpDialog.this.code == ADD){
					sendAddIp(IPConvert.long2IPString(ipBox.getText()), 
							IPConvert.long2IPString(maskBox.getText()), confirm);
				}
				//unmask();
				//hide();
			}
			
		});
		setSize("280","200");
	}
	public void sendAddIp(String ip, String mask, Widget widget){

		setMessage("发送添加IP的请求...");
		parent.systemAllController.addIp(ip, mask, new DialogAction(this, parent));
		((Button)widget).setEnabled(false);
	}
	public void sendModifyIp(String ip, String mask, Widget widget){

		setMessage("发送更改IP的请求...");
		parent.systemAllController.modifyIp(ip, mask, new DialogAction(this, parent));
		((Button)widget).setEnabled(false);
	}
	
	public void show(){
		if(AddOrModifyIpDialog.this.code == MODIFY){
			setText("更改IP地址");
		}else{
			setText("添加IP地址");
		}
		
		super.show();
		ipBox.setFocus(true);
		center();
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return ipBox;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}
	public void setFirstFocus() {
		// TODO Auto-generated method stub
		ipBox.setFocus(true);
	}

}
