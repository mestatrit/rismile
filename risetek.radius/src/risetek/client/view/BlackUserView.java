package risetek.client.view;

import risetek.client.control.RadiusBlackController;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.PromptPanel;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class BlackUserView extends RismileTableView implements IRisetekView {
	private final static String[] columns = {"序号","终端号","用户名称"};
//	private final static String[] columnStyles = {"uid","imsi","backusername"};
	public final static int[] columnSize = {7, 20, 40};
	private final static int rowCount = 20;	

	public Button clearButton = new Button("清除(C)", new RadiusBlackController.EmptyAction());
	public Button refreshButton = new Button("刷新(R)", new RadiusBlackController.refreshAction());
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击导入该用户为合法用户.",
		"点击导入该用户为合法用户.",
	};
	
	public BlackUserView()
	{
		super(rowCount, false);
		columnsIndex.clear();
		for(int i=0;i<columns.length;i++){
			columnsIndex.add(i);
		}
		setPromptPanel(initPromptGrid());
//		addToolButton(clearButton);
//		clearButton.addClickHandler(new RadiusBlackController.EmptyAction());
//		addToolButton(refreshButton);
//		refreshButton.addClickHandler(new RadiusBlackController.refreshAction());
		grid.addClickHandler(new RadiusBlackController.TableAction());
		initGridTitle(columns);
	}
	
	private Widget initPromptGrid(){
		VerticalPanel vp = new VerticalPanel();
		vp.add(initMessagePanel());
		vp.add(initButtonList());
		vp.addStyleName("prompt-border");
//		Grid promptGrid = new Grid(2, 1);
//		promptGrid.setCellPadding(0);
//		promptGrid.setCellSpacing(0);
//		promptGrid.setHeight(Entry.SinkHeight);
//		promptGrid.setWidth("100%");
//		promptGrid.setWidget(0, 0, initMessagePanel());
//		promptGrid.setWidget(1, 0, initButtonList());
//		promptGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(0, 0, "prompt-border");
//		promptGrid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(1, 0, "prompt-border");
//		return promptGrid;
		return vp;
	}
	
	private Widget initMessagePanel() {
		PromptPanel message = new PromptPanel();
		message.setTitleText("提示信息");
		HTML body = new HTML("&nbsp;&nbsp;&nbsp;&nbsp;本页显示当前未通过认证单请求登陆的用户信息。" +
				"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>清除:</strong>将当前记录的不明身份用户信息全部删除。" + 
				"<br />&nbsp;&nbsp;&nbsp;&nbsp;<strong>刷新:</strong>更新当前不明用户列表" +
				"<br><br>&nbsp;&nbsp;&nbsp;&nbsp;<strong>特权登录</strong>后，可通过点击左侧列表给不明用户分配合法的认证信息。");
		message.setBody(body);
		body.setStyleName("prompt-message");
		return message;
	}
	
	private Widget initButtonList() {
		PromptPanel buttonList = new PromptPanel();
		Grid buttonListGrid = new Grid(2, 1);
		buttonListGrid.setWidth("95%");
		buttonListGrid.setWidget(0, 0, clearButton);
		buttonListGrid.setWidget(1, 0, refreshButton);
		for(int i=0;i<buttonListGrid.getRowCount();i++){
			buttonListGrid.getCellFormatter().setHeight(i, 0, "30px");
			buttonListGrid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		buttonList.setTitleText("基本操作");
		buttonList.setBody(buttonListGrid);
		return buttonList;
	}
	
	public Grid getGrid() {
		if (grid != null)
			return grid;

		return  new MouseEventGrid() {
			public void onMouseOut(Element td, int column) {
				DOM.setStyleAttribute(td, "color", "");
				DOM.setStyleAttribute(td, "cursor", "pointer");
				setInfo("");
			}

			public void onMouseOver(Element td, int column) {
				DOM.setStyleAttribute(td, "color", "red");
				DOM.setStyleAttribute(td, "cursor", "hand");
				setInfo(banner_text[column]);
			}
		};

	}

	public void onLoad()
	{
		RadiusBlackController.load();
	}
	
	public void disablePrivate() {
		grid.unsinkEvents( Event.ONCLICK );
		grid.unsinkEvents( Event.ONMOUSEOVER );
		grid.unsinkEvents( Event.ONMOUSEOUT );
	}

	public void enablePrivate() {
		grid.sinkEvents( Event.ONCLICK );
		grid.sinkEvents( Event.ONMOUSEOVER );
		grid.sinkEvents( Event.ONMOUSEOUT );
	}
	
	public void render(RismileTable table)
	{
    	if(columnsIndex.size()>columns.length){
    		columnsIndex.clear();
    		for(int i=0;i<columns.length;i++){
    			columnsIndex.add(i);
    		}
    	}
		super.render(table);
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
    	}
	}

	@Override
	public void doAction(int keyCode) {
		switch (keyCode) {
		case KEY.C:
			clearButton.click();
			break;
		case KEY.R:
			refreshButton.click();
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
