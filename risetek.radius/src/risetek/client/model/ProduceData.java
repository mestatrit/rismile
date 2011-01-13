package risetek.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.XMLDataParse;


public class ProduceData {
	
	public String version;
//	public String status;
	public String serial;
	
	public void parseXML(String text)
	{
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();
		
		serial = XMLDataParse.getElementText(customerElement, "serial");
		version = XMLDataParse.getElementText(customerElement, "version");
//		status = XMLDataParse.getElementText(customerElement, "status");
	}

}
