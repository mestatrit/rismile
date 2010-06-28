package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface ILoginService extends RemoteService {
	String loginServer(String username, String password) throws IllegalArgumentException;
}
