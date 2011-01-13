package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FilterDialog extends CustomDialog {

	public final TextBox filter = new TextBox();
	
	public FilterDialog() {
		label.setText("输入的关键字会匹配记录中的相关数据");
		Grid grid = new Grid(2, 1);
		grid.setWidget(0, 0, new Label("通过输入空白来清除过滤的限定"));
		grid.setWidget(1, 0, filter);
		grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		filter.setWidth("320px");
		filter.setTabIndex(1);
		mainPanel.add(grid);
	}
	
	@Override
	public void show(){
		setText("设定记录过滤");
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
