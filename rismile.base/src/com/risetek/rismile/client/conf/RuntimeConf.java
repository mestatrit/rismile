package com.risetek.rismile.client.conf;

import java.util.HashMap;

public class RuntimeConf {
	private static HashMap<String, String> runtimeConf = new HashMap<String, String>();
	
	public static void AddConfig(String key, String val) {
		runtimeConf.put(key, val);
	}
	
	public static String getConfig(String key) {
		return runtimeConf.get(key);
	}
}
