package com.risetek.keke.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.keke.client.nodes.ILoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements
		ILoginService {

	private static final long serialVersionUID = 1212436686440421909L;
	String[][] login_sucessed = {
			{"1", "NamedNode", "Login Sucessed", ""},
			{"1", "InjectToken", "Login Sucessed", ""},
			{"0", "Promotion", "登录成功", "p4"},
	};

	String[][] login_faild_invalid_username = {
			{"1", "NamedNode", "Login Failed invalid username", ""},
			{"2", "Promotion", "用户不存在", "p2"},
			{"0", "Promotion", "重新登录", "p3"},
			{"1", "Promotion", "取消登录", "p4"},
			{"1", "Exit"},
	};
	
	String[][] login_faild_invalid_password = {
			{"1", "NamedNode", "Login Failed invalid password", ""},
			{"0", "Promotion", "口令错误", "p3"},
	};
	
	@Override
	public String[][] loginServer(String username, String password)
			throws IllegalArgumentException {
		if( "".equals(username) )
			return login_faild_invalid_username;
		
		if( "".equals(password) )
			return login_faild_invalid_password;

		return login_sucessed;
	}

}
