package risetek.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.XMLDataParse;

public class RismileUserFlowTable extends RismileTable {
	public boolean LEVEL = false;

	public RismileUserFlowTable(boolean dir) {
		super(dir);
	}

	public String filter = "";
	@Override
	public void parseXML(String text)
	{
		Element entryElement = XMLParser.parse( text ).getDocumentElement();
		String sum = XMLDataParse.getElementText( entryElement, "TOTAL" );
		LEVEL = XMLDataParse.getElementNumber( entryElement, "ASSERT" ) > 0;
		
		setSum(Integer.parseInt(sum));
		nodes = entryElement.getElementsByTagName("rowid");
	}
}






