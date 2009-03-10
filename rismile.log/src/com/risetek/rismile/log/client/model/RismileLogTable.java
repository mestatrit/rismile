package com.risetek.rismile.log.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;

public class RismileLogTable extends RismileTable {
	// 对应的自动刷新按键。
	public boolean autoRefresh = true;
	
	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][3];
		int j = 0;
		for(;j<users.getLength();j++ ) {
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();//getElementText( logElement, "rowid" );
			data[j][1] = getElementText( logElement, "logTIME" );
			data[j][2] = getElementText( logElement, "message" );
			
		}
		setData(data);
		
	}
	
}
