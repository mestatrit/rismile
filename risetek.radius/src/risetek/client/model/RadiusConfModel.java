package risetek.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class RadiusConfModel {

	private String authPort;
	private String acctPort;
	private String secretKey;
	private String version;
	private String maxUser;

	public String getAuthPort(){
		return authPort;
	}
	public String getAcctPort(){
		return acctPort;
	}
	public String getSecretKey(){
		return secretKey;
	}
	public String getVersion(){
		return version;
	}
	public String getMaxUser(){
		return maxUser;
	}
	
	public void parseXML( String text )
	{
		Document dom = XMLParser.parse(text);
		Element customerElement = dom.getDocumentElement();
		XMLParser.removeWhitespace(customerElement);
		
		secretKey = customerElement.getElementsByTagName("secret").item(0).getFirstChild().getNodeValue();
		authPort   = customerElement.getElementsByTagName("auth").item(0).getFirstChild().getNodeValue();
		acctPort   = customerElement.getElementsByTagName("acct").item(0).getFirstChild().getNodeValue();
		version = customerElement.getElementsByTagName("serial").item(0).getFirstChild().getNodeValue();
		maxUser = customerElement.getElementsByTagName("maxuser").item(0).getFirstChild().getNodeValue();
		
	}
	
}
