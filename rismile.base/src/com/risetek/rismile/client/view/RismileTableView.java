package com.risetek.rismile.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextDecoration;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends ViewComposite {
	public Grid grid = getGrid();
	protected final VerticalPanel outer = new VerticalPanel();
	private final DockPanel toolPanel = new DockPanel();
	public final NavBar navbar = new NavBar();
    private final Label infoLabel = new Label("");
    private final Label statisticLabel = new Label("");	
    
    public abstract String[] parseRow(Node node);
    
    
	public RismileTableView() {
		Style style;
	    outer.setHeight(Entry.SinkHeight);
	    outer.setWidth("100%");
	    outer.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

	    setStyleName("rismiletable");
	    
	    //toolPanel.setBorderWidth(1);
	    toolPanel.setStyleName("toolbar");
	    
	    style = toolPanel.getElement().getStyle();
	    style.setBorderStyle(BorderStyle.SOLID);
	    style.setBorderColor(UIConfig.OuterLine);
	    toolPanel.setWidth("100%");
	    toolPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    outer.add(toolPanel);
	    outer.setCellHeight(toolPanel, UIConfig.TitleHeight);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    
	    // 总数提示
	    Grid total = new Grid(1,1);
	    //total.setBorderWidth(1);
	    total.setWidth("170px");
	    toolPanel.add(total, DockPanel.EAST);
	    statisticLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    total.setWidget(0, 0, statisticLabel);
	    toolPanel.setCellWidth(total, "170px");
	    
	    toolPanel.add(infoLabel, DockPanel.WEST);
		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
//	    toolPanel.setCellWidth(infoLabel, "100%");
	    toolPanel.setCellWidth(infoLabel, "50%");

	    infoLabel.getElement().getStyle().setColor(UIConfig.InformationLabel);
	    
	    style = infoLabel.getElement().getStyle(); 
	    style.setMarginLeft(20, Unit.PX);
	    style.setFontSize(16, Unit.PX);
	    style.setColor("crimson");
	    
	    grid.setStyleName("table");
    	/*
    		cellspacing:1px;
    		margin-bottom: 0px;
    		font-size: 10pt;
    		width: 100%;
    		height: 100%;
    		align: center;
    		white-space: nowrap;
    		table-layout: fixed;
    	*/

	    style = grid.getElement().getStyle();
	    style.setFontSize(10, Unit.PT);
	    outer.add(grid);
	    
	    addLeftSide(outer);
	}

	public abstract Grid getGrid();
	
	public void setInfo(String text){
		infoLabel.setText(text);
	}

    public void setStatisticText(int total){
    	statisticLabel.setText("总数："+Integer.toString(total));
    }

    public void renderLineCallback(RismileTable table, String[] srcRowData, int line) {};
    public void cleanLineCallback(int line) {};
    
    public void renderLine(RismileTable table, int index ) {
    	NodeList nodes = table.nodes;
    	int nodesLength = nodes.getLength();
		int destColCount = grid.getCellCount(0);
    	if( index >= nodesLength) {
			for (int destColIndex = 0; destColIndex < destColCount; ++destColIndex) {
				grid.clearCell(index+1, destColIndex);
			}
    		cleanLineCallback(index);
    	}
    	else
    	{
    		String[] srcRowData = parseRow(nodes.item(index));
			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				String cellText = srcRowData[srcColIndex];
				// ie 下的边框表现不正常。
				if( null == cellText || "".equalsIgnoreCase( cellText ))
					grid.clearCell(index+1, srcColIndex);
				else
					grid.setText(index+1, srcColIndex, cellText);
			}
			renderLineCallback(table, srcRowData, index);
    	}
    }

    public void renderStatistic(RismileTable table) {
		setStatisticText( table.getSum() );

		boolean goFirst, goPrev, goNext, goLast;
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
    
	public void formatGrid(Grid grid, int rowCount, String[] columns, int[] columnWidth, HorizontalAlignmentConstant[] columnHAlign)
	{
		grid.resize(rowCount + 1, columns.length);
		int destColCount = grid.getCellCount(0);
		CellFormatter formatter = grid.getCellFormatter();
		Style style;
		for (int i = 0 ; i < grid.getColumnCount(); i++) {
	        grid.setText(0, i, columns[i]);
            style = formatter.getElement(0, i).getStyle();
            style.setFontWeight(FontWeight.BOLD);
            style.setBorderWidth(0, Unit.PX);
            style.setFontSize(13, Unit.PT);
            formatter.setHorizontalAlignment(0, i, HasHorizontalAlignment.ALIGN_CENTER);
            style.setTextDecoration(TextDecoration.UNDERLINE);
            
            if( -1 != columnWidth[i] ) {
            	if( 0 == columnWidth[i] ) {
        			for (int rowIndex = 0; rowIndex < rowCount+1; ++rowIndex) {
                		grid.getCellFormatter().setVisible(rowIndex, i, false);
        			}
            	}
            	else
            		grid.getCellFormatter().setWidth(0, i, columnWidth[i]+"px");
            }
	    }
	    
	    // 初始化表格格式。
		int destRowIndex = 1; // 留出表格头这一行

		for (; destRowIndex < rowCount+1; ++destRowIndex) {
			for (int srcColIndex = 0; srcColIndex < destColCount; ++srcColIndex) {
				formatter.setAlignment(destRowIndex, srcColIndex, columnHAlign[srcColIndex], HasVerticalAlignment.ALIGN_MIDDLE);
			}
		}
	}

}
