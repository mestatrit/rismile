package risetek.client.control;

import risetek.client.dialog.BlackUserDialog;
import risetek.client.model.RismileBlackUserTable;
import risetek.client.view.BlackUserView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.view.RismileTableView;

public class RadiusBlackController extends RismileTableController {
	private static String loadForm = "SqlBlackUserInfoXML";
	private static String emptyForm = "clearblack";
	private static String modifyForm = "SqlBlackUserInfoXML";

	RismileBlackUserTable data = new RismileBlackUserTable();
	public BlackUserView view;

	public RadiusBlackController() {
		view = new BlackUserView(this);
		data.setLimit( view.grid.getRowCount() );
	}

	public void load() {
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
		SysLog.log("RadiusBlackController");
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data);
	}

	public class EmptyAction implements ClickListener {

		public void onClick(Widget sender) {
			if (Window.confirm("是否要清除所有不明用户?")) {
				view.clearButton.setEnabled(false);
				empty();
			}
		}
	}

	public class TableAction implements TableListener {

		public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
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
				control.dialog.confirm.addClickListener(control);
				control.dialog.show(rowid, tips_ismi, tips_username);

				// BlackUserDialog dialog = new BlackUserDialog(view,
				// view.getGrid().getText(row, 1), view.getGrid().getText(row,
				// 2));

				break;
			default:
				break;
			}

		}

		// ----------------- 认可用户信息
		public class BlackUserControl implements ClickListener, RequestCallback {
			public BlackUserDialog dialog = new BlackUserDialog(view);

			public void onClick(Widget sender) {
				if (dialog.valid()) {
					add(dialog.rowid, dialog.usernameBox.getText(),
							dialog.imsiBox.getText(), dialog.passwordBox
									.getText(), dialog.ipaddressBox.getText(),
							this);
					((Button)sender).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusBlackController.this.onError(request,exception);
			}

			public void onResponseReceived(Request request, Response response) {
				dialog.hide();
				SysLog.log("remote execute");
				load();
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
