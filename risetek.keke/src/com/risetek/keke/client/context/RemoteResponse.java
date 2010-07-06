package com.risetek.keke.client.context;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;

public class RemoteResponse implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
		// TODO: 装载系统错误提示。
		PosContext.Log("Remote request:" + "error.");
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		PosContext.Log("Remote: "+response.getText());
		String[][] a = Sticklets.xmlToSticklet(response.getText());
		Sticklet s = Sticklets.loadSticklet(a);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(s));
	}

}
