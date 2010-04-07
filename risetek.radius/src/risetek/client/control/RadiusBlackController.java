package risetek.client.control;

import risetek.client.dialog.BlackUserDialog;
import risetek.client.model.RismileBlackUserTable;
import risetek.client.view.BlackUserView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.utils.UI;
import com.risetek.rismile.client.view.RismileTableView;

public class RadiusBlackController extends RismileTableController {
	private static String loadForm = "SqlBlackUserInfoXML";
	private static String emptyForm = "clearblack";
	private static String modifyForm = "SqlBlackUserInfoXML";

	RismileBlackUserTable data = new RismileBlackUserTable();
	public BlackUserView view;

	public RadiusBlackController() {
		view = new BlackUserView(this);
		data.setLimit( view.grid.getRowCount() - 1 );
	}

	public void load() {
		MessageConsole.setText("提取不明用户数据");

		String query = "lpage=" + data.getLimit() + "&offset="
				+ data.getOffset();
		remoteRequest.get(loadForm, query, this);
	}

	public void empty() {
		remoteRequest.get(emptyForm, null, this);
	}

	public void add(String rowID, String name, String imsi, String password,
			String ip, RequestCallback callbak) {
		String query = "function=newuser&id=" + rowID + "&username=" + name
				+ "&imsicode=" + imsi + "&password=" + password + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		remoteRequest.get(modifyForm, query, callbak);
	}

	public void delRow(String rowID) {
		String query = "function=deluser&id=" + rowID;
		remoteRequest.get(modifyForm, query, this);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取不明用户数据失败");
		SysLog.log("RadiusBlackController");
	}

	public void onResponseReceived(Request request, Response response) {
		MessageConsole.setText("获得不明用户数据");
		data.parseXML(response.getText());
		view.render(data);
	}

	public class EmptyAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("清除不明用户操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			if(!Entry.login){
				Window.alert(UI.errInfo);
				return;
			}
			if (Window.confirm("是否要清除所有不明用户?")) {
				remoteRequest.get(emptyForm,null, new EmptyCallback());
			}
		}
	}
	
	public class refreshAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			data.setOffset(0);
			load();
			}
	}

	public class TableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(!Entry.login){
				Window.alert(UI.errInfo);
				return;
			}
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			int row = Mycell.getRowIndex();
			int cell = Mycell.getCellIndex();
			

			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			String rowid = view.getGrid().getText(row, 0);
			String tips_ismi = view.getGrid().getText(row, 1);
			String tips_username = view.getGrid().getText(row, 2);
			switch (cell) {
			case 0:
				// 选择了删除用户。
				if (Window.confirm("是否要删除?\n" + "用户名:"
						+ tips_username + "\n" + "IMSI: "
						+ tips_ismi)) {

					delRow(rowid);
				}
				break;
			case 1:
			case 2:
				// 导入用户为合法用户。
				BlackUserControl control = new BlackUserControl();
				control.dialog.confirm.addClickHandler(control);
				control.dialog.show(rowid, tips_ismi, tips_username);
				break;
			default:
				break;
			}
		}

		// ----------------- 认可用户信息
		public class BlackUserControl implements ClickHandler, RequestCallback {
			public BlackUserDialog dialog = new BlackUserDialog();

			public void onError(Request request, Throwable exception) {
				RadiusBlackController.this.onError(request,exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					add(dialog.rowid, dialog.usernameBox.getText(),
							dialog.imsiBox.getText(), dialog.passwordBox
									.getText(), dialog.ipaddressBox.getText(),
							this);
					((Button)event.getSource()).setEnabled(false);
				}
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
