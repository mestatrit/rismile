package com.risetek.scada.client.view;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class mapsView extends Composite {

	private final SimplePanel frame = new SimplePanel();
	private final MapWidget map = new MapWidget(); 
	public mapsView() {
		map.setDraggable(true);
	    map.setScrollWheelZoomEnabled(true);
	    map.setInfoWindowEnabled(true);
		//frame.setWidth("100%");
		//frame.setHeight(Entry.SinkHeight);
	    map.setSize("800px", "640px");
	    frame.add(map);
	    initWidget(frame);
	}

	

}
