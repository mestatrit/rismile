package risetek.client.view;

import risetek.client.control.RadiusUserStatusController;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class UserStatusDetailsView extends Composite {
	
	private final static int rowCount = 7;
    private final static int colCount = 2;

    private final Grid grid = getGrid(); 
    
    public RadiusUserStatusController control;
    
    public UserStatusDetailsView(RadiusUserStatusController control){
    	grid.resize(rowCount, colCount);
    	grid.setStyleName("table");
    	this.control = control;
    	initWidget(grid);
    }

	public void onLoad(){
		control.load();
	}
	
	public void render(String[] date){
		
	}
	
	public Grid getGrid() {
		if (grid != null){
			return grid;
		}
		return new MouseEventGrid();
	}

	class MouseEventGrid extends Grid {
		
		public MouseEventGrid(){
			super();
			setCellPadding(2);
			setCellSpacing(0);
			sinkEvents(Event.MOUSEEVENTS);
		}
		
		public void onBrowserEvent(Event event){
			super.onBrowserEvent(event);
		}
	}
}
