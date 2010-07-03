package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IRemoteServiceAsync {
	void remoteService(String param, AsyncCallback<String[][]> callback)
	throws IllegalArgumentException;
}
