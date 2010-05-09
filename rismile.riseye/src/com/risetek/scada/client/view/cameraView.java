package com.risetek.scada.client.view;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.scada.client.Base64Encoder;
import com.risetek.scada.client.Entry;
import com.risetek.scada.client.ImgPack;

public class cameraView extends Composite {
	private static cameraViewUiBinder uiBinder = GWT
			.create(cameraViewUiBinder.class);

	interface cameraViewUiBinder extends UiBinder<Widget, cameraView> {
	}

	@RemoteServiceRelativePath("photo")
	public interface PhotoService extends RemoteService {
		ArrayList<ImgPack> getPhoto(String ident, long cookie);
	}

	public interface PhotoServiceAsync {
		void getPhoto(String ident, long cookie,
				AsyncCallback<ArrayList<ImgPack>> callback);
	}

	PhotoServiceAsync photoService = (PhotoServiceAsync) GWT
			.create(PhotoService.class);

	private static Timer PhotoTimer = null;

	@UiField
	Label title;

	@UiField
	Label ident;

	@UiField
	Label seq;

	@UiField
	Label timestamp;

	@UiField
	Image eyes;

	@UiField
	Label picsize;

	@UiField
	Label gps;
	@UiField
	Label gps2;
	@UiField
	Label currentident;

	@UiField
	Grid identlist;

	static long last = 0;
	long	currentID = 0;
	boolean	ident_valid = false;

	public cameraView() {
		Widget w = uiBinder.createAndBindUi(this);
		w.setHeight(Entry.SinkHeight);
		initWidget(w);

		identlist.resize(10, 3);
		identlist.setBorderWidth(1);

		identlist.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Cell c = identlist.getCellForEvent(event);
				int index = c.getRowIndex();
				cIdent = identlist.getText(index, 0) + ":"
						+ identlist.getText(index, 1);
				MessageConsole.setText("CLICKED:" + cIdent);
				currentident.setText("您点击了:" + cIdent);
			}

		});
	}

	AsyncCallback<ArrayList<ImgPack>> callback = new AsyncCallback<ArrayList<ImgPack>>() {
		public void onSuccess(ArrayList<ImgPack> l) {
			MessageConsole.setText("数据得到 ");

			int loop = 0;
			Iterator<ImgPack> i = l.iterator();
			while (i.hasNext()) {
				ImgPack img = i.next();
				if (img.image != null) {
					GWT.log("Local ID: " + currentID + "  Remote: "
								+ img.Cookie, null);
					GWT.log("Get ImgPack id is:" + img.id + " seq is:"
							+ img.seq + " stamp is:" + img.stamp
							+ " length is:" + img.image.length, null);
					ident.setText("识别号：" + img.id);
					seq.setText("摄像头序列：" + img.seq);
					picsize.setText("图片大小：" + img.image.length);
					timestamp.setText("上传时间：" + img.stamp);

					eyes.setUrl("data:image/jpeg;base64,"
							+ Base64Encoder.toBase64String(img.image));

					currentID = img.Cookie;
					
					MessageConsole.setText("图片得到 ");
					if (img.GPS != null) {
						GWTStringTokenizer tokenizer = new GWTStringTokenizer(
								img.GPS, ",", false);
						String token = tokenizer.nextToken();
						if (token.equals("$GPRMC")) {
							token = tokenizer.nextToken();
							token = tokenizer.nextToken();
							if (token.equalsIgnoreCase("A")) {
								String lattitude = tokenizer.nextToken();
								token = tokenizer.nextToken();
								String longitude = tokenizer.nextToken();

								double dlattitude = (Double
										.parseDouble(lattitude) + 25) / 100;
								double dlongitude = (Double
										.parseDouble(longitude) + 8) / 100;
								gps.setText("经度：" + dlattitude);
								gps2.setText("维度：" + dlongitude);
							}
						}
					}
					else
					{
						gps.setText("");
						gps2.setText("");
					}
				}

				ident_valid = true;

				if (loop < 8) {
					identlist.setText(loop, 0, img.id);
					identlist.setText(loop, 1, img.seq);
					long delta = System.currentTimeMillis() - img.Cookie;
					identlist.setText(loop, 2, new Long((delta + 50) / 100)
							.toString()
							+ "ms");
				}
				loop++;
			}
			for (; loop < 8; loop++) {
				identlist.clearCell(loop, 0);
				identlist.clearCell(loop, 1);
				identlist.clearCell(loop, 2);
			}

			long ti = System.currentTimeMillis() - last;
			int sc;
			if (ti > 500)
				sc = 500;
			else
				sc = (int) ti + 1;

			last = System.currentTimeMillis();
			if (PhotoTimer != null)
				PhotoTimer.schedule(sc);

		}

		public void onFailure(Throwable caught) {
			GWT.log("Failed ImgPack", null);
			MessageConsole.setText("图片未得到 ");
			long ti = System.currentTimeMillis() - last;
			int sc;
			if (ti > 1000)
				sc = 1000;
			else
				sc = (int) ti + 500;
			last = System.currentTimeMillis();
			if (PhotoTimer != null)
				PhotoTimer.schedule(sc);
		}
	};

	String cIdent = "";

	public void show() {
		PhotoTimer = new Timer() {
			public void run() {
				if("".equalsIgnoreCase(cIdent) && ident_valid ) {
					// 获取缺省的标号
					cIdent = identlist.getText(0, 0) + ":" + identlist.getText(0, 1);
					currentident.setText("缺省选择了：[" + cIdent+"]");
				}
				
				MessageConsole.setText("提取图片 " + currentID);
				GWT.log("提取图片 " + currentID, null);
				photoService.getPhoto(cIdent, currentID, callback);
			}
		};
		PhotoTimer.run();
	}

	public void hide() {
		if (PhotoTimer != null) {
			PhotoTimer.cancel();
			PhotoTimer = null;
			MessageConsole.setText("终止图片获取");
		}
	}
}
