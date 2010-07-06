package com.risetek.keke.server.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.sticklet.Sticklets;

public class News {

	final static String[][] news = {
			{ "3", "NamedNode", "epay.remote.news", "" },
			{ "0", "Stay", "ePay欢迎您", "20090218213217243" },
			{ "0", "Stay", "Risetek为您服务", "20090218213218178" },
			{ "0", "Stay", "NetFront先锋", "20090218213215625" }, };

	// http://www.google.com/reader/atom/feed/http://www.cnbeta.com/commentrss.php?n=10
	private static String getRSS(String address) {

		try {
			URL url = new URL(address);
			InputStream reader = url.openStream();
			DocumentBuilderFactory docBuilderFactory = 
				DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setEncoding("gbk");
			is.setByteStream(reader);
			Document doc = docBuilder.parse(is);
//			NodeList nodelist = doc.getElementsByTagName("entry");
			NodeList nodelist = doc.getElementsByTagName("item");
			int length = nodelist.getLength();
			if (length > 5)
				length = 5;

			String[][] sticklet = new String[length + 1][4];
			sticklet[0][0] = length + "";
			sticklet[0][1] = "NamedNode";
			sticklet[0][2] = "epay.remote.news";
			sticklet[0][3] = "";

			for (int loop = 0; loop < length; loop++) {
				Node nn = nodelist.item(loop);
				NodeList m = nn.getChildNodes();
				String title = m.item(1).getFirstChild().getNodeValue();
				sticklet[loop + 1][0] = "0";
				sticklet[loop + 1][1] = "Stay";
				sticklet[loop + 1][2] = title;
				sticklet[loop + 1][3] = "20090218213218178";
			}

			reader.close();
			String xml = Sticklets.stickletToXML(sticklet);
			return xml;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void process(HttpServletResponse resp) {
		PrintWriter out;
		try {
			out = resp.getWriter();
//			String xml = getRSS("http://googling.wang:ilovekerongbaby@www.google.com/reader/atom/feed/http://www.cnbeta.com/commentrss.php?n=10");
			String xml = getRSS("http://www.cnbeta.com/commentrss.php");
			//Sticklets.stickletToXML(news);
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}