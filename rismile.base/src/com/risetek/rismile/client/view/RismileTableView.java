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

	public RismileTableView(String[] columns, String[] columnStyles, int rowCount) {
		
        if (columns.length == 0) {
	        throw new IllegalArgumentException(
	        "expecting a positive number of columns");
	    }

	    if (columnStyles != null && columns.length != columnStyles.length) {
	        throw new IllegalArgumentException("expecting as many styles as columns");
	    }
	    this.columnStyles = columnStyles;

	    navbar = new NavBar();
	    
	    //this.rowCount = rowCount;
	    outer.setHeight(Entry.SinkHeight);
	    initWidget(outer);
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
	    
	    // 初始化网格的格式
	    grid.resize(rowCount + 1, columns.length);

	    for (int i = 0 ; i < columns.length; i++) {
	        grid.setText(0, i, columns[i]);
            if (columnStyles != null) {
	            grid.getCellFormatter().setStyleName(0, i, "header");
	        }
	    }
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
				// ie 下的边框表现不正常。
				if( "".equalsIgnoreCase( cellHTML ))
					grid.clearCell(destRowIndex, srcColIndex);
					
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
				formatter.addStyleName(destRowIndex, destColIndex, columnStyles[destColIndex]);
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
