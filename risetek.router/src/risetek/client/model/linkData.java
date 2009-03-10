package risetek.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class linkData {

	public int mtu;
	public int mru;
	public int mrru;

	public int mtu_max;
	public int mru_max;
	public int mrru_max;

	public int mtu_min;
	public int mru_min;
	public int mrru_min;
	
	public int mtu_default;
	public int mru_default;
	public int mrru_default;

	
	public void parseXML(Element element) {
		mtu = Integer.parseInt(getElementText(element, "mtu"));
		mru = Integer.parseInt(getElementText(element, "mru"));
		mrru = Integer.parseInt(getElementText(element, "mrru"));

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
