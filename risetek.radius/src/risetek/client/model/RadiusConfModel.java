package risetek.client.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.XMLDataParse;

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
		
		secretKey = XMLDataParse.getElementText(customerElement, "secret");
		authPort = XMLDataParse.getElementText(customerElement, "auth");
		acctPort = XMLDataParse.getElementText(customerElement, "acct");
		version = XMLDataParse.getElementText(customerElement, "serial");
		maxUser = XMLDataParse.getElementText(customerElement, "maxuser");
	}
	
}
