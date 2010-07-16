package risetek.client.view;

import risetek.client.control.RadiusUserController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.PromptPanel;
import com.risetek.rismile.client.utils.Utils;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserView extends RismileTableView  implements IRisetekView {
	
	public final static String[] columns = {"序号","终端号码","用户名称","口令","分配地址", "第二地址", "用户级别", "登录时段","备注"};
//	private final static String[] columnStyles = {"uid","imsi","username","password","ipaddress", "ipaddress", "password", "ipaddress", "note"};
	public final static int[] columnSize = {7, 20, 24, 4, 15, 15, 12, 11, 24};
	private final static int rowCount = 21;
	private Grid itemList;
	private ClickHandler action;
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改终端号码.",
		"点击修改用户名称.",
		"点击修改用户口令.",
		"点击修改分配地址.",
		"点击修改第二地址.",
		"点击修改用户级别.",
		"点击修改登录时段.",
		"点击修改备注信息."
	};

	
	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	private final Button FilterUser = new Button("过滤信息(S)", new RadiusUserController.FilterUserAction());
	private final Button addNewUser = new Button("添加用户(A)", new RadiusUserController.AddUserAction());
	private final Button downloadButton = new Button("导出文件(P)", new ClickHandler()
	{
		public void onClick(ClickEvent event) {
			Window.open("forms/exportusers", "_self", "");
		}
	});
	private final Button clearButton = new Button("用户总清(C)", new RadiusUserController.EmptyAction());
	
	public UserView() {
		super(rowCount, false);
		specialMarker = 1;
		setPromptPanel(initPromptGrid());
		grid.addClickHandler(new RadiusUserController.TableAction());
		initGridTitleNative();
	}
	
	private void initGridTitleNative(){
		String[] title = null;
		if(columnsIndex.size() == 0){
			for(int i=0;i<Utils.RISMILE_TABLE_MAX_COLUMN_COUNT;i++){
				columnsIndex.add(i);
			}
		}
		title = new String[columnsIndex.size()];
		for(int i=0;i<title.length;i++){
			int index = columnsIndex.get(i);
			title[i] = columns[index];
		}
		initGridTitle(title);
	}
	
	private Widget initPromptGrid(){
		VerticalPanel vp = new VerticalPanel();
		vp.add(initButtonList());
		vp.add(initDisplayConfig());
		vp.addStyleName("prompt-border");
//		Grid promptGrid = new Grid(2, 1);
//		promptGrid.setCellPadding(0);
//		promptGrid.setCellSpacing(0);
//		promptGrid.setHeight(Entry.SinkHeight);
//		promptGrid.setWidth("100%");
//		promptGrid.setWidget(0, 0, initButtonList());
//		promptGrid.setWidget(1, 0, initDisplayConfig());
//		promptGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(0, 0, "prompt-border");
//		promptGrid.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
//		promptGrid.getCellFormatter().setStyleName(1, 0, "prompt-border");
//		return promptGrid;
		return vp;
	}
	
	private Widget initButtonList(){
		PromptPanel buttonList = new PromptPanel();
		Grid buttonListGrid = new Grid(4, 1);
		buttonListGrid.setWidth("95%");
		buttonListGrid.setWidget(0, 0, FilterUser);
		buttonListGrid.setWidget(1, 0, addNewUser);
		buttonListGrid.setWidget(2, 0, downloadButton);
		buttonListGrid.setWidget(3, 0, clearButton);
		for(int i=0;i<buttonListGrid.getRowCount();i++){
			buttonListGrid.getCellFormatter().setHeight(i, 0, "28px");
			buttonListGrid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_CENTER);
		}
		buttonList.setTitleText("基本操作");
		buttonList.setBody(buttonListGrid);
		buttonList.setHeight("100%");
		return buttonList;
	}
	
	private Widget initDisplayConfig(){
		action = new RadiusUserController.RefreshAction();
		PromptPanel itemPanel = new PromptPanel();
		itemList = new Grid(columns.length-1, 1);
		itemList.setWidth("100%");
		
		for(int i=1;i<columns.length;i++){
			final ToggleButton tb = new ToggleButton();
			tb.setWidth("118px");
			tb.setHeight("18px");
			tb.setText(columns[i] + "(" + i + ")");
			if(i>Utils.RISMILE_TABLE_MAX_COLUMN_COUNT-1){
				tb.setDown(false);
			} else {
				tb.setDown(true);
			}
			tb.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String value = tb.getText();
					value = value.substring(value.indexOf("(") + 1, value.length()-1);
					Integer index = Integer.parseInt(value);
					if(tb.isDown()){
						if(columnsIndex.indexOf(index)==-1){
							columnsIndex.add(index);
						}
					} else {
						columnsIndex.remove(index);
					}
					action.onClick(event);
				}
			});
			itemList.setWidget(i-1, 0, tb);
			itemList.getCellFormatter().setHorizontalAlignment(i-1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			itemList.getCellFormatter().setHeight(i-1, 0, "28px");
		}
		
		itemPanel.setTitleText("显示配置");
		itemPanel.setBody(itemList);
		return itemPanel;
	}
	
	public Grid getGrid() {
		if (grid != null)	return grid;

		return new GreenMouseEventGrid();
	}
	
	public void onLoad()
	{
		RadiusUserController.load();
	}

    public void render(RismileTable table, boolean level)
	{
    	if(columns.length>Utils.RISMILE_TABLE_MAX_COLUMN_COUNT){
			initGridTitleNative();
		} else {
			initGridTitle(columns);
		}
    	super.render(table);
    	String[][] d = table.getData();
    	for(int loop = 0; loop < d.length; loop++) {
    		if( level ) {
    			if("0".equalsIgnoreCase(d[loop][6]))
        			grid.getRowFormatter().setStyleName(loop+1, "green");
    		}
    		else if("1".equalsIgnoreCase(d[loop][6])) {
    			grid.getRowFormatter().setStyleName(loop+1, "green");
    		}
    	}
    	double maxWidth = 0;
    	for(int i=0;i<columnsIndex.size();i++){
    		int index = columnsIndex.get(i);
    		int width = columnSize[index];
    		maxWidth = maxWidth + width;
    	}
    	for(int i=0;i<columnsIndex.size();i++){
    		int index = columnsIndex.get(i);
    		double width = columnSize[index];
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
    		if(i==0){
    			grid.getCellFormatter().setWidth(0, i, "70px");
    		} else {
    			grid.getCellFormatter().setWidth(0, i, per + "%");
    		}
    	}
    	for(int i=0;i<grid.getRowCount();i++){
    		for(int a=0;a<columnsIndex.size();a++){
    			int index = columnsIndex.get(a);
//    			if(index == 3){
//    				grid.getCellFormatter().setHorizontalAlignment(i, a, HasHorizontalAlignment.ALIGN_CENTER);
//    			}
    			if(index == 6){
    				grid.getCellFormatter().setHorizontalAlignment(i, a, HasHorizontalAlignment.ALIGN_CENTER);
    			}
    		}
    		grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_RIGHT);
    	}
	}
    
    class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOut(Element td, int column) {
			DOM.setStyleAttribute(td, "color", "");
			DOM.setStyleAttribute(td, "cursor", "pointer");
			setInfo(banner_tips);

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            String d[][] = RadiusUserController.INSTANCE.getTable().getData();

            if( RadiusUserController.INSTANCE.getTable().LEVEL ) {
                if( "0".equalsIgnoreCase(d[row-1][6]))
                	getRowFormatter().setStyleName(row, "green");
            } else
            if( "1".equalsIgnoreCase(d[row-1][6]))
            	getRowFormatter().setStyleName(row, "green");
		
		}

		@Override
		public void onMouseOver(Element td, int column) {
			DOM.setStyleAttribute(td, "color", "red");
			DOM.setStyleAttribute(td, "cursor", "hand");
			column = columnsIndex.get(column);
			setInfo(banner_text[column]);
		}
    	
    }
    
	public void disablePrivate() {
		addNewUser.setEnabled(false);
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);

		grid.unsinkEvents( Event.ONCLICK );
		grid.unsinkEvents( Event.ONMOUSEOVER );
		grid.unsinkEvents( Event.ONMOUSEOUT );
	}

	public void enablePrivate() {
		addNewUser.setEnabled(true);
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);

		grid.sinkEvents( Event.ONCLICK);
		grid.sinkEvents( Event.ONMOUSEOVER );
		grid.sinkEvents( Event.ONMOUSEOUT );
	}

	@Override
	public void doAction(int keyCode) {
		switch (keyCode) {
		case KEY.S:
			FilterUser.click();
			break;
		case KEY.A:
			if(addNewUser.isEnabled()){
				addNewUser.click();
			}
			break;
		case KEY.P:
			if(downloadButton.isEnabled()){
				downloadButton.click();
			}
			break;
		case KEY.C:
			if(clearButton.isEnabled()){
				clearButton.click();
			}
			break;
		case KEY.HOME:
		case KEY.END:
		case KEY.PAGEUP:
		case KEY.PAGEDOWN:
			navbar.doAction(keyCode);
			break;
		case KEY.K1:
			refreshTable(0);
			break;
		case KEY.K2:
			refreshTable(1);
			break;
		case KEY.K3:
			refreshTable(2);
			break;
		case KEY.K4:
			refreshTable(3);
			break;
		case KEY.K5:
			refreshTable(4);
			break;
		case KEY.K6:
			refreshTable(5);
			break;
		case KEY.K7:
			refreshTable(6);
			break;
		case KEY.K8:
			refreshTable(7);
			break;
		default:
			break;
		}
	}

	private void refreshTable(int i) {
		ToggleButton tb = (ToggleButton)itemList.getWidget(i, 0);
		String value = tb.getText();
		value = value.substring(value.indexOf("(") + 1, value.length()-1);
		Integer index = Integer.parseInt(value);
		if(tb.isDown()){
			tb.setDown(false);
		} else {
			tb.setDown(true);
		}
		if(tb.isDown()){
			if(columnsIndex.indexOf(index)==-1){
				columnsIndex.add(index);
			}
		} else {
			columnsIndex.remove(index);
		}
		action.onClick(null);
	}
    
}
