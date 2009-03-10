package risetek.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class IfModel  {


	//public List<DialerInterfaceData> InterfaceList = new ArrayList<DialerInterfaceData>();
	
	
	public DialerInterfaceData config = new DialerInterfaceData();
	
	public void parseXML(String text)
	{
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();

		XMLParser.removeWhitespace(customerElement);

		NodeList interfaces = customerElement.getElementsByTagName("interface");
		
		for(int i = 0; i < interfaces.getLength(); i++){
			Element element = (Element) interfaces.item(i);
			config.parseXML(element);
		}
	}
	
}
