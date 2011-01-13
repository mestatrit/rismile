package risetek.client.control;

import risetek.client.dialog.UserAddDialog;
import risetek.client.dialog.UserDelDialog;
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
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.dialog.FilterDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.NavBar.NavEvent;
import com.risetek.rismile.client.view.NavBar.NavHandler;

public class RadiusUserController extends AController {
	
	public static RadiusUserController INSTANCE = new RadiusUserController();
	final RismileUserTable data = new RismileUserTable(true);
	private static RequestFactory remoteRequest = new RequestFactory("user/");
	public final UserView view = new UserView();

	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取用户数据失败");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得用户数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}	
	
	public static abstract class DialogControl {
		protected abstract CustomDialog getDialog();
		
		RequestCallback myCaller = new myRemoteRequestCallback();
		class myRemoteRequestCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("提取用户数据失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( getDialog().processResponse(response))
					load();
			}
		}
	}
	
	private static String emptyForm = "delete/";
	private static String modifyForm = "modi/";

	private RadiusUserController() {

		data.setLimit( view.grid.getRowCount() - 1 );
		view.navbar.addNavHandler(new NavHandler(){
			@Override
			public void onNav(NavEvent event) {
				data.moveDir(event.getResult());
				load();
			}
		});
	}

	public static void load() {
		MessageConsole.setText("提取用户数据");
//		String RESTful = INSTANCE.data.getLimit()+"/"+INSTANCE.data.getOffset()+"/";
		String RESTful = INSTANCE.data.getLimit()+"/"+INSTANCE.data.getOffset();
		if( !"".equalsIgnoreCase(INSTANCE.data.filter)) {
			RESTful += "/" + INSTANCE.data.filter;
		}
		remoteRequest.get("info/" + RESTful , null, RemoteCaller);
	}

	public static void add(String name, String imsi, String password, String ip,
			RequestCallback callback) {
		String query = "username=" + name + "&imsicode="
				+ imsi + "&password=" + password + "&ipaddress="
				+ IPConvert.long2IPString(ip);

		String RESTful = imsi + "/" + name + "/";
		remoteRequest.get("add/" + RESTful, query, callback);
	}

	public static void modifyName(String rowID, String name, RequestCallback callback) {
		String RESTful = rowID + "/" + "username/" + name + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}

	public static void modifyImsi(String rowID, String imsi, RequestCallback callback) {
		String RESTful = rowID + "/" + "imsicode/" + imsi + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}

	public static void modifyLevel(String rowID, String level, RequestCallback callback) {
		String RESTful = rowID + "/" + "level/" + level + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}
	
	public static void modifyIp(String rowID, String ip, RequestCallback callback) {
		String RESTful = rowID + "/" + "ipaddress/" + IPConvert.long2IPString(ip) + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}
	public static void modifyPassword(String rowID, String password,
			RequestCallback callback) {
		String RESTful = rowID + "/" + "password/" + password + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}
	public static void modifyNote(String rowID, String note,
			RequestCallback callback)
	{
		String RESTful = rowID + "/" + "note/" + note + "/";
		remoteRequest.get(modifyForm + RESTful , null, callback);
	}
	/*
	public static void delRow(String rowID, RequestCallback callback) {
		String RESTful = rowID + "/";
		remoteRequest.get(emptyForm + RESTful, null, callback);
	}
	*/
	/*
	public static void modifyStatus(String rowID, String status, RequestCallback callback) {
		remoteRequest.get(modifyForm, null, callback);
	}
*/	
/*	
	public static void modifyLoginTime(String rowID, String status, RequestCallback callback) {
		remoteRequest.get(modifyForm, null, callback);
	}
*/	
	/*
	public static void modifySecondIp(String rowID, String ip, RequestCallback callback) {
		remoteRequest.get(modifyForm, null, callback);
	}
*/	
	
	// ----------------- 删除用户
	public static class UserDelControl extends DialogControl implements ClickHandler {
		public UserDelDialog dialog = new UserDelDialog();

		@Override
		public void onClick(ClickEvent event) {
			if( !dialog.isValid() )
				return;
			String RESTful = "";
			if(! "所有".equalsIgnoreCase(dialog.rowid))
				RESTful = dialog.rowid + "/";
			remoteRequest.DELETE(emptyForm + RESTful, null, myCaller);
		}

		@Override
		protected CustomDialog getDialog() {
			return dialog;
		}
	}
	
	public static class TableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			if( Mycell == null ) return;
			int row = Mycell.getRowIndex();
			int col = Mycell.getCellIndex();
            
			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			String rowid = table.getText(row, 0);
			int index = col;
			String tisp_value = table.getText(row, col);
			if(tisp_value.length() == 1){
				int tvalue = tisp_value.charAt(0);
				if(tvalue == 160){
					tisp_value = "";
				}
			}
			switch (index) {
			case 0:
				// 选择了删除用户。
				UserDelControl del_control = new UserDelControl();
				del_control.dialog.submit.setText("删除");
				del_control.dialog.submit.addClickHandler(del_control);
				del_control.dialog.show(rowid);
				break;
			case 1:
				// 修改警告级别。
				String newVal = table.getText(row, 8);
				if( "2".equalsIgnoreCase(newVal) )
					newVal = "1";
				else
					newVal = "2";
				
				modifyLevel(rowid, newVal, new RequestCallback() {
					@Override
					public void onError(Request request, Throwable exception) {
						MessageConsole.setText("修改警告级别失败");
					}

					@Override
					public void onResponseReceived(Request request, Response response) {
							load();
					}
				});
				
				break;
			case 2:
				// 修改IMSI号码。
				UserIMSIModifyControl ismi_control = new UserIMSIModifyControl();
				ismi_control.dialog.submit.setText("修改");
				ismi_control.dialog.submit.addClickHandler(ismi_control);
				ismi_control.dialog.show(rowid, tisp_value);
				break;
			case 3:
				// 修改用户名称。
				UserNameModifyControl name_control = new UserNameModifyControl();
				name_control.dialog.submit.setText("修改");
				name_control.dialog.submit.addClickHandler(name_control);
				name_control.dialog.show(rowid, tisp_value);
				break;
			case 4:
				// 修改用户口令。
				UserPasswordModifyControl password_control = new UserPasswordModifyControl();
				password_control.dialog.submit.setText("修改");
				password_control.dialog.submit.addClickHandler(password_control);
				password_control.dialog.show(rowid);
				break;
			case 5:
				// 修改 分配IP 地址
				UserIpModifyControl ip_control = new UserIpModifyControl();
				ip_control.dialog.submit.setText("修改");
				ip_control.dialog.submit.addClickHandler(ip_control);
				ip_control.dialog.show(rowid, tisp_value);
				break;
			case 6:
				// 修改第二分配地址
				/*
				UserNoteModifyControl sip_control = new UserNoteModifyControl();
				sip_control.dialog.submit.setText("修改");
				sip_control.dialog.submit.addClickHandler(sip_control);
				sip_control.dialog.show(rowid, tisp_value);
				*/
				break;
			case 7:
				// 修改用户级别
				/*
				UserStatusModifyControl status_control = new UserStatusModifyControl();
				status_control.dialog.submit.setText("修改");
				status_control.dialog.submit.addClickHandler(status_control);
				status_control.dialog.show(rowid, tisp_value);
				*/
				break;
			case 8:
				// 修改登录时段
				/*
				UserLoginTimeModifyControl loginTime_control = new UserLoginTimeModifyControl();
				loginTime_control.dialog.submit.setText("修改");
				loginTime_control.dialog.submit.addClickHandler(loginTime_control);
				loginTime_control.dialog.show(rowid, tisp_value);
				*/
				break;
			case 9:
				// 修改用户备注
				UserNoteModifyControl note_control = new UserNoteModifyControl();
				note_control.dialog.submit.setText("修改");
				note_control.dialog.submit.addClickHandler(note_control);
				note_control.dialog.show(rowid, tisp_value);
				break;
			default:
				break;
			}
		}

		// ----------------- 修改 IMSI 号码
		public class UserIMSIModifyControl extends DialogControl implements ClickHandler {
			public UserImsiModifyDialog dialog = new UserImsiModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid() )
					return;
				SysLog.log(dialog.newValueBox.getText());
				modifyImsi(dialog.rowid, dialog.newValueBox.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}

		// ----------------- 修改 分配 IP 地址
		public class UserIpModifyControl extends DialogControl implements ClickHandler {
			public UserIpModifyDialog dialog = new UserIpModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid() )
					return;
				SysLog.log(dialog.newValueBox.getText());
				modifyIp(dialog.rowid, dialog.newValueBox.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}

		// ----------------- 修改用户名称
		public class UserNameModifyControl extends DialogControl implements ClickHandler {
			public UserNameModifyDialog dialog = new UserNameModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid() )
					return;
				SysLog.log(dialog.newValueBox.getText());
				modifyName(dialog.rowid, dialog.newValueBox.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}

		// ----------------- 修改用户口令
		public class UserPasswordModifyControl extends DialogControl implements ClickHandler {
			public UserPasswordModifyDialog dialog = new UserPasswordModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid() )
					return;
				dialog.submit.setEnabled(false);
				modifyPassword(dialog.rowid, dialog.passwordbox.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}

		// ----------------- 修改第二分配地址
		/*
		public class UserSecondModifyControl extends DialogControl implements ClickHandler {
			public UserIpModifyDialog dialog = new UserIpModifyDialog();
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid()){
					return;
				}
				SysLog.log(dialog.newValueBox.getText());
				modifySecondIp(dialog.rowid, dialog.newValueBox.getText(), myCaller);
			}
			
		}
		*/
		/*
		// ----------------- 修改用户级别
		public class UserStatusModifyControl extends DialogControl implements ClickHandler {
			public UserStatusModifyDialog dialog = new UserStatusModifyDialog();
			
			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}

			@Override
			public void onClick(ClickEvent event) {
				if(!dialog.isValid()){
					return;
				}
				dialog.submit.setEnabled(false);
				int index = dialog.status.getSelectedIndex();
				modifyStatus(dialog.rowid, dialog.status.getValue(index), myCaller);
			}
		}
		*/
		// ----------------- 修改用户登录时段
		/*
		public class UserLoginTimeModifyControl extends DialogControl implements ClickHandler {
			public UserLoginTimeModifyDialog dialog = new UserLoginTimeModifyDialog();

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}

			@Override
			public void onClick(ClickEvent event) {
				if(!dialog.isValid()){
					return;
				}
				dialog.submit.setEnabled(false);
				modifyLoginTime(dialog.rowid, dialog.getTime(), myCaller);
			}
		}
		*/
		// ----------------- 修改用户备注
		public class UserNoteModifyControl extends DialogControl implements ClickHandler {
			public UserNoteModifyDialog dialog = new UserNoteModifyDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( !dialog.isValid() )
					return;
				dialog.submit.setEnabled(false);
				modifyNote(dialog.rowid, dialog.note.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}

	// ----------------- 增加用户
	
	public static class AddUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			AddUserControl control = new AddUserControl();
			control.dialog.submit.setText("添加");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class AddUserControl extends DialogControl implements ClickHandler {
			public UserAddDialog dialog = new UserAddDialog();
			@Override
			public void onClick(ClickEvent event) {
				if (!dialog.isValid())
					return;
				dialog.submit.setEnabled(false);
				add(dialog.usernamebox.getText(), dialog.IMSI.getText(),
							dialog.passwordbox.getText(), dialog.ipaddress
									.getText(), myCaller);
			}

			@Override
			protected CustomDialog getDialog() {
				return dialog;
			}
		}
	}

	// ----------------- 设定用户信息过滤
	public static class FilterUserAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			control control = new control();
			control.dialog.submit.setText("确定");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class control implements ClickHandler {
			public FilterDialog dialog = new FilterDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (!dialog.isValid())
					return;
				INSTANCE.data.filter = dialog.filter.getText();
				// 查询条件变更，需要归位。
				INSTANCE.data.setOffset(0);
				dialog.hide();
				if(!("".equalsIgnoreCase(INSTANCE.data.filter)))
					INSTANCE.view.setBannerTips("用户信息被过滤");
				else
					INSTANCE.view.setBannerTips("");
				load();
			}
		}
	}
	
	// 清除所有用户
	public static class EmptyAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("清除用户操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				INSTANCE.data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			
			// 选择了删除用户。
			UserDelControl del_control = new UserDelControl();
			del_control.dialog.submit.setText("删除");
			del_control.dialog.submit.addClickHandler(del_control);
			del_control.dialog.show("所有");
		}
	}
	//----------------刷新界面
	public static class RefreshAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			load();
		}
		
	}
	
	public RismileUserTable getTable() {
		return data;
	}
	
	@Override
	public void disablePrivate() {
		view.disablePrivate();
	}

	@Override
	public void enablePrivate() {
		view.enablePrivate();
	}

	@Override
	public IRisetekView getView() {
		return view;
	}

	@Override
	public void doAction(int keyCode, boolean alt) {
		view.ProcessControlKey(keyCode, alt);
	}
}
