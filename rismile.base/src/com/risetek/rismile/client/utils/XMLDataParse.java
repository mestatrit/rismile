package com.risetek.rismile.client.utils;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class XMLDataParse {

	public static String getElementText(Element item, String TagName) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(TagName);
		if (itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {
			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}

	public static String getElementAttribute(Element item, String TagName, String Attribute) {
		String result = null;
		NodeList itemList = item.getElementsByTagName(TagName);
		if (itemList.getLength() > 0 ) {
//			result = ((Element)itemList.item(0).getFirstChild()).getAttribute(Attribute);
			result = ((Element)itemList.item(0)).getAttribute(Attribute);
		}
		return result;
	}

	public static String getElementText(String text, String value) {
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();
		return getElementText(customerElement, value);
	}
}
