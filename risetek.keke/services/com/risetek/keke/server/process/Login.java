package com.risetek.keke.server.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.risetek.keke.client.sticklet.Sticklets;

public class Login {

	final static String[][] login_sucessed = {
			{ "1", "NamedNode", "Login Sucessed", "" },
			{ "1", "Param", "", "" },
			{ "1", "Param", "", "" },
			{ "3", "KVPair", "", "" },
			{ "0", "Promotion", "ePay欢迎您", "20090218213217243" },
			{ "0", "Promotion", "Risetek为您服务", "20090218213218178" },
			{ "0", "Promotion", "NetFront先锋", "20090218213215625" }, };
	
	final static String[][] login_faild_invalid_username = {
			{"1", "NamedNode", "Login Failed invalid username", ""},
			{"2", "Promotion", "用户不存在", "20090218213215625"},
			{"0", "Promotion", "重新登录", "20090218213218178"},
			{"1", "Promotion", "取消登录", "20090218213227180"},
			{"0", "Exit"},
	};
	
	final static String[][] login_faild_invalid_password = {
			{"1", "NamedNode", "Login Failed invalid password", ""},
			{"0", "Promotion", "口令错误", "Error"},
	};
	
	
	public static void process(HttpServletResponse resp) {
		PrintWriter out;
		try {
			out = resp.getWriter();
			login_sucessed[1][2] = "token";
			login_sucessed[2][2] = "12345678";
			String xml = Sticklets.stickletToXML(login_sucessed);
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}