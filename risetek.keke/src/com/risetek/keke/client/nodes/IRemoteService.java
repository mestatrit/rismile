package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ePayRemoteService")
public interface IRemoteService extends RemoteService {
	String[][] remoteService(String param) throws IllegalArgumentException;
}
