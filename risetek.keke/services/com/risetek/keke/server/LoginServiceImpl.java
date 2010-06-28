package com.risetek.keke.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.keke.client.nodes.ILoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements
		ILoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1212436686440421909L;

	@Override
	public String loginServer(String username, String password)
			throws IllegalArgumentException {
		return "token";
	}

}
