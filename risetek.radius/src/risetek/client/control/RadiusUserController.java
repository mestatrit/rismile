package risetek.client.control;


import risetek.client.dialog.UserAddDialog;
import risetek.client.dialog.UserImsiModifyDialog;
import risetek.client.dialog.UserIpModifyDialog;
import risetek.client.dialog.UserNameModifyDialog;
import risetek.client.dialog.UserPasswordModifyDialog;
import risetek.client.model.RismileUserTable;
import risetek.client.view.UserView;

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

public class RadiusUserController extends RismileTableController{
	private static String loadForm = "SqlUserInfoXML";
	private static String emptyForm = "clearuser";
	private static String modifyForm = "SqlUserInfoXML";
	RismileUserTable table = new RismileUserTable();
	
	public UserView view;
	
	public RadiusUserController() {
		view = new UserView(this);
	}

	public void load(){
		String query = "lpage="+ table.getLimit()+"&offset="+ table.getOffset();
		loadTableData(loadForm, query);
	}
	
	public void empty(){
		changeTableData(emptyForm, null, this);
	}
	public void add(String name, String imsi, String password, String ip,RequestCallback callback){
		String query = "function=newuser&username="+name+"&imsicode="+imsi+
			"&password="+password+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, callback);
	}
	public void modifyName(String rowID, String name, RequestCallback callback){
		String query = "function=moduser&id="+rowID+"&username="+name;
		changeTableData(modifyForm, query, callback);
	}
	public void modifyImsi(String rowID, String imsi, RequestCallback callback){
		String query = "function=moduser&id="+rowID+"&imsicode="+imsi;
		changeTableData(modifyForm, query, callback);
	}
	public void modifyIp(String rowID, String ip, RequestCallback callback){
		String query = "function=moduser&id="+rowID+"&ipaddress="+IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, callback);
	}
	public void modifyPassword(String rowID, String password, RequestCallback callback){
		String query = "function=moduser&id="+rowID+"&password="+password;
		changeTableData(modifyForm, query, callback);
	}
	public void delRow(String rowID, IAction action){
		String query = "function=deluser&id="+rowID;
		changeTableData(modifyForm, query, this);
	}
	public void onSuccessResponse(Response response, IAction action) {
		table.parseXML(response.getText());
		action.onSuccess(table);
	}

	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	public void onResponseReceived(Request request, Response response) {
		table.parseXML(response.getText());
		view.render(table);
	}
	
	
	public class TableAction extends ViewAction implements TableListener{

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			//focusID = grid.getText(row, 0);
			view.focusID = view.getRowId(row);
			view.focusValue = view.getGrid().getText(row, cell);
			//view.focusedRowID = Long.toString(row);
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if(Window.confirm("是否要删除该用户?\n" +
						"用户名:"+view.getGrid().getText(row, 2)+"\n"+
						"IMSI:  "+view.getGrid().getText(row, 1)+"\n"+
						"IP地址:"+view.getGrid().getText(row, 4))){
					delRow(view.focusID, this);
				}
				break;
			case 1:
				// 修改IMSI号码。
				view.mask();
				UserIMSIModifyControl ismi_control = new UserIMSIModifyControl();
				ismi_control.dialog.confirm.addClickListener(ismi_control);
				ismi_control.dialog.show();
				break;
			case 2:
				// 修改用户名称。
				view.mask();
				UserNameModifyControl name_control = new UserNameModifyControl();
				name_control.dialog.confirm.addClickListener(name_control);
				name_control.dialog.show();
				break;
			case 3:
				// 修改用户口令。
				view.mask();
				UserPasswordModifyControl password_control = new UserPasswordModifyControl();
				password_control.dialog.confirm.addClickListener(password_control);
				password_control.dialog.show();
				break;
			case 4:
				// 修改 分配IP 地址
				view.mask();
				UserIpModifyControl ip_control = new UserIpModifyControl();
				ip_control.dialog.confirm.addClickListener(ip_control);
				ip_control.dialog.show();
				break;
			default:
				break;
			}

		}

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			view.loadModel();
		}
		
	}
	
	//----------------- 修改 IMSI 号码
	public class UserIMSIModifyControl implements ClickListener, RequestCallback {
		public UserImsiModifyDialog dialog = new UserImsiModifyDialog(view);
		public void onClick(Widget sender) {
			String newImsi = dialog.newValueBox.getText();

			String check = Validity.validIMSI(newImsi);
			if (null == check) {
				SysLog.log(newImsi);
				modifyImsi(view.focusID, newImsi, this);
			}
			else
			{
				dialog.newValueBox.setFocus(true);
				Window.alert(check);
			}
			
		}

		public void onError(Request request, Throwable exception) {
			// TODO Auto-generated method stub
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			load();
		}
	}
	
	//----------------- 修改 分配 IP 地址
	public class UserIpModifyControl implements ClickListener, RequestCallback {
		public UserIpModifyDialog dialog = new UserIpModifyDialog(view);
		public void onClick(Widget sender) {
			String text = dialog.newValueBox.getText();
			String check = Validity.validIpAddress(text);
			if (null == check) {
				SysLog.log(text);
				modifyIp( view.focusID, text, this);
			}
			else
			{
				dialog.newValueBox.setFocus(true);
				Window.alert(check);
			}
			
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
	
	
	//----------------- 修改用户名称
	public class UserNameModifyControl implements ClickListener, RequestCallback {
		public UserNameModifyDialog dialog = new UserNameModifyDialog(view);
		public void onClick(Widget sender) {
			String text = dialog.newValueBox.getText();
			String check = text; 
			if (null != check) {
				SysLog.log(text);
				modifyName( view.focusID, text, this);
			}
			else
			{
				dialog.newValueBox.setFocus(true);
				Window.alert("用户名不能为空！");
			}
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
	
	
	//----------------- 修改用户口令
	public class UserPasswordModifyControl implements ClickListener, RequestCallback {
		public UserPasswordModifyDialog dialog = new UserPasswordModifyDialog(view);
		public void onClick(Widget sender) {
			String text = dialog.passwordbox.getText();
			String check = Validity.validPassword(text);
			if (null == check) {
				modifyPassword(view.focusID, text, this);
				dialog.confirm.setEnabled(false);
				
			} else {
				dialog.passwordbox.setFocus(true);
				Window.alert(check);
			}
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
	
	
	//----------------- 增加用户
	public class AddUserControl implements ClickListener, RequestCallback {
		public UserAddDialog dialog = new UserAddDialog(view);
		public void onClick(Widget sender) {
			String text; 
			String check; 
			text = dialog.IMSI.getText();
			check = Validity.validIMSI(text);
			if (null != check){
				dialog.IMSI.setFocus(true);
				Window.alert(check);
				return;
			}
			String imsicode = text;
			
			text = dialog.usernamebox.getText();
			check = text;//Validity.validUserName(text);
			if (null == check){
				dialog.usernamebox.setFocus(true);
				Window.alert("用户名不能为空！");
				return;
			}
			String username = text;
			
			text = dialog.passwordbox.getText();
			check = Validity.validPassword(text);
			if (null != check){
				dialog.passwordbox.setFocus(true);
				Window.alert(check);
				return;
			}
			String password = text;
			
			text = dialog.ipaddress.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.ipaddress.setFocus(true);
				Window.alert(check);
				return;
			}
			String ipaddress = text;
			//String ipaddress = IPConvert.long2IPString(text);
			//String values = "('"+ imsicode +"','"+username+"','"+password+"',"+ipaddress+",0)";
			//parent.userController.addRecord(null, values, new DialogAction(UserAddDialog.this, parent));
			add(username, imsicode, password, ipaddress, this);
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
	
	public class AddUserAction implements ClickListener{

		public void onClick(Widget sender) {
			UserAddDialog addDialog = new UserAddDialog(view);
			addDialog.show();
		}
		
	}
	
	// 清除所有用户
	public class EmptyAction implements ClickListener{

		public void onClick(Widget sender) {
			if(Window.confirm("是否要清除所有用户?")){ 
				view.clearButton.setEnabled(false);
				empty();
			}
		}
	}

	
}
