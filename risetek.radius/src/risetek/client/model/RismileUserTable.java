package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;

public class RismileUserTable extends RismileTable {

	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][6];
		int j = 0;
		for(;j<users.getLength();j++ ) {
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();//getElementText( logElement, "rowid" );
			data[j][1] = getElementText( logElement, "IMSI" );
			data[j][2] = getElementText( logElement, "USER" );
			data[j][3] = "******";
			data[j][4] = IPConvert.longString2IPString(getElementText( logElement, "ADDRESS" ));
			data[j][5] = getElementText( logElement, "STATUS" ).equals("1") ? "在线":"";;
			
		}
		setData(data);
		
	}
	
}






