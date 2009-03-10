package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class DialerInterfaceData {

	public lcpData lcpdata = new lcpData();
	public linkData linkdata = new linkData();
	public ifaceData ifacedata = new ifaceData();

	public void parseXML(Element element) {

		NodeList nodelist = element.getElementsByTagName("lcp");
		if( null != nodelist.item(0) ){
			Element lcpelement = (Element)nodelist.item(0);
			lcpdata.parseXML(lcpelement);
		}

		nodelist = element.getElementsByTagName("link");
		if( null != nodelist.item(0) ){
			Element lcpelement = (Element)nodelist.item(0);
			linkdata.parseXML(lcpelement);
		}

		nodelist = element.getElementsByTagName("iface");
		if( null != nodelist.item(0) ){
			Element lcpelement = (Element)nodelist.item(0);
			ifacedata.parseXML(lcpelement);
		}
	
	}

	protected String getElementText(Element item, String value) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(value);
		if (itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {

			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}

}
