package com.risetek.rismile.log.client.view;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.control.RismileLogController;
import com.risetek.rismile.log.client.model.RismileLogTable;

public class RismileLogView extends RismileTableView implements IRisetekView {
	private final static String[] columns = {"序号","日期时间","运行记录"};
	private final static int[] columnWidth = {
		UIConfig.RECORD_ID_WIDTH,
		UIConfig.DATETIME,
		-1};
	private final static HorizontalAlignmentConstant[] columnHAlign = {
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT
	};
	
	
	private final static int rowCount = 20;	
	
	private final Button TogAutoRefresh = new Button("查看历史(H)", new RismileLogController.AutoRefreshClick());
	public final Button clearButton = new Button("清除记录(C)", new RismileLogController.ClearLogAction());
	private final Button downloadButton = new Button("导出记录(P)", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Window.open("log/export/", "_self", "");
		}
	});
	private Button FilterLog = new Button("过滤信息(S)", new RismileLogController.FilterLogAction());
	
	String banner_tips = "";

	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	Timer refreshTimer = new Timer() {
		@Override
		public void run() {
			// GWT.log("自动刷新LOG数据");
			RismileLogController.INSTANCE.data.setOffset(0);
			RismileLogController.load();
		}
	};

	public RismileLogView()
	{
		addRightSide(initPromptGrid());
	}
	
	private Widget initPromptGrid(){
		final DockPanel dockpanel = new DockPanel();
		//dockpanel.setBorderWidth(1);
		dockpanel.setSize("100%", "100%");

		final VerticalPanel northpanel = new VerticalPanel();
		northpanel.setSpacing(10);
		northpanel.setWidth("100%");
		northpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		northpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		northpanel.add(FilterLog);
		northpanel.add(TogAutoRefresh);
		northpanel.add(downloadButton);
		northpanel.add(clearButton);
/*
		final VerticalPanel southpanel = new VerticalPanel();
		southpanel.setHeight("100%");
		southpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
*/
		final FlowPanel southpanel = new FlowPanel();
		southpanel.setSize("100%", "100%");
//		southpanel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
		
		dockpanel.add(northpanel, DockPanel.NORTH);
		dockpanel.add(southpanel, DockPanel.CENTER);
		//southpanel.setBorderWidth(1);
		dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);

		southpanel.add(new Stick("info", "过滤信息功按照输入的关键字匹配运行记录中的信息。"));		
//		southpanel.add(new Stick("info", "过滤信息的关键字为空，则取消匹配。"));
		southpanel.add(new Stick("info", "注意是否有信息被过滤的提示信息，如果存在，您可能没看到全部的信息。"));		

		
		southpanel.add(new Stick("info", "查看历史/自动更新是同一个按钮的不同状态。"));		
		southpanel.add(new Stick("info", "导出记录能以文本文件格式将运行记录保存为本地文件。"));		
		
		return dockpanel;
	}
	
	
	@Override
	public Grid getGrid() {
		
		if( grid == null ) {
			grid = new MouseEventGrid() {
				@Override
				public void onMouseOut(Element td, int column) {}
				@Override
				public void onMouseOver(Element td, int column) {}
			};
			formatGrid(grid, rowCount, columns, columnWidth, columnHAlign);
	    	rowFormatter = grid.getRowFormatter();
		}
			
		return grid;
	}

	@Override
	protected void onLoad() {
		GWT.log("开始自动刷新LOG数据");
		refreshTimer.run();
	}
	
	@Override
	protected void onUnload() {
		GWT.log("停止自动刷新LOG数据");
		refreshTimer.cancel();
	}
	
	@Override
	public String[] parseRow(Node node) {
		String[] data = new String[3];
		com.google.gwt.xml.client.Element logElement = (com.google.gwt.xml.client.Element)node;
		data[0] = logElement.getFirstChild().getNodeValue();
		data[1] = XMLDataParse.getElementText( logElement, "logTIME" );
		data[2] = XMLDataParse.getElementText( logElement, "message" );
		return data;
	}
	
	public void render(RismileLogTable table)
	{
		TogAutoRefresh.setText(table.autoRefresh ? "查看历史(H)" : "自动更新(H)");
		if(!table.autoRefresh)
		{
			refreshTimer.cancel();
			navbar.enable = true;
		}
		else
		{
			navbar.enable = false;
			refreshTimer.schedule(5000);
		}

		for( int index = 0; index < rowCount; index++) {
			rowFormatter.getElement(index+1).getStyle().clearBackgroundColor();
			renderLine(table, index);
		}
    	renderStatistic(table);
	}

	@Override
	public void disablePrivate() {
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);
	}

	@Override
	public void enablePrivate() {
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		switch (keyCode) {
		case KEY.S:
			FilterLog.click();
			break;
		case KEY.H:
			TogAutoRefresh.click();
			break;
		case KEY.C:
			if(clearButton.isEnabled()){
				clearButton.click();
			}
			break;
		case KEY.P:
			if(downloadButton.isEnabled()){
				downloadButton.click();
			}
			break;
		case KEY.HOME:
		case KEY.END:
		case KEY.PAGEUP:
		case KEY.PAGEDOWN:
			navbar.doAction(keyCode);
			break;
		default:
			break;
		}
	}
}
