package com.risetek.rismile.log.client.view;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.control.RismileLogController;
import com.risetek.rismile.log.client.model.RismileLogTable;

public class RismileLogView extends RismileTableView  implements IRisetekView {
	private final static String[] columns = {"序号","日期时间","运行记录"};
	private final static String[] columnStyles = {"logid","datetime","record"};
	private final static int rowCount = 20;	
	
	private final Button TogAutoRefresh = new Button("查看历史", new RismileLogController.AutoRefreshClick());
	public final Button clearButton = new Button("清除记录", new RismileLogController.ClearLogAction());
	private final Button downloadButton = new Button("导出记录", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Window.open("forms/exportlog", "_self", "");
		}
	});
	private Button FilterLog = new Button("过滤信息", new RismileLogController.FilterLogAction());
	
	String banner_tips = "";

	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	Timer refreshTimer = new Timer() {
		public void run() {
			GWT.log("自动刷新LOG数据");
			RismileLogController.load();
		}
	};

	public RismileLogView()
	{
		super(columns, columnStyles, rowCount);
		addToolButton(FilterLog);
		addToolButton(TogAutoRefresh);
		addToolButton(downloadButton);
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

	protected void onLoad() {
		GWT.log("开始自动刷新LOG数据");
		refreshTimer.run();
	}
	
	protected void onUnload() {
		GWT.log("停止自动刷新LOG数据");
		refreshTimer.cancel();
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
			navbar.enable = false;
			refreshTimer.schedule(5000);
		}
		super.render(data);
	}

	public void disablePrivate() {
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);
	}

	public void enablePrivate() {
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);
	}
	
}
