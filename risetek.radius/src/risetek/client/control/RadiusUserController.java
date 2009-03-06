package risetek.client.control;


import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;

public class RadiusUserController extends RismileTableController{
	private static String loadForm = "SqlUserInfoXML";
	private static String emptyForm = "clearuser";
	private static String modifyForm = "SqlUserInfoXML";
	
	public RadiusUserController() {
		super("userTABLE");
		// TODO Auto-generated constructor stub
	}
	public void load(int limit, int offset, IAction action){
		
		String query = "lpage="+limit+"&offset="+offset;
		loadTableData(loadForm, query, action);
	}
	public void empty(IAction action){
		changeTableData(emptyForm, null, action);
	}
	public void add(String name, String imsi, String password, String ip,
			IAction action){
		String query = "function=newuser&username="+name+"&imsicode="+imsi+
			"&password="+password+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, action);
	}
	public void modifyName(String rowID, String name, IAction action){
		String query = "function=moduser&id="+rowID+"&username="+name;
		changeTableData(modifyForm, query, action);
	}
	public void modifyImsi(String rowID, String imsi, IAction action){
		String query = "function=moduser&id="+rowID+"&imsicode="+imsi;
		changeTableData(modifyForm, query, action);
	}
	public void modifyIp(String rowID, String ip, IAction action){
		String query = "function=moduser&id="+rowID+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, action);
	}
	public void modifyPassword(String rowID, String password, IAction action){
		String query = "function=moduser&id="+rowID+"&password="+password;
		changeTableData(modifyForm, query, action);
	}
	public void delRow(String rowID, IAction action){
		String query = "function=deluser&id="+rowID;
		changeTableData(modifyForm, query, action);
	}
	public void onSuccessResponse(Response response, IAction action) {
		// TODO Auto-generated method stub
		RismileTable table = new RismileTable();
		Element entryElement = XMLParser.parse( response.getText() ).getDocumentElement();
		String sum = getElementText( entryElement, "TOTAL" );
		
		table.setSum(Integer.parseInt(sum));
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
		table.setData(data);
		action.onSuccess(table);
	}
	
}
