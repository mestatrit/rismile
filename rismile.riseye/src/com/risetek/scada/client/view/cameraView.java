package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.risetek.scada.client.Entry;
import com.risetek.scada.client.RCPImage;

public class cameraView extends Composite {

	@RemoteServiceRelativePath("photo")
	public interface PhotoService extends RemoteService {
		RCPImage getPhoto(String id);
	}	

	public interface PhotoServiceAsync {
		void getPhoto(String id, AsyncCallback<RCPImage> callback);
	}

	PhotoServiceAsync photoService = (PhotoServiceAsync)GWT.create(PhotoService.class);

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

				long ti = System.currentTimeMillis() - last;
				int sc;
				if( ti > 500 )
					sc = 500;
				else
					sc = (int)ti + 1;

				last = System.currentTimeMillis();
				if( hbTimer != null)
					hbTimer.schedule(sc);
			}});
		
		photo.addErrorHandler(new ErrorHandler(){

			public void onError(ErrorEvent event) {
				MessageConsole.setText("图片未得到 ");
				long ti = System.currentTimeMillis() - last;
				int sc;
				if( ti > 1000 )
					sc = 1000;
				else
					sc = (int)ti + 500;
				last = System.currentTimeMillis();
				if( hbTimer != null)
					hbTimer.schedule(sc);
			}});
		
	}

	AsyncCallback<RCPImage> callback = new AsyncCallback<RCPImage>() {
	    public void onSuccess(RCPImage img) {
	    	GWT.log("Get ImgPack id is:"+img.id+" seq is:"+img.seq+" stamp is:"+img.stamp +" length is:"+img.image.length, null);
	      // do some UI stuff to show success
	    }

	    public void onFailure(Throwable caught) {
	    	GWT.log("Failed ImgPack", null);
	    }
	  };	

	public void show(){
		hbTimer = new Timer() {
			public void run() {
				String imgname = "/scada/camera?id="+System.currentTimeMillis();
				MessageConsole.setText("获取图片: " + imgname );
				GWT.log("获取图片: " + imgname ,null);
				photo.setUrl(imgname);
				photoService.getPhoto("abc", callback);
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
