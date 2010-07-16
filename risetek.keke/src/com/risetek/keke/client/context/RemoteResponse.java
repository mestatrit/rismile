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
		ClientEventBus.INSTANCE.fireEvent(
				new ClientEventBus.ResponseEvent(Sticklets.INSTANCE.stickletSources.get("epay.local.services.failed")));
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		//D3Context.Log("Remote: "+response.getText());
		int status = response.getStatusCode();
		if( status == 200 ) {
			String[][] a = Util.xmlToSticklet(response.getText());
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ResponseEvent(a));
		}
		else
		{
			ClientEventBus.INSTANCE.fireEvent(
					new ClientEventBus.ResponseEvent(Sticklets.INSTANCE.stickletSources.get("epay.local.services.failed")));
		}
	}

}
