package com.risetek.rismile.log.client.view;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.PromptPanel;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.control.RismileLogController;
import com.risetek.rismile.log.client.model.RismileLogTable;

public class RismileLogView extends RismileTableView  implements IRisetekView {
	private final static String[] columns = {"序号","日期时间","运行记录"};
//	private final static String[] columnStyles = {"logid","datetime","record"};
	public final static int[] columnSize = {6, 24, 72};
	private final static int rowCount = 20;	
	
	private final Button TogAutoRefresh = new Button("查看历史(H)", new RismileLogController.AutoRefreshClick());
	public final Button clearButton = new Button("清除记录(C)", new RismileLogController.ClearLogAction());
	private final Button downloadButton = new Button("导出记录(P)", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Window.open("forms/exportlog", "_self", "");
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
		public void run() {
			GWT.log("自动刷新LOG数据");
			RismileLogController.load();
		}
	};

	public RismileLogView()
	{
		super(rowCount, false);
		columnsIndex.clear();
		for(int i=0;i<columns.length;i++){
			columnsIndex.add(i);
		}
//		addToolButton(FilterLog);
//		addToolButton(TogAutoRefresh);
//		addToolButton(downloadButton);
//		addToolButton(clearButton);
		setPromptPanel(initPromptGrid());
		initGridTitle(columns);
	}
	
	private Widget initPromptGrid(){
		VerticalPanel vp = new VerticalPanel();
		vp.add(initMessage());
		vp.add(initButtonList());
		vp.addStyleName("prompt-border");
//		Grid promptGrid = new Grid(2, 1);
//		promptGrid.setHeight(Entry.SinkHeight);
//		promptGrid.setCellPadding(0);
//		promptGrid.setCellSpacing(0);
//		promptGrid.setWidget(0, 0, initMessage());
//		promptGrid.setWidget(1, 0, initButtonList());
//		promptGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(0, 0, "prompt-border");
//		promptGrid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(1, 0, "prompt-border");
//		return promptGrid;
		return vp;
	}
	
	private Widget initMessage(){
		PromptPanel message = new PromptPanel();
		message.setTitleText("提示信息");
		HTML body = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;本页显示设备当前的日志信息。" +
				"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;可通过基本操作中的按钮实现对当前日志记录的条件过滤、历史记录的查看等功能。" +
				"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>特权登录</strong>后，可将当前全部日志记录导出为本地文件，也可以全部清除设备上的日志记录。");
		message.setBody(body);
		body.setStyleName("prompt-message");
		body.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return message;
	}
	
	private Widget initButtonList(){
		PromptPanel buttonList = new PromptPanel();
		Grid buttonListGrid = new Grid(4, 1);
		buttonListGrid.setWidth("95%");
		buttonListGrid.setWidget(0, 0, FilterLog);
		buttonListGrid.setWidget(1, 0, TogAutoRefresh);
		buttonListGrid.setWidget(2, 0, downloadButton);
		buttonListGrid.setWidget(3, 0, clearButton);
		for(int i=0;i<buttonListGrid.getRowCount();i++){
			buttonListGrid.getCellFormatter().setHeight(i, 0, "30px");
			buttonListGrid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		buttonList.setTitleText("基本操作");
		buttonList.setBody(buttonListGrid);
		return buttonList;
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
		TogAutoRefresh.setText(data.autoRefresh ? "查看历史(H)" : "自动更新(H)");
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
		double maxWidth = 0;
    	for(int i=0;i<columns.length;i++){
    		int width = columnSize[i];
    		maxWidth = maxWidth + width;
    	}
    	for(int i=0;i<columns.length;i++){
    		double width = columnSize[i];
    		double temp = width/maxWidth * 100;
    		String per = Double.toString(temp);
    		if(per.length()<8) {
    			per = per + "0000";
    		}
    		int dot = per.indexOf(".");
    		String miu = per.substring(dot+ 1, dot+5);
    		int tmiu = Integer.parseInt(miu);
    		if(tmiu>4445){
    			temp = temp + 1;
    		}
    		per = Double.toString(temp);
    		per = per.substring(0, per.indexOf("."));
    		if(i == 0){
    			grid.getCellFormatter().setWidth(0, i, "80px");
    		} else {
    			grid.getCellFormatter().setWidth(0, i, per + "%");
    		}
    	}
    	for(int i=0;i<grid.getRowCount();i++){
    		grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    		grid.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_CENTER);
    	}
	}

	public void disablePrivate() {
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);
	}

	public void enablePrivate() {
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);
	}

	@Override
	public void doAction(int keyCode) {
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
