package com.risetek.keke.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
import com.risetek.keke.server.process.Help;
import com.risetek.keke.server.process.Login;
import com.risetek.keke.server.process.News;
import com.risetek.keke.server.process.PeopleRSS;

public class RemoteServletImpl extends HttpServlet {

	private static final long serialVersionUID = 5310351748984867707L;

	private static final String[][] remoteService_missing = {
			{ "1", "NamedNode", "missing Service method", "" },
			{ "0", "Cancel", "没有该远程方法", "Error" }, };

	void process(HttpServletResponse resp, Vector<String> params) {
		PrintWriter out;
		try {
			out = resp.getWriter();
			String xml = Util.stickletToXML(remoteService_missing);
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setByteStream(req.getInputStream());
			Document doc = docBuilder.parse(is);
			NodeList nodelist = doc.getElementsByTagName("RemoteService");

			Node method = nodelist.item(0);
			if (method != null) {
				
				NodeList params = method.getChildNodes();
				Stack<String>  param =  new Stack<String>();
				for( int loop = 0; loop < params.getLength(); loop++ ) {
					if( params.item(loop).getFirstChild() == null )
						param.push("");
					else
						param.push(params.item(loop).getFirstChild().getNodeValue());
				}
				String value = null;
				if( param.size() > 0)
					value = param.pop();
					
				if ("epay/login".equals(value)) {
					Login.process(resp, param);
				}
				else if ("epay/news".equals(value)) {
					News.process(resp, param);
				}
				else if ("epay/people".equals(value)) {
					PeopleRSS.process(resp, param);
				}
				else if ("epay/help".equals(value)) {
					Help.process(resp, param);
				}
				else
					process(resp, param);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
