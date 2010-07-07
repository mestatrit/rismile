package com.risetek.keke.client.context;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;
import com.risetek.keke.client.sticklet.Util;

public class RemoteResponse implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
		D3Context.Log("Remote request:" + "error.");
		Sticklet s = Sticklets.loadSticklet("epay.local.services.failed");
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(s));
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		D3Context.Log("Remote: "+response.getText());
		String[][] a = Util.xmlToSticklet(response.getText());
		Sticklet s = Sticklets.loadSticklet(a);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(s));
	}

}
