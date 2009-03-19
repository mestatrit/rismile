package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.risetek.rismile.client.utils.XMLDataParse;

public class DialerStat {
	public String BundStatus1;
	public String BundStatus2;
	public String LinkStat;
	public String LinkType = "未知";
	public String self_address = "未获取";
	public String peer_address = "未获取";
	
	public void parseXML(Element element) {
		BundStatus1 = XMLDataParse.getElementText(element, "Status1");
		BundStatus2 = XMLDataParse.getElementText(element, "Status2");
		LinkStat = XMLDataParse.getElementText(element, "BundLinks");
		
		String value = XMLDataParse.getElementAttribute(element, "BundLinks", "type");
		if( null != value)
			LinkType = value;
		
		value = XMLDataParse.getElementAttribute(element, "IPCP", "self");
		if( null != value)
			self_address = value;
		
		value  = XMLDataParse.getElementAttribute(element, "IPCP", "peer");
		if( null != value)
			peer_address = value;
	}
}
