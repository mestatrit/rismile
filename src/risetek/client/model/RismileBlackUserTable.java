package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.XMLDataParse;

public class RismileBlackUserTable extends RismileTable {

	public void parseXML(String text)
	{
		//  TODO: 如果没有数据或者数据错误，这个parse过程会抛出错误，没有处理?
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][3];
		int j = 0;
		for(;j<users.getLength();j++ ) {
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();	//XMLDataParse.getElementText( logElement, "rowid" );
			data[j][1] = XMLDataParse.getElementText( logElement, "IMSI" );
			data[j][2] = XMLDataParse.getElementText( logElement, "USER" );
			
		}
		setData(data);
	}
	
}






