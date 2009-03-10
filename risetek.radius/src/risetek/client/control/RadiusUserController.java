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
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.control.SysLog;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.view.RismileTableView;

public class RadiusUserController extends RismileTableController {
	//private static String loadForm = "SqlUserInfoXML";
	private static String emptyForm = "clearuser";
	private static String modifyForm = "SqlUserInfoXML";
	RismileUserTable table = new RismileUserTable();

	public UserView view;

	public RadiusUserController() {
		view = new UserView(this);
	}

	public void load() {
		String query = "lpage=" + table.getLimit() + "&offset="
				+ table.getOffset();
		remoteRequest.get("SqlUserInfoXML", query, this);
	}

	public void empty() {
		changeTableData(emptyForm, null, this);
	}

	public void add(String name, String imsi, String password, String ip,
			RequestCallback callback) {
		String query = "function=newuser&username=" + name + "&imsicode="
				+ imsi + "&password=" + password + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, callback);
	}

	public void modifyName(String rowID, String name, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&username=" + name;
		changeTableData(modifyForm, query, callback);
	}

	public void modifyImsi(String rowID, String imsi, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&imsicode=" + imsi;
		changeTableData(modifyForm, query, callback);
	}

	public void modifyIp(String rowID, String ip, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		changeTableData(modifyForm, query, callback);
	}

	public void modifyPassword(String rowID, String password,
			RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&password=" + password;
		changeTableData(modifyForm, query, callback);
	}

	public void delRow(String rowID, RequestCallback callback) {
		String query = "function=deluser&id=" + rowID;
		changeTableData(modifyForm, query, callback);
	}

	public void onError(Request request, Throwable exception) {

	}

	public void onResponseReceived(Request request, Response response) {
		table.parseXML(response.getText());
		view.render(table);
	}

	private boolean checkRemoteExecute(Response response)
	{
		if( "ERROR".equals(response.getHeader("EXECUTE")) )
			return false;
		view.unmask();
		load();
		return true;
	}
	
	public class TableAction implements TableListener {

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			// focusID = grid.getText(row, 0);
			view.focusID = view.getRowId(row);
			view.focusValue = view.getGrid().getText(row, cell);
			// view.focusedRowID = Long.toString(row);
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if (Window.confirm("是否要删除该用户?\n" + "用户名:"
						+ view.getGrid().getText(row, 2) + "\n" + "IMSI:  "
						+ view.getGrid().getText(row, 1) + "\n" + "IP地址:"
						+ view.getGrid().getText(row, 4))) {
					delRow(view.focusID, RadiusUserController.this);
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
				password_control.dialog.confirm
						.addClickListener(password_control);
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

		// ----------------- 修改 IMSI 号码
		public class UserIMSIModifyControl implements ClickListener,
				RequestCallback {
			public UserImsiModifyDialog dialog = new UserImsiModifyDialog(view);

			public void onClick(Widget sender) {
				if( dialog.valid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyImsi(view.focusID, dialog.newValueBox.getText(), this);
					
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( checkRemoteExecute(response))
					dialog.hide();
				else
					dialog.setMessage_E();
			}
		}

		// ----------------- 修改 分配 IP 地址
		public class UserIpModifyControl implements ClickListener,
				RequestCallback {
			public UserIpModifyDialog dialog = new UserIpModifyDialog(view);

			public void onClick(Widget sender) {
				if( dialog.valid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyIp(view.focusID, dialog.newValueBox.getText(), this);
				}
					
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( checkRemoteExecute(response))
					dialog.hide();
				else
					dialog.setMessage_E();
			}
		}

		// ----------------- 修改用户名称
		public class UserNameModifyControl implements ClickListener,
				RequestCallback {
			public UserNameModifyDialog dialog = new UserNameModifyDialog(view);

			public void onClick(Widget sender) {
				if( dialog.valid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyName(view.focusID, dialog.newValueBox.getText(), this);
					
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( checkRemoteExecute(response))
					dialog.hide();
				else
					dialog.setMessage_E();
			}
		}

		// ----------------- 修改用户口令
		public class UserPasswordModifyControl implements ClickListener,
				RequestCallback {
			public UserPasswordModifyDialog dialog = new UserPasswordModifyDialog(
					view);

			public void onClick(Widget sender) {
				if( dialog.valid() )
				{
					modifyPassword(view.focusID, dialog.passwordbox.getText(), this);
					dialog.confirm.setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( checkRemoteExecute(response))
					dialog.hide();
				else
					dialog.setMessage_E();
			}
		}
	}

	// ----------------- 增加用户
	public class AddUserAction implements ClickListener {

		public void onClick(Widget sender) {
			AddUserControl control = new AddUserControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}

		public class AddUserControl implements ClickListener, RequestCallback {
			public UserAddDialog dialog = new UserAddDialog(view);

			public void onClick(Widget sender) {
				if (dialog.valid()) {
					add(dialog.usernamebox.getText(), dialog.IMSI.getText(),
							dialog.passwordbox.getText(), dialog.ipaddress
									.getText(), this);
					dialog.confirm.setEnabled(false);
				}

			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( checkRemoteExecute(response))
					dialog.hide();
				else
					dialog.setMessage_E();
			}
		}
	}

	// 清除所有用户
	public class EmptyAction implements ClickListener {

		public void onClick(Widget sender) {
			if (Window.confirm("是否要清除所有用户?")) {
				view.clearButton.setEnabled(false);
				empty();
			}
		}
	}

	public RismileTable getTable() {
		return table;
	}

	public RismileTableView getView() {
		return view;
	}

}
