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
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends Composite {

	public final Grid grid = getGrid();
    
	private final DockPanel outer = new DockPanel();
	private final DockPanel toolPanel = new DockPanel();
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();
	public final NavBar navbar;
    private final Label infoLabel = new Label("");
    
    private final Label statisticLabel = new Label("");	
    
	// 数据表的顺序
	String[] columnStyles;

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
	    outer.setHeight(Entry.SinkHeight);
	    initWidget(outer);
	    setStyleName("rismiletable");
	    
	    toolPanel.setStyleName("navbar");
	    outer.add(toolPanel, DockPanel.NORTH);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    toolPanel.add(new HTML("&nbsp"), DockPanel.EAST);
	    
	    // 加一个间隔
	    Label l = new Label("");
	    l.setStyleName("NavLabel");
	    l.setWidth("1em");
	    toolPanel.add(l, DockPanel.EAST);

	    statisticLabel.setStyleName("NavLabel");
	    statisticLabel.setWidth("4em");
	    toolPanel.add(statisticLabel, DockPanel.EAST);
	    // 总数提示
	    l = new Label("总数:", false);
	    l.setStyleName("NavLabel");
	    l.setWidth("3em");
	    toolPanel.add(l, DockPanel.EAST);

	    
		toolPanel.add(buttonsPanel, DockPanel.EAST);
	    toolPanel.setCellHorizontalAlignment(buttonsPanel, DockPanel.ALIGN_RIGHT);
	    toolPanel.add(infoLabel, DockPanel.WEST);
		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
	    toolPanel.setCellWidth(infoLabel, "100%");
	    infoLabel.setStyleName("info-Label");
	    
	    grid.setStyleName("table");
	    outer.add(grid, DockPanel.CENTER);
	    
	    // 初始化网格的格式
	    grid.resize(rowCount + 1, columns.length);
	    for (int i = 0 ; i < columns.length; i++) {
	        grid.setText(0, i, columns[i]);
	        if (columnStyles != null) {
//	            grid.getCellFormatter().setStyleName(0, i, columnStyles[i] + " header");
	            grid.getCellFormatter().setStyleName(0, i, "header");
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

	/*
	public String getRowId(int row){
		String id ="null";
		if(idArray.length > row){
			id = idArray[row];
		}
		return id;
	}
	*/

    public void setStatisticText(int total){
    	statisticLabel.setText(Integer.toString(total));
    }

    public void render(RismileTable table)
	{
		String data[][] = table.getData();
		//int total = table.getSum();
		int destRowCount = grid.getRowCount() - 1;
		int destColCount = grid.getCellCount(0);
		CellFormatter formatter = grid.getCellFormatter();
		int srcRowIndex = 0;
		int srcRowCount = (data.length > destRowCount ) ? destRowCount : data.length;

		int destRowIndex = 1; // 留出表格头这一行
		
		//idArray = new String[srcRowCount+1];
		for (; srcRowIndex < srcRowCount; ++srcRowIndex, ++destRowIndex) {
			String[] srcRowData = data[srcRowIndex];

			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				String cellHTML = srcRowData[srcColIndex];
				/*
				if(srcColIndex == 0 && enableRowId){
					idArray[destRowIndex] = cellHTML;
					cellHTML = Long.toString(table.getOffset() + destRowIndex);
				}
				*/
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

		setStatisticText( table.getSum() );
		
		// 设定导航状态
		boolean goFirst = table.getOffset() > 0;
		boolean goPrev = (table.getOffset() - table.getLimit()) > 0;
		boolean goNext = (table.getOffset() + table.getLimit()) < table.getSum();
		boolean goLast = table.getOffset() < table.getSum();
		navbar.enabelNavbar(goFirst, goPrev, goNext, goLast);
	}

}
