package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILoginServiceAsync {
	void loginServer(String username, String password, AsyncCallback<String[][]> callback)
	throws IllegalArgumentException;
}
