package risetek.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.XMLDataParse;

public class IfModel  {

	int unit;
	String DialerName;
	public String defaultlink;
	public DialerInterfaceData config;

	public IfModel(int unit)
	{
		this.unit = unit;
		DialerName = "Dialer"+unit;
	}
	
	public void parseXML(String text)
	{
		config = null;
		// 每次数据到来我们都拥有新的数据结构对应？
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();
		
		defaultlink = XMLDataParse.getElementText(customerElement, "defaultlink");
		NodeList interfaces = customerElement.getElementsByTagName("interface");
		
		for(int i = 0; i < interfaces.getLength(); i++){
			Element element = (Element) interfaces.item(i);
			if( DialerName.equals(element.getAttribute("name")))
			{
				config = new DialerInterfaceData();
				config.parseXML(element);
			}
		}
	}
}
