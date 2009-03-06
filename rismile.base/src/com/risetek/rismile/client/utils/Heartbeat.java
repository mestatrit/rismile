package com.risetek.rismile.client.utils;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class Heartbeat {
	private static RequestBuilder hb_Builder = new RequestBuilder(
			RequestBuilder.POST, "forms/hb");
	private static Timer hbTimer;
	private static int heartFreq = 20000;

	public static void startHeartbeat() {
		hbTimer = new Timer() {

			public void run() {
				try {
					hb_Builder.sendRequest("code=code", new RequestCallback() {

						public void onError(Request request, Throwable exception) {
							MessageConsole.setHbText("网络已断开！");
							heartFreq = 5000;
							hbTimer.schedule(heartFreq);
						}

						public void onResponseReceived(Request request,
								Response response) {
							if (response.getStatusCode() != 12029) {

								Document xmldoc = XMLParser.parse(response
										.getText());
								com.google.gwt.xml.client.Element customerElement = xmldoc
										.getDocumentElement();

								NodeList ok = customerElement
										.getElementsByTagName("OK");

								if (ok != null) {
									NodeList nopass = customerElement
											.getElementsByTagName("nopassword");
									if (nopass != null
											&& nopass.item(0) != null) {
										String nopassword = nopass.item(0)
												.getFirstChild().getNodeValue();
										MessageConsole.setHbText(nopassword);
									} else {
										MessageConsole.setHbText("网络正常！");
									}

								} else {
									MessageConsole.setHbText("网络正常！");
								}

								heartFreq = 20000;
							} else {
								MessageConsole.setHbText("网络已断开！");
								heartFreq = 5000;
							}
							hbTimer.schedule(heartFreq);
						}

					});
					MessageConsole.setHbText("连接服务器...");

				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		};
		hbTimer.schedule(5000);
	}
}
