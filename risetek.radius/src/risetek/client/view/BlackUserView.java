package risetek.client.view;

import risetek.client.conf.UIConfig;
import risetek.client.control.RadiusBlackController;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
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
import com.risetek.rismile.client.utils.KEY;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;

public class BlackUserView extends RismileTableView implements IRisetekView {
	private final static String[] columns = {"序号","终端号","用户名称"};
	private final static int rowCount = 20;	

	private final static int[] columnWidth = {UIConfig.RECORD_ID_WIDTH,UIConfig.TERMINAL_ID_WIDTH,-1};
	private final static HorizontalAlignmentConstant[] columnHAlign = {
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT
	};
	
	private final Button clearButton = new Button("清除信息(C)", new RadiusBlackController.EmptyAction());
	private final Button refreshButton = new Button("刷新信息(R)", new RadiusBlackController.refreshAction());
	private final static String[] banner_text = {
		"点击直接删除该条记录.",
		"点击导入该用户为合法用户.",
		"点击导入该用户为合法用户.",
	};
	
	public BlackUserView()
	{
		addRightSide(initPromptGrid());
	}
	
	private Widget initPromptGrid(){
		final DockPanel dockpanel = new DockPanel();
		// dockpanel.setBorderWidth(1);
		dockpanel.setSize("100%", "100%");

		final VerticalPanel northpanel = new VerticalPanel();
		northpanel.setSpacing(10);
		northpanel.setWidth("100%");
		northpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		northpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		northpanel.add(clearButton);
		northpanel.add(refreshButton);
/*
		final VerticalPanel southpanel = new VerticalPanel();
		southpanel.setHeight("100%");
		southpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
*/
		final FlowPanel southpanel = new FlowPanel();
		southpanel.setSize("100%", "100%");
		
		dockpanel.add(northpanel, DockPanel.NORTH);
		dockpanel.add(southpanel, DockPanel.CENTER);
//		dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);
	
		southpanel.add(new Stick("info", "出现在表格中的用户是未经授权却正在拨号的用户。"));		
		southpanel.add(new Stick("info", "点击左侧列表中的用户条目就可以开始给不明用户分配合法的认证信息。"));
		southpanel.add(new Stick("info", "注意给用户分配的地址是不能重复的，分配认证信息不成功，有可能是分配地址出现重复。"));

		southpanel.add(new Stick("info", "使用刷新按钮能及时更新最近的不明用户信息。"));		
		southpanel.add(new Stick("info", "不明用户的信息在设备重新启动后就会清除，也能手工清除。"));
		//southpanel.add(new Stick("info", "不明用户被授权后，也会在本表格中消失。"));
		
		return dockpanel;
	}
	
    private boolean mayColor = false;

	@Override
	public Grid getGrid() {
		
		if( grid == null ) {
			grid = new MouseEventGrid() {
				@Override
				public void onMouseOut(Element td, int column) {
					td.getStyle().clearColor();
					td.getStyle().setCursor(Cursor.DEFAULT);
					setInfo("");
				}

				@Override
				public void onMouseOver(Element td, int column) {
					if(mayColor == true) {
						td.getStyle().setColor("red");
						td.getStyle().setCursor(Cursor.POINTER);
						setInfo(banner_text[column]);
					}
				}
			};
			formatGrid(grid, rowCount, columns, columnWidth, columnHAlign);
			grid.addClickHandler(new RadiusBlackController.TableAction());
		}
		return grid;		
	}

	@Override
	public void onLoad()
	{
		RadiusBlackController.load();
	}
	
	@Override
	public void disablePrivate() {
		mayColor = false;
		grid.unsinkEvents( Event.ONCLICK );
	}

	@Override
	public void enablePrivate() {
		mayColor = true;
		grid.sinkEvents( Event.ONCLICK );
	}
	
	@Override
	public String[] parseRow(Node node) {
		String[] data = new String[3];
		com.google.gwt.xml.client.Element logElement = (com.google.gwt.xml.client.Element)node;
		data[0] = logElement.getFirstChild().getNodeValue();
		data[1] = XMLDataParse.getElementText( logElement, "IMSI" );
		data[2] = XMLDataParse.getElementText( logElement, "USER" );
		return data;
	}	
	
	public void render(RismileTable table)
	{
		for( int index = 0; index < (grid.getRowCount() - 1); index++) {
			renderLine(table, index);
		}
    	renderStatistic(table);
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
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
