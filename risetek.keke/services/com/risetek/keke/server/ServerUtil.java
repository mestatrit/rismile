package com.risetek.keke.server;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

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
}
