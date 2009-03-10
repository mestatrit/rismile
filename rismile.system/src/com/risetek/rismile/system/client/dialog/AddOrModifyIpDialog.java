package com.risetek.rismile.system.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;
import com.risetek.rismile.system.client.view.SystemView;




public class AddOrModifyIpDialog extends CustomDialog {
	public static final int ADD = 0;
	public static final int MODIFY = 1;
	private DockPanel panel = new DockPanel();

	SystemView parent;
	//Widget widget;
	public int code = ADD; 
	public final TextBox ipBox = new TextBox();
	public final TextBox maskBox = new TextBox();
	
	private final Label ipLable = new Label("IP地址：",false);
	private final Label maskLable = new Label("子网掩码：",false);
	private final Grid gridFrame = new Grid(2, 2);

	public AddOrModifyIpDialog(final SystemView parent, int code){
		super(parent);
		addStyleName("dialog");
		this.parent = parent;
		//this.widget = widget;
		
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

		setSize("280","200");
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

	public boolean valid()
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
