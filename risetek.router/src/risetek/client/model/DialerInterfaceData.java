package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class DialerInterfaceData {

	public lcpData lcpdata = new lcpData();
	public linkData linkdata = new linkData();
	public ifaceData ifacedata = new ifaceData();
	public DialerStat statdata = new DialerStat();

	public void parseXML(Element element) {
		
		NodeList nodelist = element.getElementsByTagName("stat");
		if( null != nodelist.item(0) ){
			statdata.parseXML((Element)nodelist.item(0));
		}
		
		nodelist = element.getElementsByTagName("lcp");
		if( null != nodelist.item(0) ){
			lcpdata.parseXML((Element)nodelist.item(0));
		}

		nodelist = element.getElementsByTagName("link");
		if( null != nodelist.item(0) ){
			linkdata.parseXML((Element)nodelist.item(0));
		}

		nodelist = element.getElementsByTagName("iface");
		if( null != nodelist.item(0) ){
			ifacedata.parseXML((Element)nodelist.item(0));
		}
	
	}

}
