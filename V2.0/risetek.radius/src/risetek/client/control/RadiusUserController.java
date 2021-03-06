package risetek.client.control;

import risetek.client.dialog.UserAddDialog;
import risetek.client.dialog.UserDelDialog;
import risetek.client.dialog.UserFilterDialog;
import risetek.client.dialog.UserImsiModifyDialog;
import risetek.client.dialog.UserIpModifyDialog;
import risetek.client.dialog.UserNameModifyDialog;
import risetek.client.dialog.UserNoteModifyDialog;
import risetek.client.dialog.UserPasswordModifyDialog;
import risetek.client.model.RismileUserTable;
import risetek.client.view.UserView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.log.client.control.RismileTableController;
import com.risetek.rismile.log.client.model.RismileTable;
import com.risetek.rismile.log.client.view.RismileTableView;

public class RadiusUserController extends RismileTableController {
	private static String emptyForm = "clearuser";
	private static String modifyForm = "SqlUserModify";
	RismileUserTable data = new RismileUserTable();

	public UserView view;

	public RadiusUserController() {
		view = new UserView(this);
		data.setLimit( view.grid.getRowCount() - 1 );
	}

	public void load() {
		MessageConsole.setText("提取用户数据");
		String query = "lpage=" + data.getLimit() + "&offset="
				+ data.getOffset() + "&like=" +data.filter ;
		remoteRequest.get("SqlUserInfoXML", query, this);
	}

	public void add(String name, String imsi, String password, String ip,
			RequestCallback callback) {
		String query = "function=newuser&username=" + name + "&imsicode="
				+ imsi + "&password=" + password + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		remoteRequest.get(modifyForm, query, callback);
	}

	public void modifyName(String rowID, String name, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&username=" + name;
		remoteRequest.get(modifyForm, query, callback);
	}

	public void modifyImsi(String rowID, String imsi, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&imsicode=" + imsi;
		remoteRequest.get(modifyForm, query, callback);
	}

	public void modifyIp(String rowID, String ip, RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		remoteRequest.get(modifyForm, query, callback);
	}

	public void modifyPassword(String rowID, String password,
			RequestCallback callback) {
		String query = "function=moduser&id=" + rowID + "&password=" + password;
		remoteRequest.get(modifyForm, query, callback);
	}

	public void modifyNote(String rowID, String note,
			RequestCallback callback)
	{
		String query = "function=moduser&id=" + rowID + "&note=" + note;
		remoteRequest.get(modifyForm, query, callback);
	}

	public void delRow(String rowID, RequestCallback callback) {
		String query = "function=deluser&id=" + rowID;
		remoteRequest.get(modifyForm, query, callback);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取用户数据失败");
	}

	public void onResponseReceived(Request request, Response response)
	{
		MessageConsole.setText("获得用户数据"); //wangx
		data.parseXML(response.getText());
		view.render(data);
	}

	public class TableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			String rowid = table.getText(row, 0);
			String tisp_value = table.getText(row, col);
			switch (col) {
			case 0:
				// 选择了删除用户。
				/*
				if (Window.confirm("是否要删除该用户?\n" + "用户名:"
						+ view.getGrid().getText(row, 2) + "\n" + "IMSI:  "
						+ view.getGrid().getText(row, 1) + "\n" + "IP地址:"
						+ view.getGrid().getText(row, 4))) {
					delRow(rowid, RadiusUserController.this);
				}
				*/
				UserDelControl del_control = new UserDelControl();
				del_control.dialog.confirm.addClickHandler(del_control);
				del_control.dialog.show(rowid, tisp_value);
				break;
			case 1:
				// 修改IMSI号码。
				UserIMSIModifyControl ismi_control = new UserIMSIModifyControl();
				ismi_control.dialog.confirm.addClickHandler(ismi_control);
				ismi_control.dialog.show(rowid, tisp_value);
				break;
			case 2:
				// 修改用户名称。
				UserNameModifyControl name_control = new UserNameModifyControl();
				name_control.dialog.confirm.addClickHandler(name_control);
				name_control.dialog.show(rowid, tisp_value);
				break;
			case 3:
				// 修改用户口令。
				UserPasswordModifyControl password_control = new UserPasswordModifyControl();
				password_control.dialog.confirm
						.addClickHandler(password_control);
				password_control.dialog.show(rowid);
				break;
			case 4:
				// 修改 分配IP 地址
				UserIpModifyControl ip_control = new UserIpModifyControl();
				ip_control.dialog.confirm.addClickHandler(ip_control);
				ip_control.dialog.show(rowid, tisp_value);
				break;
			case 5:
				// 修改用户备注
				UserNoteModifyControl note_control = new UserNoteModifyControl();
				note_control.dialog.confirm.addClickHandler(note_control);
				note_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}
		}

		// ----------------- 删除用户
		public class UserDelControl implements ClickHandler,
				RequestCallback {
			public UserDelDialog dialog = new UserDelDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					delRow(dialog.rowid, this);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

		// ----------------- 修改 IMSI 号码
		public class UserIMSIModifyControl implements ClickHandler,
				RequestCallback {
			public UserImsiModifyDialog dialog = new UserImsiModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyImsi(dialog.rowid, dialog.newValueBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

		// ----------------- 修改 分配 IP 地址
		public class UserIpModifyControl implements ClickHandler,
				RequestCallback {
			public UserIpModifyDialog dialog = new UserIpModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyIp(dialog.rowid, dialog.newValueBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

		// ----------------- 修改用户名称
		public class UserNameModifyControl implements ClickHandler,
				RequestCallback {
			public UserNameModifyDialog dialog = new UserNameModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					SysLog.log(dialog.newValueBox.getText());
					modifyName(dialog.rowid, dialog.newValueBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

		// ----------------- 修改用户口令
		public class UserPasswordModifyControl implements ClickHandler,
				RequestCallback {
			public UserPasswordModifyDialog dialog = new UserPasswordModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					modifyPassword(dialog.rowid, dialog.passwordbox.getText(), this);
					dialog.confirm.setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

	
		// ----------------- 修改用户备注
		public class UserNoteModifyControl implements ClickHandler,
				RequestCallback {
			public UserNoteModifyDialog dialog = new UserNoteModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					modifyNote(dialog.rowid, dialog.note.getText(), this);
					dialog.confirm.setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusUserController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// ----------------- 增加用户
	public class AddUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			AddUserControl control = new AddUserControl();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class AddUserControl implements ClickHandler, RequestCallback {
			public UserAddDialog dialog = new UserAddDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
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
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// ----------------- 设定用户信息过滤
	public class FilterUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			control control = new control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class control implements ClickHandler { //, RequestCallback {
			public UserFilterDialog dialog = new UserFilterDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					data.filter = dialog.filter.getText();
					// 查询条件变更，需要归位。
					data.setOffset(0);
					dialog.hide();
					if(!("".equalsIgnoreCase(data.filter)))
						view.setBannerTips("用户信息被过滤");
					else
						view.setBannerTips("");
					load();
				}
			}
		}
	}
	
	// 清除所有用户
	public class EmptyAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("清除用户操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除所有用户?")) {
				remoteRequest.get(emptyForm,null, new EmptyCallback());
			}
		}
	}

	public RismileTable getTable() {
		return data;
	}

	public RismileTableView getView() {
		return view;
	}

}
