package com.risetek.keke.client.sticklet;

import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class Util {

	public static String stickletToXML(String[][] sticklet) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ePay>";
		for( int loop=0; loop < sticklet.length; loop++ ) {
			String stick = "<stick t=\"".concat(sticklet[loop][1]);
			stick = stick.concat("\" d=\"").concat(sticklet[loop][0]);
			stick = stick.concat("\" p=\"").concat(sticklet[loop][2]).concat("\">");
			if( sticklet[loop][3] != null )
				stick = stick.concat("<img>").concat(sticklet[loop][3]).concat("</img>");
			stick = stick.concat("</stick>");
			xml = xml.concat(stick);
		}
		
		xml = xml.concat("</ePay>");
		return xml;
	}
	
	private static String getStickAttribute(Node node, String attribute) {
		Node n = node.getAttributes().getNamedItem(attribute);
		if( n != null )
			return n.getNodeValue();
		return null;
	}
	
	public static String[][] xmlToSticklet(String xml) {
		Vector<String[]> sticklet = new Vector<String[]>();
		Document doc = XMLParser.parse(xml);
		NodeList list = doc.getElementsByTagName("stick");
		
		for(int loop=0; loop < list.getLength(); loop++) {
			String[] nodes = new String[4];
			Node node = list.item(loop);
			nodes[0] = getStickAttribute(node,"d");
			nodes[1] = getStickAttribute(node,"t");
			nodes[2] = getStickAttribute(node,"p");
			nodes[3] = node.getChildNodes().toString();
			sticklet.add(nodes);
		}
		
		String[][] a = new String[sticklet.size()][4];
		
		for( int loop = 0; loop < sticklet.size(); loop++) {
			String[] t = sticklet.elementAt(loop);
			a[loop][0] = t[0];
			a[loop][1] = t[1];
			a[loop][2] = t[2];
			a[loop][3] = t[3];
		}
		return a;
	}
	
	
}
