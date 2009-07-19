package com.risetek.scada.client.view;

import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class mapsView extends Composite {

	private final SimplePanel frame = new SimplePanel();
	private final MapWidget map = new MapWidget(); 
	public mapsView() {
		map.setDraggable(true);
	    map.setScrollWheelZoomEnabled(true);
	    map.setInfoWindowEnabled(true);
	    map.setZoomLevel(8);
	    //map.setCurrentMapType(MapType.getSatelliteMap());
	    LatLng center = LatLng.newInstance(32.4030,102.0142, true);
	    map.setCenter(center);
		//frame.setWidth("100%");
		//frame.setHeight(Entry.SinkHeight);
	    initWidget(frame);
	    frame.add(map);
	    map.setSize("800px", "640px");
	    map.checkResizeAndCenter();
	    //map.setSize("100%", "100%");
	    //map.setWidth("100%");
	    //map.setHeight("100%");
	    map.setUIToDefault();

	    map.addMapClickHandler(new MapClickHandler() {
	        public void onClick(MapClickEvent e) {
	          MapWidget sender = e.getSender();
	          Overlay overlay = e.getOverlay();
	          LatLng point = e.getLatLng();

	          if (overlay != null && overlay instanceof Marker) {
	            sender.removeOverlay(overlay);
	          } else {
	        	  MarkerOptions opt = MarkerOptions.newInstance();
	        	  opt.setDraggable(true);
	        	  Marker m = new Marker(point, opt);
	            sender.addOverlay(m);
	          }
	        }
	      });
	
	
	}

	public void onshow(){
		map.checkResize();
	}

}
