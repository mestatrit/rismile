package com.risetek.rismile.log.client.view;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class LogFilterDialog extends CustomDialog {

	public final TextBox filter = new TextBox();
	
	public LogFilterDialog() {
		label.setText("输入的信息会匹配终端号、用户名称和备注");
		Grid grid = new Grid(2, 1);
		grid.setWidget(0, 0, new Label("通过输入空白来清除过滤的限定"));
		grid.getCellFormatter().setStyleName(0, 0, "description");
		grid.setWidget(1, 0, filter);
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HorizontalPanel.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HorizontalPanel.ALIGN_CENTER);
		filter.setWidth("320");
		filter.setTabIndex(1);
		mainPanel.add(grid);
	}
	
	public void show(){
		setText("设定信息过滤");
		super.show();
		if(null != filter){
			filter.setFocus(true);
		}
	}

	public boolean isValid()
	{
		return true;
	}

	public Widget getFirstTabIndex() {
		return filter;
	}
}
