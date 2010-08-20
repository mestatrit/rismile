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
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.NavBar.NavEvent;
import com.risetek.rismile.client.view.NavBar.NavHandler;

public class RadiusBlackController extends AController {
	
	public static final RadiusBlackController INSTANCE = new RadiusBlackController();
	private static final RequestFactory remoteRequest = new RequestFactory();
	public final BlackUserView view = new BlackUserView();
	final RismileBlackUserTable data = new RismileBlackUserTable(true);
	
	protected static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取不明用户数据失败");
			SysLog.log("RadiusBlackController");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得不明用户数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}
	
	private static final String loadForm = "SqlBlackUserInfoXML";
	private static final String emptyForm = "clearblack";
	private static final String modifyForm = "SqlBlackUserInfoXML";


	private RadiusBlackController() {

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
		MessageConsole.setText("提取不明用户数据");

		String query = "lpage=" + INSTANCE.data.getLimit() + "&offset="
				+ INSTANCE.data.getOffset();
		remoteRequest.get(loadForm, query, RemoteCaller);
	}

	public static void add(String rowID, String name, String imsi, String password,
			String ip, RequestCallback callbak) {
		String query = "function=newuser&id=" + rowID + "&username=" + name
				+ "&imsicode=" + imsi + "&password=" + password + "&ipaddress="
				+ IPConvert.long2IPString(ip);
		remoteRequest.get(modifyForm, query, callbak);
	}

	public static void delRow(String rowID) {
		String query = "function=deluser&id=" + rowID;
		remoteRequest.get(modifyForm, query, RemoteCaller);
	}

	public static class EmptyAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("清除不明用户操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				INSTANCE.data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除所有不明用户?")) {
				remoteRequest.get(emptyForm,null, new EmptyCallback());
			}
		}
	}
	
	public static class refreshAction implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			INSTANCE.data.setOffset(0);
			load();
		}
	}

	public static class TableAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			HTMLTable table = (HTMLTable)event.getSource();
			Cell Mycell = table.getCellForEvent(event);
			int row = Mycell.getRowIndex();
			int cell = Mycell.getCellIndex();
			

			// 在第一列中的是数据的内部序号，我们的操作都针对这个号码。
			String rowid = INSTANCE.view.getGrid().getText(row, 0);
			String tips_ismi = INSTANCE.view.getGrid().getText(row, 1);
			String tips_username = INSTANCE.view.getGrid().getText(row, 2);
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
				control.dialog.submit.setText("导入");
				control.dialog.submit.addClickHandler(control);
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
				RemoteCaller.onError(request,exception);
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
