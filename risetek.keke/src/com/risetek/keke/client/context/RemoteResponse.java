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
		D3Context.CallSticklet(s);
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		D3Context.Log("Remote: "+response.getText());
		String[][] a = Util.xmlToSticklet(response.getText());
		// TODO: 我们应该想办法把Sticklets或者其原文作为参数传递到RemoteRequest的下一个Stick上去。
		// 这有利于分解RemoteRequest的enter和action两个步骤，实现规范性。
		Sticklet s = Sticklets.loadSticklet(a);
		D3Context.CallSticklet(s);
	}

}
