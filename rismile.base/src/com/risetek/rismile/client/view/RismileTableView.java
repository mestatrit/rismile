package com.risetek.rismile.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends Composite {

	public final Grid grid = getGrid();
    
	private final VerticalPanel outer = new VerticalPanel();
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
	    //toolPanel.setBorderWidth(1);
	    toolPanel.setStyleName("navbar");
	    outer.add(toolPanel);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    
	    statisticLabel.setWidth("5em");
	    statisticLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    toolPanel.add(statisticLabel, DockPanel.EAST);
	    // 总数提示
	    Label l = new Label("总数:", false);
	    l.setWidth("4em");
	    l.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
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
	    outer.add(grid);
	    
	    // 初始化网格的格式
	    grid.resize(rowCount + 1, columns.length);
	    //grid.getRowFormatter().addStyleName(0, "header");
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

    public void setStatisticText(int total){
    	statisticLabel.setText(Integer.toString(total));
    }

    public void render(RismileTable table)
	{
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

			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				String cellHTML = srcRowData[srcColIndex];
				grid.setText(destRowIndex, srcColIndex, cellHTML);
				formatter.addStyleName(destRowIndex, srcColIndex, columnStyles[srcColIndex]);
			}
			grid.getRowFormatter().setStyleName(destRowIndex, "normal");
		}

		// Clear remaining table rows.
		//
		//boolean isLastPage = false;
		for (; destRowIndex < destRowCount + 1; ++destRowIndex) {
			//isLastPage = true;
			for (int destColIndex = 0; destColIndex < destColCount; ++destColIndex) {
				grid.clearCell(destRowIndex, destColIndex);
			}
			grid.getRowFormatter().setStyleName(destRowIndex, "normal");
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

}
