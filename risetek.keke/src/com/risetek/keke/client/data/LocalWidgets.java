package com.risetek.keke.client.data;


public class LocalWidgets {
	
//	HashMap<String, String[][]> widgets =  new HashMap<String, String[][]>();
	
	String[][] login = {
			{"PromotionTicker", "登录ePay"},
			{"UsernameTicker", "输入用户名称"},
			{"PasswordTicker", "输入登录密码"},
			{"PromotionTicker", "登录ePay"},
			{"LoginTicker", "链接网络"}
	};
	
	
	private LocalWidgets() {
//		widgets.put("epay.local.login", login);
	}
	
	public static LocalWidgets INSTANCE = new LocalWidgets();
}
