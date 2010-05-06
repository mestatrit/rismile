package com.risetek.scada.client.view;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.risetek.scada.client.ImgPack;

public class mapsView extends Composite {
	@RemoteServiceRelativePath("ggps")
	public interface GPSService extends RemoteService {
		ArrayList<ImgPack> getGPS(String id);
	}

	public interface GPSServiceAsync {
		void getGPS(String id, AsyncCallback<ArrayList<ImgPack>> callback);
	}

	GPSServiceAsync GPSService = (GPSServiceAsync) GWT.create(GPSService.class);

	private final SimplePanel frame = new SimplePanel();
	private final MapWidget map = new MapWidget();
	static public Marker m;

	public mapsView() {
		map.setDraggable(true);
		map.setScrollWheelZoomEnabled(true);
		map.setInfoWindowEnabled(true);
		map.setZoomLevel(8);
		// map.setCurrentMapType(MapType.getSatelliteMap());
		// frame.setWidth("100%");
		// frame.setHeight(Entry.SinkHeight);
		initWidget(frame);
		frame.add(map);
		map.setSize("800px", "640px");
		map.setUIToDefault();
		LatLng center = LatLng.newInstance(30.679923, 104.01053, true);
		map.setCenter(center);
		// map.checkResizeAndCenter();

		map.addMapClickHandler(new MapClickHandler() {
			public void onClick(MapClickEvent e) {
				MapWidget sender = e.getSender();
				Overlay overlay = e.getOverlay();
				LatLng point = e.getLatLng();

				if (overlay != null && overlay instanceof Marker) {
					sender.removeOverlay(overlay);
					m = null;
				} else {
					MarkerOptions opt = MarkerOptions.newInstance();
					opt.setDraggable(true);
					opt.setAutoPan(true);
					opt.setBouncy(true);
					if (m == null) {
						m = new Marker(point, opt);
						m.addMarkerDragEndHandler(new RisetekMarkerDragEndHandler());
						m.addMarkerClickHandler(new RisetekMarkerClickHandler());
						sender.addOverlay(m);
					} else {
						m.setLatLng(point);
						// m.getIcon().setDragCrossAnchor(Point.newInstance(0,0));
						// m.showMapBlowup();
					}
				}
			}
		});

	}

	class RisetekMarkerClickHandler implements MarkerClickHandler {

		@Override
		public void onClick(MarkerClickEvent event) {
			MessageConsole.setText("click!");
			// Marker marker = event.getSender();
		}

	}

	class RisetekMarkerDragEndHandler implements MarkerDragEndHandler {

		@Override
		public void onDragEnd(MarkerDragEndEvent event) {
			Marker marker = event.getSender();
			MessageConsole.setText(marker.getLatLng().toUrlValue());
		}

	}

	static long last = 0;

	AsyncCallback<ArrayList<ImgPack>> callback = new AsyncCallback<ArrayList<ImgPack>>() {
		public void onSuccess(ArrayList<ImgPack> imgpacks) {
    		Iterator<ImgPack> i = imgpacks.iterator();
    		ImgPack img = null;
    		while(i.hasNext()) {
    			img = i.next();
    		}
			
			if (img !=null && img.GPS != null) {
				MessageConsole.setText(img.GPS);

				GWTStringTokenizer tokenizer = new GWTStringTokenizer(img.GPS, ",", false);
				String token = tokenizer.nextToken();

				if (token.equals("$GPRMC")) {
					token = tokenizer.nextToken();
					token = tokenizer.nextToken();
					if (token.equalsIgnoreCase("A")) {
						String lattitude = tokenizer.nextToken();
						token = tokenizer.nextToken();
						String longitude = tokenizer.nextToken();

						double dlattitude = (Double.parseDouble(lattitude)+25) / 100;
						double dlongitude = (Double.parseDouble(longitude)+8) / 100;

						MessageConsole.setText("经度：" + dlattitude + " 维度："
								+ dlongitude);

						LatLng point = 
							LatLng.newInstance(dlattitude, dlongitude, true);
						MarkerOptions opt = MarkerOptions.newInstance();
						opt.setDraggable(true);
						opt.setAutoPan(true);
						opt.setBouncy(true);
						if (m == null) {
							m = new Marker(point, opt);
							map.addOverlay(m);
						} else {
							m.setLatLng(point);
						}
					}
				}
			} else
				MessageConsole.setText("NO GPS Provide");

			long ti = System.currentTimeMillis() - last;
			int sc;
			if (ti > 500)
				sc = 500;
			else
				sc = (int) ti + 1;

			last = System.currentTimeMillis();
			if (hbTimer != null)
				hbTimer.schedule(sc);

		}

		public void onFailure(Throwable caught) {
			GWT.log("Failed ImgPack", null);
			MessageConsole.setText("GPS信息未得到 ");
			long ti = System.currentTimeMillis() - last;
			int sc;
			if (ti > 1000)
				sc = 1000;
			else
				sc = (int) ti + 500;
			last = System.currentTimeMillis();
			if (hbTimer != null)
				hbTimer.schedule(sc);
		}
	};

	private static Timer hbTimer = null;

	public void show() {
		// map.setSize("100%", "100%");
		// map.checkResize();
		map.checkResizeAndCenter();
		hbTimer = new Timer() {
			public void run() {
				MessageConsole.setText("提取GPS");
				GWT.log("提取GPS", null);
				GPSService.getGPS("", callback);
			}
		};
		hbTimer.run();
	}

	public void hide() {
		if (hbTimer != null) {
			hbTimer.cancel();
			hbTimer = null;
			MessageConsole.setText("终止GPS提取");
		}
	}

}
