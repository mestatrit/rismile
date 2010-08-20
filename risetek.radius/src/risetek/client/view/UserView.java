package risetek.client.view;

import risetek.client.conf.UIConfig;
import risetek.client.control.RadiusUserController;
import risetek.client.model.RismileUserTable;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.risetek.rismile.client.conf.RuntimeConf;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserView extends RismileTableView  implements IRisetekView {

	private final Button FilterUser = new Button("过滤信息(S)", new RadiusUserController.FilterUserAction());
	private final Button addNewUser = new Button("添加用户(A)", new RadiusUserController.AddUserAction());
	private final Button downloadButton = new Button("导出文件(P)", new ClickHandler() {
		public void onClick(ClickEvent event) {
			Window.open("forms/exportusers", "_self", "");
		}
	});
	private final Button clearButton = new Button("用户总清(C)", new RadiusUserController.EmptyAction());
	private final Button RefreshUser = new Button("刷新信息(R)", new RadiusUserController.RefreshAction());

	public final static String[] columns = {"序号", "", "终端号","用户名称","口令","分配地址", "第二地址", "状态", "登录时段","备注"};
	public final static int rowCount = UIConfig.numberOfGridRows;
	
	private final static int[] columnWidth = {
		UIConfig.RECORD_ID_WIDTH,
		0,
		UIConfig.TERMINAL_ID_WIDTH,
		UIConfig.USER_NAME_WIDTH,
		50,
		UIConfig.IPADDRESS_WIDTH,
		0,
		0,
		0,
		-1};
	
	private final static HorizontalAlignmentConstant[] columnHAlign = {
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_LEFT
	};
	
	
	String banner_tips = "";
	private final static String[] banner_text = {
		"点击删除该条记录.",
		"点击修改警示级别.",
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
	
	public UserView() {
		addRightSide(initPromptGrid());
	}

	private Widget initPromptGrid(){
		final DockPanel dockpanel = new DockPanel();
		dockpanel.setHeight("100%");

		final VerticalPanel northpanel = new VerticalPanel();
		northpanel.setSpacing(10);
		northpanel.setWidth("100%");
		northpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		northpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		final VerticalPanel southpanel = new VerticalPanel();
		southpanel.setHeight("100%");
		southpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		
		dockpanel.add(northpanel, DockPanel.NORTH);
		dockpanel.add(southpanel, DockPanel.SOUTH);
		dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);

		northpanel.add(FilterUser);
		northpanel.add(addNewUser);
		northpanel.add(downloadButton);
		northpanel.add(clearButton);
		northpanel.add(RefreshUser);
		
		if( null == RuntimeConf.getConfig("ASSERT") )
			southpanel.add(new Stick("info", "使用刷新信息按钮能及时更新用户信息。"));
		else
			southpanel.add(new Stick("info", "序号后的图标表达不在线用户的警示级别。"));
		
		southpanel.add(new Stick("info", "绿底色的条目表示该用户在线。"));
		southpanel.add(new Stick("info", "点击不同条目的不同列，能够修改这个用户的该项数据。"));
		southpanel.add(new Stick("info", "注意给用户分配的地址是不能重复的。"));
		southpanel.add(new Stick("info", "终端号和用户名称的组合确定一个用户，因此也不能重复。"));
		
		return dockpanel;
	}
	
	public Grid getGrid() {
		if( grid == null ) {
			grid = new GreenMouseEventGrid();
			if( null != RuntimeConf.getConfig("ASSERT") )
				columnWidth[1] = 15;
			formatGrid(grid, rowCount, columns, columnWidth, columnHAlign);
			grid.addClickHandler(new RadiusUserController.TableAction());
	    	formatter = grid.getRowFormatter();
		}
		return grid;		
	}
	
	public void onLoad()
	{
		RadiusUserController.load();
	}

	private RowFormatter formatter;
	
	@Override
	public String[] parseRow(Node node) {
		com.google.gwt.xml.client.Element logElement = (com.google.gwt.xml.client.Element)node;
		String[] data = new String[10];
		data[0] = logElement.getFirstChild().getNodeValue();
		data[1] = null;
		data[2] = XMLDataParse.getElementText( logElement, "IMSI" );
		data[3] = XMLDataParse.getElementText( logElement, "USER" );
		data[4] = "****";
		data[5] = IPConvert.longString2IPString(XMLDataParse.getElementText( logElement, "ADDRESS" ));
		data[6] = XMLDataParse.getElementText( logElement, "SADD" );
		data[7] = XMLDataParse.getElementText( logElement, "STATUS" );
		data[8] = XMLDataParse.getElementText( logElement, "AssertLevel" );
		data[9] = XMLDataParse.getElementText( logElement, "NOTE" );
		return data;
	}

	@Override
    public void renderLineCallback(RismileTable table, String[] srcRowData, int line) {

		Style style = formatter.getElement(line+1).getStyle();
		if( ((RismileUserTable)table).LEVEL ) {
			if("0".equalsIgnoreCase(srcRowData[7])) {
				style.setBackgroundColor(UIConfig.USER_TABLE_GREE_COLOR);
			} else if( "2".equalsIgnoreCase(srcRowData[7]) ) {
				style.setBackgroundColor(UIConfig.USER_TABLE_WARNING_COLOR);
			}
			else
				style.clearBackgroundColor();
			
			// TODO: 添入图标
			if( "2".equalsIgnoreCase(srcRowData[8]) ) 
				getGrid().getCellFormatter().setStyleName(line+1, 1, "backIcons");
			else
				getGrid().getCellFormatter().removeStyleName(line+1, 1, "backIcons");
		}
		else
		{
			if("1".equalsIgnoreCase(srcRowData[7])) {
				style.setBackgroundColor(UIConfig.USER_TABLE_GREE_COLOR);
			}
			else
				style.clearBackgroundColor();
		}
	};

	@Override
    public void cleanLineCallback(int line) {
    	// 清除以前的背景。
		formatter.getElement(line+1).getStyle().clearBackgroundColor();
		getGrid().getCellFormatter().removeStyleName(line+1, 1, "backIcons");
	};
	
    public void render(RismileUserTable table)
	{
    	for( int index = 0; index < rowCount; index++) {
    		renderLine(table, index);
    	}
    	renderStatistic(table);
	}
    
    private boolean mayColor = false;
    class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOut(Element td, int column) {
			DOM.removeElementAttribute(td, "title");			

			td.getStyle().setOverflow(Overflow.HIDDEN);
			td.getStyle().clearColor();
			td.getStyle().setCursor(Cursor.DEFAULT);
			setInfo(banner_tips);

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            renderLine(RadiusUserController.INSTANCE.getTable(), row-1);
		}

		@Override
		public void onMouseOver(Element td, int column) {
			td.getStyle().setOverflow(Overflow.VISIBLE);
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
			
			if(mayColor == true) {
				td.getStyle().setColor("red");
				setInfo(banner_text[column]);
				td.getStyle().setCursor(Cursor.POINTER);
			}
		}
    }
    
	public void disablePrivate() {
		mayColor = false;
		addNewUser.setEnabled(false);
		downloadButton.setEnabled(false);
		clearButton.setEnabled(false);
//		RefreshUser.setEnabled(false);
		grid.unsinkEvents( Event.ONCLICK );
	}

	public void enablePrivate() {
		mayColor = true;
		addNewUser.setEnabled(true);
		downloadButton.setEnabled(true);
		clearButton.setEnabled(true);
//		RefreshUser.setEnabled(true);
		grid.sinkEvents( Event.ONCLICK);
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
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
		case KEY.R:
			if(RefreshUser.isEnabled()){
				RefreshUser.click();
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
	
	//private Grid itemList;
	//private ClickHandler action;

	private void refreshTable(int i) {
		
		return;
		/*
		ToggleButton tb = (ToggleButton)itemList.getWidget(i, 0);
		String value = tb.getText();
		value = value.substring(value.indexOf("(") + 1, value.length()-1);
//		Integer index = Integer.parseInt(value);
		if(tb.isDown()){
			tb.setDown(false);
		} else {
			tb.setDown(true);
		}
		action.onClick(null);
		*/
	}
}
