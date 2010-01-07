package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.DockPanel;
import com.risetek.scada.client.Entry;

public class cameraView extends Composite {

	private final DockPanel frame = new DockPanel();
	private static Timer hbTimer = null;

	Image photo = new Image();

	static long last = 0;
	
	public cameraView() {

		GWT.log("get picture", null);
		//frame.setBorderWidth(1);
		frame.add(photo, DockPanel.CENTER);
		frame.setHeight(Entry.SinkHeight);
		initWidget(frame);
		frame.setWidth("100%");

		photo.addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent event) {
				MessageConsole.setText("图片得到 ");

				int sc;
				if( System.currentTimeMillis() - last > 500)
					sc = 1;
				else
					sc = 500;

				last = System.currentTimeMillis();
				if( hbTimer != null)
					hbTimer.schedule(sc);
					// hbTimer.run();
			}});
		
		photo.addErrorHandler(new ErrorHandler(){

			public void onError(ErrorEvent event) {
				MessageConsole.setText("图片未得到 ");
				int sc;
				if( System.currentTimeMillis() - last > 500)
					sc = 500;
				else
					sc = 1000;

				last = System.currentTimeMillis();
				if( hbTimer != null)
					hbTimer.schedule(sc);
					// hbTimer.run();
			}});
	}

	public void show(){
		hbTimer = new Timer() {
			public void run() {
				String imgname = "/scada/camera?id="+System.currentTimeMillis();
				MessageConsole.setText("获取图片: " + imgname );
				GWT.log("获取图片: " + imgname ,null);
				photo.setUrl(imgname);
				//photo.prefetch(url);
			}
		};
		hbTimer.run();
	}

	public void hide(){
		if( hbTimer != null )
		{
			hbTimer.cancel();
			hbTimer = null;
			MessageConsole.setText("终止图片获取");
		}
	}
}
