package risetek.client.control;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;

public class RadiusBlackController extends RismileTableController{
	private static String loadForm = "SqlBlackUserInfoXML";
	private static String emptyForm = "clearblack";
	private static String modifyForm = "SqlBlackUserInfoXML";
	public RadiusBlackController() {
		super("blackTABLE");
	}
public void load(int limit, int offset, IAction action){
		
		String query = "lpage="+limit+"&offset="+offset;
		loadTableData(loadForm, query, action);
	}
	public void empty(IAction action){
		changeTableData(emptyForm, null, action);
	}
	public void add(String rowID, String name, String imsi, String password, String ip,
			IAction action){
		String query = "function=newuser&id="+rowID+"&username="+name+"&imsicode="+imsi+
			"&password="+password+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, action);
	}
	public void delRow(String rowID, IAction action){
		String query = "function=deluser&id="+rowID;
		changeTableData(modifyForm, query, action);
	}
	public void onSuccessResponse(Response response, IAction action) {
		// TODO Auto-generated method stub
		RismileTable table = new RismileTable();
		//  TODO: 如果没有数据或者数据错误，这个parse过程会抛出错误，没有处理?
		Element entryElement = XMLParser.parse( response.getText() ).getDocumentElement();
		String sum = getElementText( entryElement, "TOTAL" );
		
		table.setSum(Integer.parseInt(sum));
		NodeList users = entryElement.getElementsByTagName("rowid");
		String data[][] = new String[users.getLength()][3];
		int j = 0;
		for(;j<users.getLength();j++ ) {
			Element logElement = (Element)users.item(j);
			data[j][0] = logElement.getFirstChild().getNodeValue();//getElementText( logElement, "rowid" );
			data[j][1] = getElementText( logElement, "IMSI" );
			data[j][2] = getElementText( logElement, "USER" );
			
		}
		table.setData(data);
		action.onSuccess(table);
	}

}
