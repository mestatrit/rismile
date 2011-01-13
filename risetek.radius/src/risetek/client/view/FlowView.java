package risetek.client.view;
import risetek.client.control.RadiusUserFlowController;
import risetek.client.model.RismileUserFlowTable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
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

public class FlowView extends RismileTableView implements IRisetekView {
	private final static String[] columns = {"序号","记录时间","终端号","用户名称","输入流量","输出流量","会话时长"};
	private final static int[] columnWidth = {
		UIConfig.RECORD_ID_WIDTH,
		UIConfig.DATETIME,
		risetek.client.conf.UIConfig.TERMINAL_ID_WIDTH,
		risetek.client.conf.UIConfig.USER_NAME_WIDTH,
		risetek.client.conf.UIConfig.SESSION_FLOW,
		risetek.client.conf.UIConfig.SESSION_FLOW,
		-1};
	
	private final static HorizontalAlignmentConstant[] columnHAlign = {
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_CENTER,
		HasHorizontalAlignment.ALIGN_LEFT,
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_RIGHT,
		HasHorizontalAlignment.ALIGN_RIGHT
	};
	
	
	private final static int rowCount = 20;	
	
	public final Button clearButton = new Button("清除记录(C)", new RadiusUserFlowController.ClearFlowAction());
	private final Button downloadButton = new Button("导出记录(P)", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			Window.open("flow/export/", "_self", "");
		}
	});
	private Button FilterLog = new Button("过滤信息(S)", new RadiusUserFlowController.FilterFlowAction());
	
	String banner_tips = "";

	public void setBannerTips(String tips)
	{
		banner_tips = tips;
		setInfo(banner_tips);
	}
	
	public FlowView()
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
		northpanel.add(clearButton);
		northpanel.add(downloadButton);

		final FlowPanel southpanel = new FlowPanel();
		southpanel.setSize("100%", "100%");
		
		dockpanel.add(northpanel, DockPanel.NORTH);
		dockpanel.add(southpanel, DockPanel.CENTER);
		//southpanel.setBorderWidth(1);
		dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);

		southpanel.add(new Stick("info", "请及时导出数据，并清空数据记录。"));
		southpanel.add(new Stick("info", "导出记录能以文本文件格式将流量记录保存为本地文件，并用Excel等工具进行分析处理。"));		
		southpanel.add(new Stick("info", "过滤信息功按照输入的关键字匹配记录中的信息。"));
		southpanel.add(new Stick("info", "过滤信息的关键字为空，则取消匹配。"));
		southpanel.add(new Stick("info", "注意是否有信息被过滤的提示信息，如果存在，您可能没看到全部的信息。"));		
		
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
		RadiusUserFlowController.load();
	}
	
	@Override
	public String[] parseRow(Node node) {
		String[] data = new String[7];
		com.google.gwt.xml.client.Element logElement = (com.google.gwt.xml.client.Element)node;
		data[0] = logElement.getFirstChild().getNodeValue();
		data[1] = XMLDataParse.getElementText( logElement, "end_time" );
		data[2] = XMLDataParse.getElementText( logElement, "station" );
		data[3] = XMLDataParse.getElementText( logElement, "user" );
		data[4] = XMLDataParse.getElementText( logElement, "input" );
		data[5] = XMLDataParse.getElementText( logElement, "output" );
		data[6] = XMLDataParse.getElementText( logElement, "session_time" );
		return data;
	}
	
	public void render(RismileUserFlowTable table)
	{
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
