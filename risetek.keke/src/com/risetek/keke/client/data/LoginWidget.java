package com.risetek.keke.client.data;


public class LoginWidget extends AWidget {
	public final static String Label = "Login Process";
	String[][] a = {
			{"4", "NamedNode", Label, ""},
			{"1", "Promotion", "登录ePay", "p3"},
			{"0", "Promotion", "送时间", "p4"},
			{"0", "Promotion", "送生活", "p2"},
			{"0", "Promotion", "送安全", "p5"},
			{"1", "Username", "输入用户名称", "p2"},
			{"1", "Password", "输入登录密码", "p2"},
			{"0", "Login", "登录ePay", ""},
	};

	public LoginWidget() {
		rootNode = loadNodes(a);
	}
}
