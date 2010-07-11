package com.risetek.keke.server.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.risetek.keke.client.sticklet.Util;

public class PeopleRSS {

	private static String getRSS(String address) {

		try {
			URL url = new URL(address);
			InputStream reader = url.openStream();
			DocumentBuilderFactory docBuilderFactory = 
				DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setByteStream(reader);
			Document doc = docBuilder.parse(is);

			NodeList nodelist = doc.getElementsByTagName("item");
			int length = nodelist.getLength();
			//if (length > 5)	length = 5;

			String[][] sticklet = new String[length + 1][4];
			sticklet[0][0] = length + "";
			sticklet[0][1] = "Named";
			sticklet[0][2] = "epay.remote.peopleRSS";
			sticklet[0][3] = "";

			for (int loop = 0; loop < length; loop++) {
				Node nn = nodelist.item(loop);
				NodeList m = nn.getChildNodes();
				String title = m.item(2).getNextSibling().getFirstChild().getNodeValue();
				sticklet[loop + 1][0] = "0";
				sticklet[loop + 1][1] = "Stay";
				sticklet[loop + 1][2] = title;
				sticklet[loop + 1][3] = "";
			}

			reader.close();
			String xml = Util.stickletToXML(sticklet);
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

	public static void process(HttpServletResponse resp, Vector<String> params) {
		PrintWriter out;
		try {
			out = resp.getWriter();
			String xml = getRSS("http://www.people.com.cn/rss/politics.xml");
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}