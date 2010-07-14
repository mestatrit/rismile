package com.risetek.keke.server;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServerUtil {
	public static String DocToString(Document doc) {
		System
				.setProperty("javax.xml.transform.TransformerFactory",
						"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
		StreamResult strresult = new StreamResult(new StringWriter());

		try {
			Transformer tansform = TransformerFactory.newInstance()
					.newTransformer();
			tansform.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			tansform.setOutputProperty(OutputKeys.INDENT, "yes");
			// xml, html, text
			tansform.setOutputProperty(OutputKeys.METHOD, "xml");
			tansform.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			tansform.transform(new DOMSource(doc.getDocumentElement()), strresult);
			return strresult.getWriter().toString();
		} catch (Exception e1) {
			System.err.println("xml.tostring(document): " + e1);
		}
		return null;
	}


	public static Element StringtoXmlElement(String text) {
		try {
			DocumentBuilderFactory docBuilderFactory = 
				DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(text));
			Document doc = docBuilder.parse(is);
			return doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
