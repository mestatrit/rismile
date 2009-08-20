package com.risetek.scada.client.view;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class mapsView extends Composite {

	private final SimplePanel frame = new SimplePanel();
	private final MapWidget map = new MapWidget(); 
	static public Marker m;

	public mapsView() {
		map.setDraggable(true);
	    map.setScrollWheelZoomEnabled(true);
	    map.setInfoWindowEnabled(true);
	    map.setZoomLevel(8);
	    //map.setCurrentMapType(MapType.getSatelliteMap());
		//frame.setWidth("100%");
		//frame.setHeight(Entry.SinkHeight);
	    initWidget(frame);
	    frame.add(map);
	    map.setSize("800px", "640px");
	    map.setUIToDefault();
	    LatLng center = LatLng.newInstance(30.679923,104.01053, true);
	    map.setCenter(center);
//	    map.checkResizeAndCenter();

	    map.addMapClickHandler(new MapClickHandler() {
	        public void onClick(MapClickEvent e) {
	          MapWidget sender = e.getSender();
	          Overlay overlay = e.getOverlay();
	          LatLng point = e.getLatLng();

	          if (overlay != null && overlay instanceof Marker) {
	            sender.removeOverlay(overlay);
	            m=null;
	          } else {
	        	  MarkerOptions opt = MarkerOptions.newInstance();
	        	  opt.setDraggable(true);
	        	  opt.setAutoPan(true);
	        	  opt.setBouncy(true);
	        	  if( m == null)
	        	  {
	        		  m = new Marker(point, opt);
	        		  m.addMarkerDragEndHandler( new RisetekMarkerDragEndHandler());
	        		  m.addMarkerClickHandler(new RisetekMarkerClickHandler());
		        	  sender.addOverlay(m);
	        	  }
	        	  else
	        	  {
	        		  m.setLatLng(point);
//	        		  m.getIcon().setDragCrossAnchor(Point.newInstance(0,0));
	        		  //m.showMapBlowup();
	        	  }
	          }
	        }
	      });
	
	    Control.getTracter();
	}

	class RisetekMarkerClickHandler implements MarkerClickHandler {

		@Override
		public void onClick(MarkerClickEvent event) {
			MessageConsole.setText("click!");
			//Marker marker = event.getSender();
		
		}
		
	}
	
	class RisetekMarkerDragEndHandler implements MarkerDragEndHandler {

		@Override
		public void onDragEnd(MarkerDragEndEvent event) {
			Marker marker = event.getSender();
			MessageConsole.setText(marker.getLatLng().toUrlValue());
		}

		
	}
	
	public void onshow(){
		//map.setSize("100%", "100%");
//		map.checkResize();
	    map.checkResizeAndCenter();
	}

}
