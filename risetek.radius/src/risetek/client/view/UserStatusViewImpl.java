package risetek.client.view;

import risetek.client.conf.UIConfig;
import risetek.client.control.RadiusUserStatusController;
import risetek.client.model.RismileUserStatusTable;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
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
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class UserStatusViewImpl extends RismileTableView  implements IRisetekView {

	private final Button FilterUser = new Button("过滤信息(S)", new RadiusUserStatusController.FilterUserAction());
	private final Button RefreshUser = new Button("刷新信息(R)", new RadiusUserStatusController.RefreshAction());

	public final static String[] columns = {"序号", "终端号","用户名称","分配地址", "本次流量","备注"};
	public final static int rowCount = UIConfig.numberOfGridRows;
	
	private final static int[] columnWidth = {
		UIConfig.RECORD_ID_WIDTH,
		UIConfig.TERMINAL_ID_WIDTH,
		UIConfig.USER_NAME_WIDTH,
		UIConfig.IPADDRESS_WIDTH,
		UIConfig.SESSION_WIDTH,
		-1};
	
	private final static HorizontalAlignmentConstant[] columnHAlign = {
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT
	};
	
	
	String banner_tips = "";
	
	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public UserStatusViewImpl() {
		addRightSide(initPromptGrid());
	}

	private Widget initPromptGrid(){
		final DockPanel dockpanel = new DockPanel();
		dockpanel.setSize("100%", "100%");

		final VerticalPanel northpanel = new VerticalPanel();
		northpanel.setSpacing(10);
		northpanel.setWidth("100%");
		northpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		northpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		northpanel.add(FilterUser);
		northpanel.add(RefreshUser);
/*
		final VerticalPanel southpanel = new VerticalPanel();
		southpanel.setHeight("100%");
		southpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
*/		
		final FlowPanel southpanel = new FlowPanel();
		southpanel.setSize("100%", "100%");

		dockpanel.add(northpanel, DockPanel.NORTH);
		dockpanel.add(southpanel, DockPanel.CENTER);
		//dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);

	
		southpanel.add(new Stick("info", "流量信息是该次链接建立后的总流量。分成了输入流量和输出流量两个部分显示。"));
		southpanel.add(new Stick("info", "使用刷新信息按钮能及时更新用户状态信息。"));
		southpanel.add(new Stick("info", "绿底色的条目表示该用户在线。"));
		southpanel.add(new Stick("info", "红底色的条目表示该关注的用户不在线。"));
		
		return dockpanel;
	}
	
	@Override
	public Grid getGrid() {
		if( grid == null ) {
			grid = new GreenMouseEventGrid();
			formatGrid(grid, rowCount, columns, columnWidth, columnHAlign);
	    	rowFormatter = grid.getRowFormatter();
		}
		return grid;		
	}
	
	@Override
	public void onLoad()
	{
		RadiusUserStatusController.load();
	}

	@Override
	public String[] parseRow(Node node) {
		com.google.gwt.xml.client.Element logElement = (com.google.gwt.xml.client.Element)node;
		String[] data = new String[13];
		data[0] = logElement.getFirstChild().getNodeValue();
		data[1] = XMLDataParse.getElementText( logElement, "IMSI" );
		data[2] = XMLDataParse.getElementText( logElement, "USER" );
		data[3] = IPConvert.longString2IPString(XMLDataParse.getElementText( logElement, "ADDRESS" ));
		data[4] = "";
		data[5] = XMLDataParse.getElementText( logElement, "NOTE" );
		data[6] = XMLDataParse.getElementText( logElement, "STATUS" );
		data[7] = XMLDataParse.getElementText( logElement, "AssertLevel" );
		data[8] = XMLDataParse.getElementText( logElement, "InputOctets" );
		data[9] = XMLDataParse.getElementText( logElement, "OutputOctets" );
		data[10] = XMLDataParse.getElementText( logElement, "Begin" );
		data[11] = XMLDataParse.getElementText( logElement, "TInput" );
		data[12] = XMLDataParse.getElementText( logElement, "TOutput" );
		return data;
	}

	@Override
    public void renderLineCallback(RismileTable table, String[] srcRowData, int line) {

		Style style = rowFormatter.getElement(line+1).getStyle();
		if( ((RismileUserStatusTable)table).LEVEL ) {
			if("0".equalsIgnoreCase(srcRowData[6])) {
				style.setBackgroundColor(UIConfig.USER_TABLE_GREE_COLOR);
			} else if( "2".equalsIgnoreCase(srcRowData[6]) ) {
				style.setBackgroundColor(UIConfig.USER_TABLE_WARNING_COLOR);
			}
			else
				style.clearBackgroundColor();
			
		}
		else
		{
			if("1".equalsIgnoreCase(srcRowData[8])) {
				style.setBackgroundColor(UIConfig.USER_TABLE_GREE_COLOR);
			}
			else
				style.clearBackgroundColor();
		}
		
		if( ! "NULL".equals(srcRowData[8])) {
			getGrid().setText(line+1, 4, srcRowData[8] + "/" + srcRowData[9]);
		}
		
	};

	@Override
    public void cleanLineCallback(int line) {
    	// 清除以前的背景。
		rowFormatter.getElement(line+1).getStyle().clearBackgroundColor();
	};
	
    public void render(RismileUserStatusTable table)
	{
    	for( int index = 0; index < rowCount; index++) {
    		renderLine(table, index);
    	}
    	renderStatistic(table);
	}
    
    class GreenMouseEventGrid extends MouseEventGrid {

		@Override
		public void onMouseOut(Element td, int column) {
			DOM.removeElementAttribute(td, "title");			
			td.getStyle().setOverflow(Overflow.HIDDEN);
			td.getStyle().clearColor();
			setInfo(banner_tips);

            Element tr = DOM.getParent(td);
            Element body = DOM.getParent(tr);
            int row = DOM.getChildIndex(body, tr);
            if(row == 0) return;
            renderLine(RadiusUserStatusController.INSTANCE.getTable(), row-1);
		}

		@Override
		public void onMouseOver(Element td, int column) {
			td.getStyle().setOverflow(Overflow.VISIBLE);
			String title = td.getInnerText();
			
			if((column > 1) && (null != title) && !("".equals(title)) && !(" ".equalsIgnoreCase(title))) {
				DOM.setElementAttribute(td, "title", td.getInnerText());			
			}
		}
    }
    
	@Override
	public void disablePrivate() {
	}

	@Override
	public void enablePrivate() {
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		switch (keyCode) {
		case KEY.S:
			FilterUser.click();
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
		default:
			break;
		}
	}
}
