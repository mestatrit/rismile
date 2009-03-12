package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.utils.XMLDataParse;

public class lcpData {

	public String pppusername;
	public String ppppassword;
	public boolean accept_pap;
	public boolean accept_chap;
	public boolean accept_eap;
	public boolean accept_mschap;
	public String keepalive;

	public void parseXML(Element element) {
		pppusername = XMLDataParse.getElementText(element, "username");
		ppppassword = XMLDataParse.getElementText(element, "password");
		keepalive = XMLDataParse.getElementText(element, "keepalive");

		NodeList authList = element.getElementsByTagName("authentication");
		for (int i = 0; i < authList.getLength(); i++) {
			String value = ((Element) authList.item(i)).getFirstChild()
					.getNodeValue();
			if (value.equals("Acceptable")) {
				String type = ((Element) authList.item(i)).getAttribute("type");
				if (type.equals("chapmd5")) {
					accept_chap = true;
				} else if (type.equals("pap")) {
					accept_pap = true;
				} else if (type.equals("eap")) {
					accept_eap = true;
				} else if (type.equals("ms-chap")) {
					accept_mschap = true;
				}
			}
		}
	}

}
