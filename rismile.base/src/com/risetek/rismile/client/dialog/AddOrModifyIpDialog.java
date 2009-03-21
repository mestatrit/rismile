package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.Validity;

public class AddOrModifyIpDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int MODIFY = 1;
	private DockPanel panel = new DockPanel();

	//Widget widget;
	public int code = ADD; 
	public final TextBox ipBox = new TextBox();
	public final TextBox maskBox = new TextBox();
	
	public AddOrModifyIpDialog(int code){
		//addStyleName("dialog");
		//this.parent = parent;
		//this.widget = widget;
		
		Grid gridFrame = new Grid(2, 2);
		this.code = code;
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(new Label("请在下面输入IP："),DockPanel.NORTH);
		
		gridFrame.setWidget(0,0,new Label("IP地址：",false));
		gridFrame.setWidget(1,0,new Label("子网掩码：",false));
		gridFrame.setWidget(0,1,ipBox);
		gridFrame.setWidget(1,1,maskBox);
		
		ipBox.setTabIndex(1);
		maskBox.setTabIndex(2);
/*		
		if(code == MODIFY){
			ipBox.setText(parent.ip_address);
			maskBox.setText(parent.ip_mask);
		}
*/		
		panel.add(gridFrame, DockPanel.CENTER);
		panel.setSpacing(10);
		
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	
	public void show(String ipadd, String ipmask){
		if(AddOrModifyIpDialog.this.code == MODIFY){
			setText("更改IP地址");
			ipBox.setText(ipadd);
			maskBox.setText(ipmask);
		}else{
			setText("添加IP地址");
		}
		
		super.show();
		ipBox.setFocus(true);
	}

	
	public void show(){
		if(AddOrModifyIpDialog.this.code == MODIFY){
			setText("更改IP地址");
		}else{
			setText("添加IP地址");
		}
		
		super.show();
		ipBox.setFocus(true);
	}
	
	public Widget getFirstTabIndex() {
		return ipBox;
	}

	public boolean isValid()
	{

		String check = Validity.validIpAddress(ipBox.getText());
		if (null != check) {
			ipBox.setFocus(true);
			this.setMessage(check);
			return false;
		}

		check = Validity.validIpAddress(maskBox.getText());
		if (null != check) {
			maskBox.setFocus(true);
			this.setMessage(check);
			return false;
		}

		return true;
	}
}
