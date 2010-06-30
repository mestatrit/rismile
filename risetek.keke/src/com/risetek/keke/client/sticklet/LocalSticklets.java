package com.risetek.keke.client.sticklet;


public class LocalSticklets {
	
//	HashMap<String, String[][]> widgets =  new HashMap<String, String[][]>();
	
	String[][] login = {
			{"PromotionTicker", "登录ePay"},
			{"UsernameTicker", "输入用户名称"},
			{"PasswordTicker", "输入登录密码"},
			{"PromotionTicker", "登录ePay"},
			{"LoginTicker", "链接网络"}
	};
	
	public final static String LoaginLabel = "Login Process";
	public final static String[][] a = {
			{"4", "NamedNode", LoaginLabel},
			{"1", "Promotion", "登录ePay", "p3"},
			{"0", "Promotion", "送时间", "p4"},
			{"0", "Promotion", "送生活", "p2"},
			{"0", "Promotion", "送安全", "p5"},
			{"1", "Username", "输入用户名称", "p2"},
			{"1", "Password", "输入登录密码", "p2"},
			{"0", "Login", "登录ePay", ""},
	};
	
	public final static String RootLabel = "Root ePay"; 
	public final static String[][] b = {
			{"1", "NamedNode", RootLabel},
			{"3", "SecurityCheck"},
			{"0", "Promotion", "服务创造价值", "p4"},
			{"0", "Promotion", "观念决定出路", "p2"},
			{"0", "Logout", "退出登录", "p5"},
	};
	
	
	private LocalSticklets() {
//		widgets.put("epay.local.login", login);
	}
	
	public static LocalSticklets INSTANCE = new LocalSticklets();
}
