package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;

public class Control implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("没有GPS数据...");
		//hbTimer.run();
		hbTimer.schedule(3000);
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		if( response.getStatusCode() == 200 )
		{
			GWT.log(response.getText(),null);
			MessageConsole.setText("获取最新GPS数据..."+response.getText());
			hbTimer.schedule(1000);
		}
		else
		{
			MessageConsole.setText("服务端未正确响应："+ response.getStatusCode());
			hbTimer.schedule(1000);
		}
	}

	
	private static RequestBuilder hb_Builder;
	private static Timer hbTimer;
	static { 
		hb_Builder = new RequestBuilder(RequestBuilder.GET, "/scada/getGPS");
		// 设定 5 秒钟
		hb_Builder.setTimeoutMillis(5000);
		hbTimer = new Timer() {
			public void run() {
				try {
					hb_Builder.sendRequest("code=code", control);
					//MessageConsole.setText("获取最新GPS数据..."+(times++));
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		};
	}

	
	private static Control control = new Control();
	//private static int times = 0;
	public static void startTracert() {
		hbTimer.run();
	}
	
	public static void stopTracert() {
		hbTimer.cancel();
	}
}
