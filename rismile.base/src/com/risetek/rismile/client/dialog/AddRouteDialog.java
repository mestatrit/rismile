package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.Validity;

public class AddRouteDialog extends CustomDialog {
	
	public final TextBox destBox = new TextBox();
	public final TextBox maskBox = new TextBox();
	//private final TextBox interfaceBox = new TextBox();
	public final TextBox gateBox = new TextBox();
	
	public AddRouteDialog() {
		Grid gridFrame = new Grid(3, 2);
		label.setText("请在下面输入路由：");
		
		gridFrame.setWidget(0, 0, new Label("目的地址：",false));
		gridFrame.setWidget(1, 0, new Label("掩码：",false));
		//gridFrame.setWidget(2, 0, interfaceLabel);
		gridFrame.setWidget(2, 0, new Label("网关：", false));
		gridFrame.getCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.getCellFormatter().setHorizontalAlignment(2, 0, HorizontalPanel.ALIGN_RIGHT);
		gridFrame.setWidget(0, 1, destBox);
		gridFrame.setWidget(1, 1, maskBox);
		//gridFrame.setWidget(2, 1, interfaceBox);
		gridFrame.setWidget(2, 1, gateBox);
		
		destBox.setTabIndex(1);
		maskBox.setTabIndex(2);
		gateBox.setTabIndex(3);
		
		mainPanel.add(gridFrame);
		
	}

	public void show(){
		setText("添加路由");
		if(null != destBox){
			destBox.setFocus(true);
		}
		super.show();
	}
	
	public Widget getFirstTabIndex() {
		return destBox;
	}


	public boolean isValid()
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
