package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.log.client.model.RismileTable;

public class RismileUserTable extends RismileTable {
	public String filter = "";
	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][7];
		int j = 0;
		for(;j<users.getLength();j++ )
		{
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();
			data[j][1] = XMLDataParse.getElementText( logElement, "IMSI" );
			data[j][2] = XMLDataParse.getElementText( logElement, "USER" );
			data[j][3] = "****";
			data[j][4] = IPConvert.longString2IPString(XMLDataParse.getElementText( logElement, "ADDRESS" ));
			data[j][5] = XMLDataParse.getElementText( logElement, "NOTE" );
//			data[j][6] = XMLDataParse.getElementText( logElement, "STATUS" ).equals("1") ? "在线":"";
			data[j][6] = XMLDataParse.getElementText( logElement, "STATUS" );
		}
		setData(data);
	}
}






