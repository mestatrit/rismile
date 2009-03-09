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

public class RismileLogView extends RismileTableView {
	private final static String[] columns = {"序号","日期时间","运行记录"};
	private final static String[] columnStyles = {"id","datetime","record"};
	private final static int rowCount = 20;	
	
	public boolean autoRefresh = true;
	public Button TogAutoRefresh;
	public Button clearButton = new Button("清除");
	boolean showing = false;
	public RismileLogController logController;

	
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
		super.addToolButton(clearButton);
		clearButton.addStyleName("rismile-Tool-Button");
		clearButton.addClickListener(logController.new ClearLogAction());
	}

	Timer refreshTimer = new Timer() {
		public void run() {
			update();
		}
	};

	public Grid getGrid() {
		return new MouseEventGrid() {
			public void onMouseOut(Element td, int column) {
			}

			public void onMouseOver(Element td, int column) {
			}
		};
	}

	public void start_refresh() {
		showing = true;
		refreshTimer.run();
	}

	public void update() {

		TogAutoRefresh.setText(autoRefresh ? "查看历史" : "自动更新");
		if (autoRefresh && showing) {
			logController.load();
			refreshTimer.schedule(10000);
		}
	}

	public void stop_refresh() {
		showing = false;
	}

	public void replaneNavBar(int total) {
		/*
		if (!autoRefresh)
			super.enabledNavBar(total);
			*/
	}


	public void loadModel() {
		logController.load();
	}

	public void mask() {
		// TODO Auto-generated method stub
		
	}

	public void unmask() {
		// TODO Auto-generated method stub
		
	}
	

}
