package com.risetek.rismile.log.client.view;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.control.RismileLogController;
import com.risetek.rismile.log.client.model.RismileLogTable;

public class RismileLogView extends RismileTableView {
	private final static String[] columns = {"序号","日期时间","运行记录"};
	private final static String[] columnStyles = {"id","datetime","record"};
	private final static int rowCount = 20;	
	
	public Button TogAutoRefresh;
	public Button clearButton;
	public RismileLogController control;

	Timer refreshTimer = new Timer() {
		public void run() {
			control.load();
		}
	};

	public RismileLogView(RismileLogController control)
	{
		this(columns, columnStyles, rowCount, control);
	}
	
	private RismileLogView(String[] columns, String[] columnStyles, int rowCount, RismileLogController control) {
		super(columns, columnStyles, rowCount, control);
		this.control = control;
		TogAutoRefresh = new Button("查看历史", control.new AutoRefreshClick());
		addToolButton(TogAutoRefresh);
		TogAutoRefresh.addStyleName("toolbutton");

		Button downloadButton = new Button("导出文件");
		addToolButton(downloadButton);
		downloadButton.addStyleName("toolbutton");
		downloadButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.open("forms/exportlog", "_self", "");
			}

		});
		
		clearButton = new Button("清除",control.new ClearLogAction());
		clearButton.addStyleName("toolbutton");
		addToolButton(clearButton);
	}

	public Grid getGrid() {
		
		if( grid != null )	return grid;
		
		return new MouseEventGrid() {
			public void onMouseOut(Element td, int column) {
			}

			public void onMouseOver(Element td, int column) {
			}
		};
	}

	public void start_refresh() {
		refreshTimer.run();
		refreshTimer.scheduleRepeating(5000);
	}

	public void stop_refresh() {
		refreshTimer.cancel();
	}

	public void render(RismileLogTable data)
	{
		super.render(data);
		TogAutoRefresh.setText(data.autoRefresh ? "查看历史" : "自动更新");
		if(!data.autoRefresh)
		{
			refreshTimer.cancel();
			navbar.enable = true;
		}
		else
		{
			navbar.enabelNavbar(false, false, false, false);
			navbar.enable = false;
		}
	}
	/*
	public void onLoad()
	{
		MessageConsole.setText("Log onload");
		control.load();
	}
	*/
}
