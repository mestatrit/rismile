package com.risetek.rismile.client.http;

import com.google.gwt.http.client.Response;

public interface RequestListener {
	
	void onLoadingStart();
	void onResponse(Response response);
	void onError(String error);
	void onLoadingFinish();
	
}
