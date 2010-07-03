package com.risetek.keke.server.process;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.gwt.core.client.GWT;

public class Login {
	
	final static String[][] login_sucessed = {
			{"1", "NamedNode", "Login Sucessed", ""},
			{"1", "InjectToken", "Login Sucessed", ""},
			{"0", "Promotion", "ePay欢迎您", "20090218213217243"},
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
	
	
	public static String[][] process(Node node) {
		GWT.log("Node text:"+node.getTextContent());
		/*
		Node u = method.getAttributes().getNamedItem("username");
		//Node u = doc.getElementsByTagName("username").item(0);
		if( u == null || u.getNodeValue() == null )
			return login_faild_invalid_username;
		
//		u = doc.getElementsByTagName("password").item(0);
		u = method.getAttributes().getNamedItem("password");
		if( u == null || u.getNodeValue() == null )
			return login_faild_invalid_password;
		*/
		return login_sucessed;
	}
}
