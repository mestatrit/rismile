package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class lcpData {

	public String pppusername;
	public String ppppassword;
	public int accept_pap;
	public int accept_chap;
	public int accept_eap;
	public int accept_mschap;
	public String keepalive;

	public void parseXML(Element element) {
		pppusername = getElementText(element, "username");
		ppppassword = getElementText(element, "password");
		keepalive = getElementText(element, "keepalive");

		NodeList authList = element.getElementsByTagName("authentication");
		for (int i = 0; i < authList.getLength(); i++) {
			String value = ((Element) authList.item(i)).getFirstChild()
					.getNodeValue();
			if (value.equals("Acceptable")) {
				String type = ((Element) authList.item(i)).getAttribute("type");
				if (type.equals("chapmd5")) {
					accept_chap = 1;
				} else if (type.equals("pap")) {
					accept_pap = 1;
				} else if (type.equals("eap")) {
					accept_eap = 1;
				} else if (type.equals("ms-chap")) {
					accept_mschap = 1;
				}
			}
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
