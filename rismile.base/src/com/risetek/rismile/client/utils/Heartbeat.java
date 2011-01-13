package com.risetek.rismile.client.utils;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.HeartbeatEvent;
import com.risetek.rismile.client.RismileContext.RuntimeEvent;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.http.RequestFactory;

public class Heartbeat implements RequestCallback {

	private static Timer hbTimer;
	private static final RequestFactory remoteRequest = new RequestFactory("forms/");

	public static int networkspeed = (UIConfig.NETWORKSPEED_UP + UIConfig.NETWORKSPEED_LOW) / 2;
	
	static Heartbeat response = new Heartbeat();

	public static synchronized void startHeartbeat() {

		if( null == hbTimer ) {
			hbTimer = new Timer() {
				@Override
				public void run() {
					remoteRequest.POST("hb", null, response);
				}
			};
		}
		hbTimer.cancel();
		hbTimer.run();
	}

	@Override
	public void onError(Request request, Throwable exception) {
		RismileContext.fireEvent(new HeartbeatEvent("设备未响应！"));
		networkspeed += UIConfig.NETWORKSPEED_DELTA;
		if( networkspeed > UIConfig.NETWORKSPEED_UP )
			networkspeed = UIConfig.NETWORKSPEED_UP;
		hbTimer.schedule(1000);
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		networkspeed -= UIConfig.NETWORKSPEED_DELTA;
		if( networkspeed < UIConfig.NETWORKSPEED_LOW )
			networkspeed = UIConfig.NETWORKSPEED_LOW;
		
		if (response.getStatusCode() == 200) {
			Document xmldoc = XMLParser.parse(response.getText());
			com.google.gwt.xml.client.Element customerElement = xmldoc
					.getDocumentElement();
			NodeList ok = customerElement.getElementsByTagName("OK");
			if (ok != null) {
				NodeList nopass = customerElement
						.getElementsByTagName("nopassword");
				if (nopass != null && nopass.item(0) != null) {
					String nopassword = nopass.item(0).getFirstChild()
							.getNodeValue();
					RismileContext.fireEvent(new HeartbeatEvent(nopassword));
				} else {
					RismileContext.fireEvent(new HeartbeatEvent("网络正常！"));
				}

			} else {
				RismileContext.fireEvent(new HeartbeatEvent("网络正常！"));
			}
			
			String runtime = XMLDataParse.getElementText(customerElement, "run_time");
			if( ! "".equalsIgnoreCase(runtime) )
				RismileContext.fireEvent(new RuntimeEvent(runtime));
			
			hbTimer.schedule(20000 - ((networkspeed - UIConfig.NETWORKSPEED_LOW) * 4));
		} else {
			RismileContext.fireEvent(new HeartbeatEvent("设备响应异常！"));
			hbTimer.schedule(1000);
		}
	}

}
