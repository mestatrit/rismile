package com.risetek.rismile.log.client.control;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;

public class RismileLogController extends RismileTableController{
	private static String loadForm = "SqlLogMessageXML";
	private static String emptyForm = "clearlog";
	
	public RismileLogController() {
		super("logTABLE");
		// TODO Auto-generated constructor stub
	}

	public void load(int limit, int offset, IAction action){
		
		String query = "lpage="+limit+"&offset="+offset;
		loadTableData(loadForm, query, action);
	}
	public void empty(IAction action){
		changeTableData(emptyForm, null, action);
	}
	
	public void onSuccessResponse(Response response, IAction action) {
		// TODO Auto-generated method stub
		RismileTable table = new RismileTable();
		Element entryElement = XMLParser.parse( response.getText() ).getDocumentElement();
		String sum = getElementText( entryElement, "TOTAL" );
		
		table.setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][3];
		int j = 0;
		for(;j<users.getLength();j++ ) {
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();//getElementText( logElement, "rowid" );
			data[j][1] = getElementText( logElement, "logTIME" );
			data[j][2] = getElementText( logElement, "message" );
			
		}
		table.setData(data);
		action.onSuccess(table);
	}
}
