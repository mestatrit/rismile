package com.risetek.keke.server.process;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Node;

import com.google.gwt.core.client.GWT;

public class PLogin {
	
	final static String[][] login_sucessed = {
			{"1", "NamedNode", "Login Sucessed", ""},
			{"3", "InjectToken", "Login Sucessed", ""},
			{"0", "Promotion", "ePay欢迎您", "20090218213217243"},
			{"0", "Promotion", "Risetek为您服务", "20090218213218178"},
			{"0", "Promotion", "NetFront先锋", "20090218213215625"},
	};

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
			/*
			out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<stick type='NamedNode' d='1'>missing Service method<PARAM></PARAM></stick>"
					+ "<stick type='Promotion' d='0'>没有该远程方法<PARAM img='Error'></PARAM></stick>"
					);
					*/

			out.println(
					"<stick type='NamedNode' d='1'>missing Service method<PARAM></PARAM></stick>"
					+ "<stick type='Promotion' d='0'>没有该远程方法<PARAM img='Error'></PARAM></stick>"
					);
			
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}