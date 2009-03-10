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
	
	//public boolean autoRefresh = true;
	public Button TogAutoRefresh;
	public Button clearButton;
	public RismileLogController logController;

	Timer refreshTimer = new Timer() {
		public void run() {
			logController.load();
		}
	};
	
	public RismileLogView(RismileLogController control)
	{
		this(columns, columnStyles, rowCount, control);
	}
	
	private RismileLogView(String[] columns, String[] columnStyles, int rowCount, RismileLogController control) {
		super(columns, columnStyles, rowCount, control);
		logController = control;
		TogAutoRefresh = new Button("", logController.new AutoRefreshClick());
		super.addToolButton(TogAutoRefresh);
		TogAutoRefresh.addStyleName("rismile-Tool-Button");

		Button downloadButton = new Button("导出文件");
		super.addToolButton(downloadButton);
		downloadButton.addStyleName("rismile-Tool-Button");
		downloadButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.open("forms/exportlog", "_self", "");
			}

		});
		
		clearButton = new Button("清除",logController.new ClearLogAction());
		clearButton.addStyleName("rismile-Tool-Button");
		super.addToolButton(clearButton);
	}

	public Grid getGrid() {
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


	public void mask() {
		// TODO Auto-generated method stub
		
	}

	public void unmask() {
		// TODO Auto-generated method stub
		
	}
	
	public void render(RismileLogTable data)
	{
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
		
		super.render(data);
	}
}
