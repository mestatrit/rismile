package com.risetek.rismile.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends Composite implements IView{

	public final Grid grid = getGrid();
    
	private final DockPanel outer = new DockPanel();
	private final DockPanel toolPanel = new DockPanel();
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();
	public final NavBar navbar;
    private final Label infoLabel = new Label("");
    
    private final Label statisticLabel = new Label("");	
    
	private String[] idArray;
	protected int tmpStartRow = 0; 
	public int startRow = 0;
	//public int rowCount = 0;
	public int TotalRecord = 0;
	public String focusID;
	public String focusValue;
	
	public int currentRow;
	
	//public String focusedRowID;
	boolean enableRowId = true;
	// 数据表的顺序
	String[] columnStyles;
	protected String condition = null;

	public RismileTableView(String[] columns, String[] columnStyles, int rowCount, RismileTableController control) {
		
        if (columns.length == 0) {
	        throw new IllegalArgumentException(
	        "expecting a positive number of columns");
	    }

	    if (columnStyles != null && columns.length != columnStyles.length) {
	        throw new IllegalArgumentException("expecting as many styles as columns");
	    }
	    this.columnStyles = columnStyles;

	    navbar = new NavBar(control);
	    
	    //this.rowCount = rowCount;
	    
	    outer.addStyleName("rismile-page-container");
	    initWidget(outer);
	    
	    outer.addStyleName("rismiletable");
	    
	    toolPanel.setStyleName("navbar");
	    outer.add(toolPanel, DockPanel.NORTH);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    toolPanel.add(new HTML("&nbsp"), DockPanel.EAST);
	    
	    // 加一个间隔
	    Label l = new Label("");
	    l.setStyleName("rismile-Nav-Label");
	    l.setWidth("1em");
	    toolPanel.add(l, DockPanel.EAST);

	    statisticLabel.setStyleName("rismile-Nav-Label");
	    statisticLabel.setWidth("4em");
	    toolPanel.add(statisticLabel, DockPanel.EAST);
	    // 总数提示
	    l = new Label("总数:", false);
	    l.setStyleName("rismile-Nav-Label");
	    l.setWidth("3em");
	    toolPanel.add(l, DockPanel.EAST);

	    
		toolPanel.add(buttonsPanel, DockPanel.EAST);
	    toolPanel.setCellHorizontalAlignment(buttonsPanel, DockPanel.ALIGN_RIGHT);
	    toolPanel.add(infoLabel, DockPanel.WEST);
		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
	    toolPanel.setCellWidth(infoLabel, "100%");
	    infoLabel.setStyleName("rismile-info-Label");
	    
	    grid.setStyleName("table");
	    outer.add(grid, DockPanel.CENTER);
	    
	    //outer.add(searchBar, DockPanel.SOUTH);
	    // 初始化网格的格式
	    grid.resize(rowCount + 1, columns.length);
	    for (int i = 0 ; i < columns.length; i++) {
	        grid.setText(0, i, columns[i]);
	        if (columnStyles != null) {
	            grid.getCellFormatter().setStyleName(0, i, columnStyles[i] + " header");
	        }
	    }
	    
	}
	public abstract Grid getGrid();
	
	public void clearInfo() {
		infoLabel.setText("");
	}
	public void setInfo(String text){
		infoLabel.setText(text);
	}
    public void addToolButton(Widget button){
		buttonsPanel.add(button);
	}

	public void setRowCount(int rows) {
	    grid.resizeRows(rows);
	}

	public int getDataRowCount() {
	    return grid.getRowCount() - 1;
	}
	public String getRowId(int row){
		String id ="null";
		if(idArray.length > row){
			id = idArray[row];
		}
		return id;
	}

	public int getHeight(){
		return outer.getOffsetHeight();
	}
	
    public void setStatisticText(String text){
    	statisticLabel.setText(text);
    }
	
	public void render(RismileTable table)
	{
		String data[][] = table.getData();
		int total = table.getSum();
		int destRowCount = getDataRowCount();
		int destColCount = grid.getCellCount(0);
		CellFormatter formatter = grid.getCellFormatter();
		int srcRowIndex = 0;
		int srcRowCount = (data.length > grid.getRowCount() - 1) ? grid
				.getRowCount() - 1 : data.length;

		int destRowIndex = 1; // skip navbar row
		
		idArray = new String[srcRowCount+1];
		for (; srcRowIndex < srcRowCount; ++srcRowIndex, ++destRowIndex) {
			String[] srcRowData = data[srcRowIndex];

			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				String cellHTML = srcRowData[srcColIndex];
				if(srcColIndex == 0 && enableRowId){
					idArray[destRowIndex] = cellHTML;
					cellHTML = Long.toString(table.getOffset() + destRowIndex);
				}
				grid.setText(destRowIndex, srcColIndex, cellHTML);
				formatter.addStyleName(destRowIndex, srcColIndex, columnStyles[srcColIndex]);
			}
		}

		// Clear remaining table rows.
		//
		//boolean isLastPage = false;
		for (; destRowIndex < destRowCount + 1; ++destRowIndex) {
			//isLastPage = true;
			for (int destColIndex = 0; destColIndex < destColCount; ++destColIndex) {
				grid.clearCell(destRowIndex, destColIndex);
			}
		}

		// Synchronize the nav buttons.
		// enabledNavBar(total);
		// Update the status message.
		//
		//setStatusText((startRow + 1) + " - " + (startRow + srcRowCount));
		
		setStatisticText("" + total);
		TotalRecord = total;
		
		// 设定导航状态
		boolean goFirst = table.getOffset() > 0;
		boolean goPrev = (table.getOffset() - table.getLimit()) > 0;
		boolean goNext = (table.getOffset() + table.getLimit()) < table.getSum();
		boolean goLast = table.getOffset() < table.getSum();
		navbar.enabelNavbar(goFirst, goPrev, goNext, goLast);
	}

}
