package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.XMLDataParse;

public class RismileBlackUserTable extends RismileTable {

	public RismileBlackUserTable(boolean dir) {
		super(dir);
	}

	public void parseXML(String text)
	{
		//  TODO: 如果没有数据或者数据错误，这个parse过程会抛出错误，没有处理?
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		
		setSum(Integer.parseInt(sum));
		nodes = entryElement.getElementsByTagName("rowid");
	}
	
}






