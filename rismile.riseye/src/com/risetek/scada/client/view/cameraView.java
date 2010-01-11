package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.scada.client.Entry;
import com.risetek.scada.client.ImgPack;

public class cameraView extends Composite {
	private static cameraViewUiBinder uiBinder = GWT.create(cameraViewUiBinder.class);

	interface cameraViewUiBinder extends UiBinder<Widget, cameraView> {
	}

	@RemoteServiceRelativePath("photo")
	public interface PhotoService extends RemoteService {
		ImgPack getPhoto(String id);
	}	

	public interface PhotoServiceAsync {
		void getPhoto(String id, AsyncCallback<ImgPack> callback);
	}

	PhotoServiceAsync photoService = (PhotoServiceAsync)GWT.create(PhotoService.class);

	private static Timer hbTimer = null;

	
	@UiField
	Image photo;// = new Image();

	@UiField
	Label title;

	@UiField
	Label ident;

	@UiField
	Label seq;

	@UiField
	Label timestamp;

	@UiField
	Label picsize;
	
	static long last = 0;
	
	public cameraView() {
		Widget w = uiBinder.createAndBindUi(this);
		w.setHeight(Entry.SinkHeight);
		initWidget(w);
		GWT.log("get picture", null);
		//frame.setBorderWidth(1);

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

	AsyncCallback<ImgPack> callback = new AsyncCallback<ImgPack>() {
	    public void onSuccess(ImgPack img) {
	    	GWT.log("Get ImgPack id is:"+img.id+" seq is:"+img.seq+" stamp is:"+img.stamp +" length is:"+img.image.length, null);
//	    	title.setText("id is:"+img.id+"\r\n seq is:"+img.seq+" stamp is:"+img.stamp +" length is:"+img.image.length);
	    	ident.setText("识别号："+img.id);
	    	seq.setText("摄像头序列："+img.seq);
	    	picsize.setText("图片大小："+img.image.length);
	    	timestamp.setText("上传时间："+img.stamp);
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
				photoService.getPhoto(new Long(System.currentTimeMillis()).toString(), callback);
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
