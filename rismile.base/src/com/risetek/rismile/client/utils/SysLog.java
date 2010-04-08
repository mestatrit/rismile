/*
 * 该类用来输出调试信息，也许我们应该在浏览器端开辟一个调试窗口。
 * 当然我们也应该有办法关闭这些信息的输出。
 * 
 */
package com.risetek.rismile.client.utils;

import com.google.gwt.core.client.GWT;

public class SysLog {
	public static void log(String message)
	{
		GWT.log(message, null);
	}
}
