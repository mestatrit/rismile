package com.risetek.rismile.log.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.XMLDataParse;

public class RismileLogTable extends RismileTable {
	
	public RismileLogTable(boolean dir) {
		super(dir);
	}

	// 对应的自动刷新按键。
	public boolean autoRefresh = true;
	public String message_filer  = "";
	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		nodes = entryElement.getElementsByTagName("rowid");
	}
	
}
