package com.risetek.scada.client.view;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class mapsView extends Composite {

	private final VerticalPanel frame = new VerticalPanel();

	public mapsView() {
		MapWidget map = new MapWidget();
		map.setDraggable(true);
	    map.setScrollWheelZoomEnabled(true);
	    map.setInfoWindowEnabled(true);
	    map.setSize("800px", "640px");
	    frame.add(map);
	    initWidget(frame);
	}

	

}
