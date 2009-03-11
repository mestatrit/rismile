package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.risetek.rismile.client.utils.XMLDataParse;

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
		mtu = Integer.parseInt(XMLDataParse.getElementText(element, "mtu"));
		mru = Integer.parseInt(XMLDataParse.getElementText(element, "mru"));
		mrru = Integer.parseInt(XMLDataParse.getElementText(element, "mrru"));

	}

}
