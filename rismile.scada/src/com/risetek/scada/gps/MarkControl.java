package com.risetek.scada.gps;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.risetek.scada.client.view.MessageConsole;

public class MarkControl implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
	}

	public MarkControl(String gpsdatas)
	{
		new RequestBuilder(RequestBuilder.POST, "/scada/putGPS?datas="+gpsdatas);
	}
	
}
