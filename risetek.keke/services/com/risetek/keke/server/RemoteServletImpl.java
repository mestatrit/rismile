package com.risetek.keke.server;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.risetek.keke.server.process.PLogin;

public class RemoteServletImpl extends HttpServlet {

	private static final long serialVersionUID = 5310351748984867707L;
/*
	private static final String[][] remoteService_missing = {
			{ "1", "NamedNode", "missing Service method", "" },
			{ "0", "Promotion", "没有该远程方法", "Error" }, };
*/
	void process(HttpServletResponse resp) {
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<stick type='NamedNode' d='1'>missing Service method<PARAM></PARAM></stick>"
					+ "<stick type='Promotion' d='0'>没有该远程方法<PARAM img='Error'></PARAM></stick>"
					);
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
				Node name = method.getAttributes().getNamedItem("name");
				if (name != null) {
					String value = name.getTextContent();
					if (value != null) {
						if ("Login".equals(value)) {
							PLogin.process(resp);
							return;
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		process(resp);
	}
}
