package com.risetek.rismile.client.utils;

public class Validity {
	// 电子邮件格式
	// private static final String username_pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	// 11位数字
	// private static final String imsi_pattern = "^(\\d{15})$";
	// ip 地址格式
	//private static final String ipaddress_pattern = "^(\\d{1,3}\\.){3}(\\d{1,3})$";
	private static final String ipaddress_pattern = "^((0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})\\.)" +
			"{3}(0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})$";
	// RADIUS端口格式
	private static final String radius_port_pattern = "^(\\d{1,7})$";
	// RADIUS的sharekey格式
	// private static final String radius_sharekey_pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]$";
	// 路由的interface格式
	// private static final String interface_pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]$";
	
	public static String validUserName(String text){
		if ( null == text || "".equals(text)) return "用户名称不能为空！";
		return null;
		//if( text.matches(username_pattern) ) return null;
		//return "用户名称不符合格式要求！";
	}
	
	public static String validPassword(String text){
		if ( null == text || "".equals(text)) return "口令不能为空！";
		return null;
	}

	public static String validAdminName(String text){
		if ( null == text || "".equals(text)) return "名称不能为空！";
		return null;
	}
	
	public static String validIMSI(String text){
		if ( null == text || "".equals(text)) return "终端号码不能为空！";
		return null;
		//if( text.matches(imsi_pattern)) return null;
		//return "IMSI号码不符合要求！";
	}
	
	public static String validIpAddress(String text){
		if ( null == text || "".equals(text)) return "分配地址不能为空！";
		if( text.matches(ipaddress_pattern) ) return null;
		return "地址不符合要求！";
	}
	
	public static String validRadiusPort(String text){
		if ( null == text || "".equals(text)) return "端口不能为空！";
		if( text.matches(radius_port_pattern) ) return null;
		return "端口不符合要求！";
	}
	public static String validRadiusShareKey(String text){
		if ( null == text || "".equals(text)) return "共享密匙不能为空！";
		//if( text.matches(radius_sharekey_pattern) ) return null;
		//return "共享密匙不符合要求！";
		return null;
	}
	public static String validRouteInterface(String text){
		if ( null == text || "".equals(text)) return "接口不能为空！";
		return null;
	}
}
