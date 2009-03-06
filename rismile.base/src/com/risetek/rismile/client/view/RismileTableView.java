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
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.model.RismileTable;


public abstract class RismileTableView extends Composite 
				implements NavBarListener, IView{

	protected final Grid grid = getGrid();
    
	private final DockPanel outer = new DockPanel();
	private final DockPanel toolPanel = new DockPanel();
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();
	protected final NavBar navbar = new NavBar();
	//protected final SearchBar searchBar = new SearchBar();
    private final Label infoLabel = new Label("");
    
	private String[] idArray;
	protected int tmpStartRow = 0; 
	protected int startRow = 0;
	protected int rowCount = 0;
	private int TotalRecord = 0;
	public String focusID;
	public String focusValue;
	public String rowID;
	boolean enableRowId = true;
	// 数据表的顺序
	String[] columnStyles;
	protected String condition = null;

	public RismileTableView(String[] columns, String[] columnStyles, int rowCount) {

        if (columns.length == 0) {
	        throw new IllegalArgumentException(
	        "expecting a positive number of columns");
	    }

	    if (columnStyles != null && columns.length != columnStyles.length) {
	        throw new IllegalArgumentException("expecting as many styles as columns");
	    }
	    this.columnStyles = columnStyles;

	    this.rowCount = rowCount;
	    
	    outer.addStyleName("radius-page-container");
	    initWidget(outer);
	    
	    toolPanel.setStyleName("navbar");
	    outer.add(toolPanel, DockPanel.NORTH);
	    
	    toolPanel.add(navbar, DockPanel.EAST);
	    toolPanel.add(new HTML("&nbsp"), DockPanel.EAST);
		toolPanel.add(buttonsPanel, DockPanel.EAST);
	    toolPanel.setCellHorizontalAlignment(buttonsPanel, DockPanel.ALIGN_RIGHT);
	    toolPanel.add(infoLabel, DockPanel.WEST);
		toolPanel.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
		toolPanel.setCellHorizontalAlignment(infoLabel, HasAlignment.ALIGN_LEFT);
		toolPanel.setCellVerticalAlignment(infoLabel, HasAlignment.ALIGN_MIDDLE);
	    toolPanel.setCellWidth(infoLabel, "100%");
	    infoLabel.setStyleName("radius-info-Label");
	    
	    grid.setStyleName("table");
	    outer.add(grid, DockPanel.CENTER);
	    
	    //outer.add(searchBar, DockPanel.SOUTH);
	    //searchBar.searchButton.addClickListener(new SearchClickListener());
	    //searchBar.backButton.addClickListener(new SearchClickListener());
	    initTable(columns, columnStyles, rowCount);
	    setStyleName("radius");
	    
	    navbar.addNavBarListener(this);
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
	public void setStartRow(int startRow){
		this.startRow = startRow;
	}
	public void setRowCount(int rows) {
	    grid.resizeRows(rows);
	}

	private int getDataRowCount() {
	    return grid.getRowCount() - 1;
	}
	public String getRowId(int row){
		String id ="null";
		if(idArray.length > row){
			id = idArray[row];
		}
		return id;
	}

	private void initTable(String[] columns, String[] columnStyles, int rowCount) {
	    // Set up the header row. It's one greater than the number of visible rows.
	    //
	    grid.resize(rowCount + 1, columns.length);
	    for (int i = 0, n = columns.length; i < n; i++) {
	        grid.setText(0, i, columns[i]);
	        if (columnStyles != null) {
	            grid.getCellFormatter().setStyleName(0, i, columnStyles[i] + " header");
	        }
	    }
	}
	public void gotoFirst() {
		// TODO Auto-generated method stub

        startRow = 0;
        navbar.enabelNavbar(false, false, false, false);
        loadModel();
	}
	public void gotoNext() {
		// TODO Auto-generated method stub

		startRow += getDataRowCount();
		navbar.enabelNavbar(false, false, false, false);
		loadModel();
	}
	
	public void gotoPrev() {
		// TODO Auto-generated method stub
		
		startRow -= getDataRowCount();
		if (startRow < 0) {
			startRow = 0;
		}
		navbar.enabelNavbar(false, false, false, false);
		loadModel();
	}
	public void gotoLast() {
		// TODO Auto-generated method stub
		
		startRow = TotalRecord - getDataRowCount();
		navbar.enabelNavbar(false, false, false, false);
		loadModel();
	}
	public void enabledNavBar(int total){
		// Synchronize the nav buttons.
		
		boolean firstEnabled = startRow > 0;
		boolean preEnabled = startRow > 0;
		boolean nextEnabled = (startRow + getDataRowCount()) < total;
		boolean lastEnabled = (startRow + getDataRowCount()) < total;
		
		navbar.enabelNavbar(firstEnabled, preEnabled, nextEnabled, lastEnabled);

	}

	public int getHeight(){
		return outer.getOffsetHeight();
	}
	
	public class LoadTableAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			String data[][] = ((RismileTable)object).getData();
			int total = ((RismileTable)object).getSum();
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
						cellHTML = Long.toString(startRow + destRowIndex);
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
			enabledNavBar(total);
			// Update the status message.
			//
			//setStatusText((startRow + 1) + " - " + (startRow + srcRowCount));
			
			navbar.setStatisticText("总数:"+total);
			TotalRecord = total;
		}
		
	}
	
	public class ToolButtonAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/*
	class SearchClickListener implements ClickListener{

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if(sender == searchBar.searchButton){
				int index = searchBar.searchListBox.getSelectedIndex();
				String colName = searchBar.searchListBox.getValue(index);
				String colValue = searchBar.inputTextBox.getText().trim();
				if(colName == null || colName == "" || colValue == null || colValue == ""){
					return;
				}
				if(colName.equals("ADDRESS")){
					String check = Validity.validIpAddress(colValue);
					if (null == check) {
						condition = colName + "=" + IPConvert.long2IPString(colValue);
					} else {
						searchBar.inputTextBox.setFocus(true);
						Window.alert(check);
						return;
					}
				} else {
					condition = colName + "='" + colValue + "'";
				}
				tmpStartRow = startRow;
				startRow = 0;
				RismileTableView.this.loadModel();
				searchBar.backButton.setVisible(true);
			}else if(sender == searchBar.backButton){
				startRow = tmpStartRow;
				condition = null;
				RismileTableView.this.loadModel();
				searchBar.backButton.setVisible(false);
			}
		}
		
	}*/
}
