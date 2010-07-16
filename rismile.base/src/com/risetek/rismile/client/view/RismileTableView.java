package com.risetek.rismile.client.view;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends Composite {

	public final Grid grid = getGrid();
	public static ArrayList<Integer> columnsIndex = new ArrayList<Integer>();
    
	protected final VerticalPanel outer = new VerticalPanel();
	private final DockPanel toolPanel = new DockPanel();
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();
	public final NavBar navbar;
    private final Label infoLabel = new Label("");
    private final Label statisticLabel = new Label("");	
    Grid main = new Grid(1, 2);
    public static int specialMarker = -1;
    
	// 数据表的顺序
	String[] columnStyles;
//	String[] columns;
	int rowCount;
	
	public RismileTableView(int rowCount) {
		this(rowCount, true);
	}

	public RismileTableView(int rowCount, boolean toolBar) {
		initWidget(main);
		main.setCellPadding(0);
		main.setCellSpacing(0);
		main.getCellFormatter().addStyleName(0, 0, "mainLeft");
//        if (columns.length == 0) {
//	        throw new IllegalArgumentException(
//	        "expecting a positive number of columns");
//	    }
//
//	    if (columnStyles != null && columns.length != columnStyles.length) {
//	        throw new IllegalArgumentException("expecting as many styles as columns");
//	    }
//	    
//	    this.columns = columns;
//	    this.columnStyles = columnStyles;
	    this.rowCount = rowCount;

	    navbar = new NavBar();
	    
	    //this.rowCount = rowCount;
	    outer.setHeight(Entry.SinkHeight);
	    outer.setWidth("100%");
	    setStyleName("rismiletable");
	    //toolPanel.setBorderWidth(1);
	    toolPanel.setStyleName("navbar");
	    outer.add(toolPanel);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    
	    // 总数提示
	    Grid total = new Grid(1,1);
	    //total.setBorderWidth(1);
	    total.setWidth("7em");
	    toolPanel.add(total, DockPanel.EAST);
	    statisticLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    total.setWidget(0, 0, statisticLabel);
	    
		toolPanel.add(buttonsPanel, DockPanel.EAST);
	    toolPanel.setCellHorizontalAlignment(buttonsPanel, DockPanel.ALIGN_RIGHT);
	    toolPanel.add(infoLabel, DockPanel.WEST);
		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
	    toolPanel.setCellWidth(infoLabel, "100%");
	    infoLabel.setStyleName("info-Label");
	    
	    grid.setStyleName("table");
	    outer.add(grid);
	    if(!toolBar) {
			main.setWidth("100%");
			main.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_TOP);
			main.getColumnFormatter().setWidth(0, "90%");
			main.getColumnFormatter().setWidth(1, "10%");
			main.setWidget(0, 0, outer);
			main.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
	    }
	}
	
	public void initGridTitle(String[] columns){
		// 初始化网格的格式
	    grid.resize(rowCount + 1, columns.length);

	    for (int i = 0 ; i < grid.getColumnCount(); i++) {
	        grid.setText(0, i, columns[i]);
            grid.getCellFormatter().setStyleName(0, i, "header");
	    }
	}
	
	public void setPromptPanel(Widget promptPanel){
		main.setWidget(0, 1, promptPanel);
	}
	
	public abstract Grid getGrid();
	
	public void setInfo(String text){
		infoLabel.setText(text);
	}
    public void addToolButton(Widget button){
		buttonsPanel.add(button);
	}

    public void setStatisticText(int total){
    	statisticLabel.setText("总数："+Integer.toString(total));
    }

    public void render(RismileTable table)
	{
		initGridStyle();
    	String data[][] = table.getData();
		int destRowCount = grid.getRowCount() - 1;
		int destColCount = grid.getCellCount(0);
		CellFormatter formatter = grid.getCellFormatter();
		int srcRowIndex = 0;
		int srcRowCount = (data.length > destRowCount ) ? destRowCount : data.length;

		int destRowIndex = 1; // 留出表格头这一行
		
		//idArray = new String[srcRowCount+1];
		for (; srcRowIndex < srcRowCount; ++srcRowIndex, ++destRowIndex) {
			String[] srcRowData = data[srcRowIndex];
				srcRowData = new String[columnsIndex.size()];
				String[] temp = data[srcRowIndex];
				for(int i=0;i<srcRowData.length;i++){
					int index = columnsIndex.get(i);
					if(specialMarker == 1){
						if(index == 6){
							String tempValue = temp[index];
							if("0".equals(tempValue)){
								tempValue = "不关注";
							} else if("1".equals(tempValue)){
								tempValue = "一般用户";
							} else if("2".equals(tempValue)){
								tempValue = "重点用户";
							}
							srcRowData[i] = tempValue;
							continue;
						}
					}
					srcRowData[i] = temp[index];
				}

			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				String cellHTML = srcRowData[srcColIndex];
				grid.setText(destRowIndex, srcColIndex, cellHTML);
				// ie 下的边框表现不正常。
				if( "".equalsIgnoreCase( cellHTML ))
					grid.clearCell(destRowIndex, srcColIndex);
				if(srcColIndex == 0){
					formatter.addStyleName(destRowIndex, srcColIndex, "start");
				} else if (srcColIndex == destColCount-1){
					formatter.addStyleName(destRowIndex, srcColIndex, "end");
				} else {
					formatter.addStyleName(destRowIndex, srcColIndex, "mid");
				}
				formatter.getElement(destRowIndex, srcColIndex).setAttribute("nowrap", "nowrap");
			}
		}

		// Clear remaining table rows.
		//
		//boolean isLastPage = false;
		for (; destRowIndex < destRowCount + 1; ++destRowIndex) {
			//isLastPage = true;
			for (int destColIndex = 0; destColIndex < destColCount; ++destColIndex) {
				grid.clearCell(destRowIndex, destColIndex);
				if(destColIndex == 0){
					formatter.addStyleName(destRowIndex, destColIndex, "start");
				} else if (destColIndex == destColCount-1){
					formatter.addStyleName(destRowIndex, destColIndex, "end");
				} else {
					formatter.addStyleName(destRowIndex, destColIndex, "mid");
				}
			}
			grid.getRowFormatter().setStyleName(destRowIndex, "normal");
		}

		for(int i=0;i<destColCount;i++){
			if(i == 0){
				formatter.setStyleName(destRowCount, i, "laststart");
			} else if(i == destColCount - 1) {
				formatter.setStyleName(destRowCount, i, "lastend");
			} else {
				formatter.setStyleName(destRowCount, i, "lastmid");
			}
		}
		
		setStatisticText( table.getSum() );
		
		boolean goFirst;
		boolean goPrev;
		boolean goNext;
		boolean goLast;
		// 设定导航状态
		if( table.ASC )
		{
			goFirst = table.getOffset() > 0;
			goPrev = (table.getOffset() - table.getLimit()) > 0;
			goNext = (table.getOffset() + table.getLimit()) < table.getSum();
			goLast = (table.getOffset() + table.getLimit()) < table.getSum();
		}
		else
		{
			goFirst = (table.getOffset() + table.getLimit()) < table.getSum();
			goPrev = (table.getOffset() + table.getLimit()) < table.getSum();
			goNext = (table.getOffset() - table.getLimit()) > 0;
			goLast = table.getOffset() > 0;
		}
		navbar.enabelNavbar(goFirst, goPrev, goNext, goLast);
	}
    
    private void initGridStyle() {
    	setInfo("");
		int row = grid.getRowCount();
		int col = grid.getColumnCount();
		for(int i=1;i<row;i++){
			grid.getRowFormatter().setStyleName(i, "normal");
			if(i!=row-1){
				for(int a=0;a<col;a++){
					String style = "";
					if(a == 0){
						style = "start";
					} else if(a == col-1) {
						style = "end";
					} else {
						style = "mid";
					}
					grid.getCellFormatter().setStyleName(i, a, style);
					grid.getCellFormatter().getElement(i, a).removeAttribute("style");
				}
			} else {
				for(int a=0;a<col;a++){
					String style = "";
					if(a == 0){
						style = "laststart";
					} else if(a == col-1) {
						style = "lastend";
					} else {
						style = "lastmid";
					}
					grid.getCellFormatter().setStyleName(i, a, style);
					grid.getCellFormatter().getElement(i, a).removeAttribute("style");
				}
			}
		}
	}
}
