package com.risetek.keke.server;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.keke.client.nodes.IRemoteService;
import com.risetek.keke.server.process.Login;

public class RemoteServiceImpl extends RemoteServiceServlet implements
		IRemoteService {

	private static final long serialVersionUID = 1212436686440521909L;

	String[][] remoteService_missing = {
			{ "1", "NamedNode", "missing Service method", "" },
			{ "0", "Promotion", "没有该远程方法", "Error" }, };

	@Override
	public String[][] remoteService(String param)
			throws IllegalArgumentException {

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docBuilderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(param));
			Document doc = docBuilder.parse(is);
			NodeList nodelist = doc.getElementsByTagName("RemoteService");

			Node method = nodelist.item(0);
			if (method != null) {
				Node name = method.getAttributes().getNamedItem("name");
				if (name != null) {
					String value = name.getTextContent();
					if (value != null) {
						if ("Login".equals(value)) {
							return Login.process(method);
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
		return remoteService_missing;
	}

}
