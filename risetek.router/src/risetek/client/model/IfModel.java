package risetek.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class IfModel {

	private String name;
	private Element ifElement;
	
	public String getName(){
		return name;
	}
	public Element getIfElement(){
		return ifElement;
	}

	public List<mpdConfig> InterfaceList = new ArrayList<mpdConfig>();
	
	
	public class mpdConfig
	{
		public class lcpDataModel {
			public	String pppusername;
			public	String ppppassword;
			public	int	accept_pap;
			public	int accept_chap;
			public	int accept_eap;
			public	int accept_mschap;
			public	String keepalive;

			public void parseXML(Element element)
			{
				pppusername = getElementText(element, "username");
				ppppassword = getElementText(element, "password");
				keepalive = getElementText(element, "keepalive");

				NodeList authList = element.getElementsByTagName("authentication");
				for(int i = 0; i < authList.getLength(); i++){
					String value = ((Element)authList.item(i)).getFirstChild().getNodeValue();
					if(value.equals("Acceptable")){
						String type = ((Element)authList.item(i)).getAttribute("type");
						if( type.equals("chapmd5"))
						{
							accept_chap = 1;
						}
						else if(type.equals("pap"))
						{
							accept_pap = 1;
						}
						else if(type.equals("eap"))
						{
							accept_eap = 1;
						}
						else if(type.equals("ms-chap"))
						{
							accept_mschap = 1;
						}
					}
				}
			}
		}
		
		
		public lcpDataModel lcpdata = new lcpDataModel();
		
		public void parseXML(Element element)
		{

			
			
		}
	}
	
	public void parseXML(String text)
	{
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();

		XMLParser.removeWhitespace(customerElement);

		//List<IfModel> ifModelList = new ArrayList<IfModel>();
		NodeList interfaces = customerElement.getElementsByTagName("interface");
		for(int i = 0; i < interfaces.getLength(); i++){
			Element element = (Element) interfaces.item(i);
			mpdConfig config = new mpdConfig();
			config.parseXML(element);
			InterfaceList.add(config);
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
