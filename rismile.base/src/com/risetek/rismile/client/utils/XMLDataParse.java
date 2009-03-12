package com.risetek.rismile.client.utils;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class XMLDataParse {
	public static String getElementText(Element item, String value) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(value);
		if (itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {
			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}

	public static String getElementText(String text, String value) {
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();
		return getElementText(customerElement, value);
	}
}
