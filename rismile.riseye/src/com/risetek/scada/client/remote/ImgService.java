package com.risetek.scada.client.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImgService {
	public void getImg(String s, AsyncCallback<String> callback);
}
