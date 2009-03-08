package risetek.client.control;

import risetek.client.dialog.BlackUserDialog;
import risetek.client.model.RismileBlackUserTable;
import risetek.client.view.BlackUserView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.control.SysLog;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.Validity;

public class RadiusBlackController extends RismileTableController{
	private static String loadForm = "SqlBlackUserInfoXML";
	private static String emptyForm = "clearblack";
	private static String modifyForm = "SqlBlackUserInfoXML";

	RismileBlackUserTable	table 	= new RismileBlackUserTable();
	public BlackUserView	view;
	public RadiusBlackController() {
		view = new BlackUserView(this);
	}

	/*
	public void load(int limit, int offset)
	{
		String query = "lpage="+limit+"&offset="+offset;
		loadTableData(loadForm, query);
	}
	*/
	
	public void load()
	{
		String query = "lpage="+table.getLimit()+"&offset="+table.getOffset();
		loadTableData(loadForm, query);
	}
	
	
/*	
	public void load(int limit, int offset, IAction action)
	{
		
		String query = "lpage="+limit+"&offset="+offset;
		loadTableData(loadForm, query, action);
	}
*/	
	public void empty(){
		changeTableData(emptyForm, null, this);
	}
	public void add(String rowID, String name, String imsi, String password, String ip, RequestCallback callbak){
		String query = "function=newuser&id="+rowID+"&username="+name+"&imsicode="+imsi+
			"&password="+password+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, callbak);
	}
	public void delRow(String rowID){
		String query = "function=deluser&id="+rowID;
		changeTableData(modifyForm, query, this);
	}
	public void onSuccessResponse(Response response, IAction action) {
		table.parseXML(response.getText());
		action.onSuccess(table);
	}

	public void onError(Request request, Throwable exception) {
		SysLog.log("RadiusBlackController");
	}

	public void onResponseReceived(Request request, Response response) {
		table.parseXML(response.getText());
		view.render(table);
	}

	public class EmptyAction extends ViewAction implements ClickListener{

		public void onSuccess(Object object) {
			super.onSuccess();
			view.clearButton.setEnabled(true);
			view.loadModel();
		}

		public void onClick(Widget sender) {
			if(Window.confirm("是否要清除所有不明用户?")){ 
				view.clearButton.setEnabled(false);
				empty();
			}
		}
		
	}
	
	
	public class TableAction extends ViewAction implements TableListener{

		public void onSuccess(Object object) {
			super.onSuccess();
			view.loadModel();
		}

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			//focusID = grid.getText(row, 0);
			view.focusID = view.getRowId(row);
			view.focusValue = view.getGrid().getText(row, cell);
			//view.focusedRowID = Long.toString(row);
			view.currentRow = row;
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if(Window.confirm("是否要删除?\n" +
						"用户名:"+view.getGrid().getText(row, 2)+"\n"+
						"IMSI: "+view.getGrid().getText(row, 1))){
					
					delRow(view.focusID);
				}
				break;
			case 1:
			case 2:
				// 导入用户为合法用户。
				view.mask();
				BlackUserControl control = new BlackUserControl();
				control.dialog.confirm.addClickListener(control);
				control.dialog.show();
				
				//BlackUserDialog dialog = new BlackUserDialog(view, view.getGrid().getText(row, 1), view.getGrid().getText(row, 2));

				break;
			default:
				break;
			}

		}
	}
	
	
	
	//----------------- 认可用户信息
	public class BlackUserControl implements ClickListener, RequestCallback {
		public BlackUserDialog dialog = 
			new BlackUserDialog(view , view.getGrid().getText(view.currentRow, 1), view.getGrid().getText(view.currentRow, 2));
		public void onClick(Widget sender) {
			String text; 
			String check; 
			
			text = dialog.imsiBox.getText();
			check = Validity.validIMSI(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			String imsicode = text;
			
			text = dialog.usernameBox.getText();
			String username = text;
			
			text = dialog.passwordBox.getText();
			check = Validity.validPassword(text);
			if (null != check){
				dialog.passwordBox.setFocus(true);
				Window.alert(check);
				return;
			}
			String password = text;
			
			text = dialog.ipaddressBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.ipaddressBox.setFocus(true);
				Window.alert(check);
				return;
			}
			String ipaddress = text;
			add(view.focusID, username, imsicode, password, ipaddress, this);
			dialog.confirm.setEnabled(false);
			
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			load();
		}
	}
	
		
}
