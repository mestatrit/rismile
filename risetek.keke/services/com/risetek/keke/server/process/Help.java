package com.risetek.keke.server.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import com.risetek.keke.client.sticklet.Util;

public class Help {

	final static String[][] news = {
			{ "3", "NamedNode", "epay.remote.help", "" },
			{ "0", "Stay", "基础知识", "20090218213217243" },
			{ "0", "Stay", "怎么操作", "20090218213218178" },
			{ "0", "Stay", "该问谁？", "20090218213215625" }, };

	public static void process(HttpServletResponse resp, Vector<String> params) {
		PrintWriter out;
		try {
			out = resp.getWriter();
			String xml = Util.stickletToXML(news);
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}