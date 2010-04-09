package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.log.client.model.RismileTable;

public class RismileUserStatusTable extends RismileTable {
	public String filter = "";
	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][7];
		for(int j = 0;j<users.getLength();j++ )
		{
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();
			data[j][1] = XMLDataParse.getElementText( logElement, "STATUS" );
			data[j][2] = XMLDataParse.getElementText( logElement, "IMSI" );
			data[j][3] = XMLDataParse.getElementText( logElement, "USER" );
			data[j][4] = XMLDataParse.getElementText( logElement, "PASSWORD" );
			data[j][5] = IPConvert.longString2IPString(XMLDataParse.getElementText( logElement, "ADDRESS" ));
			data[j][6] = XMLDataParse.getElementText( logElement, "NOTE" );
		}
		setData(data);
	}
}






