package com.risetek.keke.server.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

import javax.servlet.http.HttpServletResponse;

import com.risetek.keke.client.sticklet.Util;

public class Login {

	final static String[][] login_sucessed = {
			{ "1", "Named", "Login Sucessed", "" },
			{ "1", "Param", "", "" },
			{ "1", "Param", "", "" },
			{ "3", "KVPair", "", "" },
			{ "0", "Promotion", "", "<img v=\"20090218213217243\" />" },
			{ "0", "Promotion", "Risetek为您服务", "<img v=\"20090218213218178\" />" },
			{ "0", "Promotion", "NetFront先锋", "<img v=\"20090218213215625\" />" }, };

	final static String[][] login_faild_invalid_username = {
		{"1", "Named", "Login Failed invalid username", ""},
		{"0", "Cancel", "用户不存在", "<img v=\"20090218213215625\" />"},
	};

	final static String[][] login_faild_invalid_password = {
			{"1", "Named", "Login Failed invalid password", ""},
			{"0", "Cancel", "口令错误", "<img v=\"Error\" />"},
	};
	
	
	public static int process(HttpServletResponse resp, Stack<String> params) {
		PrintWriter out = null;
		try {
			String xml;
			if( params.size() >= 2) {
				out = resp.getWriter();
				
				String guess_password = params.pop();
				String guess_username = params.pop();
				if( "".equals(guess_username))
					xml = Util.stickletToXML(login_faild_invalid_username);
				else if( "".equals(guess_password))
					xml = Util.stickletToXML(login_faild_invalid_password);
				else {
					login_sucessed[1][2] = "token";
					login_sucessed[2][2] = "12345678";
					login_sucessed[4][2] = "ePay欢迎："+guess_username;
					xml = Util.stickletToXML(login_sucessed);
				}
				
				out.println(xml);
				out.flush();
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return -1;
	}
}