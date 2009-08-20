package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.risetek.scada.client.Entry;

public class cameraView extends Composite {

	TextBox userLabel = new TextBox();
	TextBox serialLabel = new TextBox();
	private final DockPanel frame = new DockPanel();
	private static Timer hbTimer;

	Image photo = new Image();

	public cameraView() {

		GWT.log("get picture", null);
		frame.add(photo, DockPanel.CENTER);
		frame.setHeight(Entry.SinkHeight);
		initWidget(frame);
		frame.setWidth("100%");

		//photo.setUrl("/scada/camera");
		//hbTimer.run();
	}

	public void onshow(){
		hbTimer = new Timer() {
			public void run() {
				String imgname = "/scada/camera?id="+System.currentTimeMillis();
				MessageConsole.setText("获取图片: " + imgname );
				GWT.log("获取图片: " + imgname ,null);
				photo.setUrl(imgname);
				//photo.prefetch(url);
				photo.addLoadHandler(new LoadHandler(){
					public void onLoad(LoadEvent event) {
						MessageConsole.setText("图片得到 ");
					}});
				hbTimer.schedule(1000);
			}
		};
		hbTimer.run();
	}

}
