package com.risetek.rismile.client.utils;

public class IPConvert {
	private static long ip2i(String ipaddress){
		long retValue = 0;
		String[] token = ipaddress.split("\\.",4);
		for( int looper = 0; looper < token.length; looper++){
			retValue = retValue * 256 + Long.parseLong(token[looper]);
		}
		return retValue;
	}
	

	private static String i2ip(long ip){
		return Long.toString(((ip >> 24 ) & 0xff))+"." + Long.toString(((ip >> 16 ) & 0xff)) +"."
			+ Long.toString(((ip >> 8 ) & 0xff)) + "." + Long.toString(((ip ) & 0xff));
	}
	
	public static String long2IPString(String ipaddress){
		return Long.toString(ip2i(ipaddress));
	}

	// 讲数值表示的地址转换成字符串形式。
	public static String longString2IPString(String ipaddress){
		return i2ip(Long.parseLong(ipaddress));
	}
}
