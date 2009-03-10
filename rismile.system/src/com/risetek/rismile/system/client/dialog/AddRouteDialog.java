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

public class AddRouteDialog extends CustomDialog {
	
	private DockPanel panel = new DockPanel();

	SystemView parent;
	//Widget widget;
	
	public final TextBox destBox = new TextBox();
	public final TextBox maskBox = new TextBox();
	//private final TextBox interfaceBox = new TextBox();
	public final TextBox gateBox = new TextBox();
	
	private final Label destLabel = new Label("目的地址：",false);
	private final Label maskLabel = new Label("掩码：",false);
	//private final Label interfaceLabel = new Label("接口：", false);
	private final Label gateLabel = new Label("网关：", false);

	private final Grid gridFrame = new Grid(3, 2);

	public AddRouteDialog(final SystemView parent){
		super(parent);
		addStyleName("dialog");
		this.parent = parent;
		//this.widget = widget;
		
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		panel.add(new Label("请在下面输入路由："),DockPanel.NORTH);
		
		gridFrame.setWidget(0, 0, destLabel);
		gridFrame.setWidget(1, 0, maskLabel);
		//gridFrame.setWidget(2, 0, interfaceLabel);
		gridFrame.setWidget(2, 0, gateLabel);
		
		gridFrame.setWidget(0, 1, destBox);
		gridFrame.setWidget(1, 1, maskBox);
		//gridFrame.setWidget(2, 1, interfaceBox);
		gridFrame.setWidget(2, 1, gateBox);
		
		destBox.setTabIndex(1);
		maskBox.setTabIndex(2);
		gateBox.setTabIndex(3);
		
		panel.add(gridFrame, DockPanel.CENTER);
		
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}

	public void show(){
		setText("添加路由");
		super.show();
		
		destBox.setFocus(true);
		center();
	}
	
	public void show(String tips){
		setText(tips);
		super.show();
		center();
	}

	public Widget getFirstTabIndex() {
		return destBox;
	}


	public boolean valid()
	{

		String check = Validity.validIpAddress(destBox.getText());
		if (null != check) {
			destBox.setFocus(true);
			setMessage(check);
			return false;
		}

		check = Validity.validIpAddress(maskBox.getText());
		if (null != check) {
			maskBox.setFocus(true);
			setMessage(check);
			return false;
		}
		/*
		 * text = interfaceBox.getText(); check =
		 * Validity.validRouteInterface(text); if (null != check){
		 * Window.alert(check); return; }
		 */

		check = Validity.validIpAddress(gateBox.getText());
		if (null != check) {
			gateBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
}
