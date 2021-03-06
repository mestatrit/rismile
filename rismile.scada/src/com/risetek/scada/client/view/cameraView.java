package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.risetek.scada.client.Entry;

public class cameraView extends Composite {

	private final Grid table = new Grid(2,1);
	TextBox userLabel = new TextBox();
	TextBox serialLabel = new TextBox();
	private final VerticalPanel frame = new VerticalPanel();
	private final FormPanel formPanel = new FormPanel();
	private static Timer hbTimer;

	Image photo = new Image();

	static int loop = 0;
	
	
	public cameraView() {

		table.setBorderWidth(1);

		table.setWidth("100%");

		GWT.log("get picture", null);
		photo.setUrl("/scada/camera");
		table.setWidget(0, 0, photo);

		
		Button submit = new Button("上传图片");
		table.setWidget(1, 0, submit);
		submit.setWidth("7em");
		submit.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});

		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction("/scada/camera");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.addSubmitHandler(new SubmitHandler(){
			public void onSubmit(SubmitEvent event) {
			}
		}
		);

		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			public void onSubmitComplete(SubmitCompleteEvent event) {
			}
		});

		frame.add(table);
		frame.setWidth("100%");		
		frame.setHeight(Entry.SinkHeight);
		initWidget(frame);

		hbTimer = new Timer() {
			public void run() {
				loop++;
				String imgname = "/scada/camera?id="+System.currentTimeMillis();
				MessageConsole.setText("获取图片: " + imgname );
				
				GWT.log("获取图片: " + imgname ,null);
				//photo.setUrl("/image/p2.jpg");
				photo.setUrl(imgname);
				photo.addLoadHandler(new LoadHandler(){

					@Override
					public void onLoad(LoadEvent event) {
						MessageConsole.setText("图片得到 ");
					}});
//				Image.prefetch(imgname);
				hbTimer.schedule(1000);
			}
		};
		hbTimer.schedule(1000);
	
	
	}

	

}
