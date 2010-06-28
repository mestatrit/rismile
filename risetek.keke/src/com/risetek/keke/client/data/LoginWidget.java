package com.risetek.keke.client.data;


public class LoginWidget extends AWidget {
	String[][] a = {
			{"4", "NamedNode", "Root ePay", ""},
			{"1", "Promotion", "登录ePay", "p3"},
			{"0", "Promotion", "送时间", "p4"},
			{"0", "Promotion", "送生活", "p2"},
			{"0", "Promotion", "送安全", "p5"},
			{"1", "Username", "输入用户名称", "p2"},
			{"1", "Password", "输入登录密码", "p2"},
			{"1", "LoginTicker", "登录ePay", ""},
			{"0", "CreatorTicker", "链接网络", ""}
	};

	private LoginWidget() {
		rootNode = loadNodes(a);
	}
	
	public static LoginWidget INSTANCE = new LoginWidget();
	
}
