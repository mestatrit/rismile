package risetek.client.control;

import risetek.client.dialog.ResetFlowDialog;
import risetek.client.model.RismileUserStatusTable;
import risetek.client.view.UserStatusView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.dialog.FilterDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.NavBar.NavEvent;
import com.risetek.rismile.client.view.NavBar.NavHandler;

public class RadiusUserStatusController extends AController {
	
	public static RadiusUserStatusController INSTANCE = new RadiusUserStatusController();
	final RismileUserStatusTable data = new RismileUserStatusTable(true);
	private static RequestFactory remoteRequest = new RequestFactory("user/");
	public final UserStatusView view = new UserStatusView();

	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取用户状态数据失败");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得用户状态数据");
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
				MessageConsole.setText("提取用户数据状态失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( getDialog().processResponse(response))
					load();
			}
		}
	}
	
	private RadiusUserStatusController() {

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
		MessageConsole.setText("提取用户状态数据");
		String RESTful = INSTANCE.data.getLimit()+"/"+INSTANCE.data.getOffset();
		if( !"".equalsIgnoreCase(INSTANCE.data.filter)) {
			RESTful += "/" + INSTANCE.data.filter;
		}
		remoteRequest.get("status/" + RESTful , null, RemoteCaller);
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
	
	//----------------刷新界面
	public static class RefreshAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			load();
		}
		
	}

	//----------------重置流量界面	TODO: FIXME!
	// 清除所有用户
	public static class FlowAction implements ClickHandler {
		class EmptyCallback implements RequestCallback {

			@Override
			public void onError(Request request, Throwable exception) {
				MessageConsole.setText("重置流量计量操作失败");
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				//INSTANCE.data.setOffset(0);
				load();
			}
			
		}
		@Override
		public void onClick(ClickEvent event) {
			ResetFlowControl control = new ResetFlowControl();
			control.dialog.submit.setText("确定");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show("重置流量计量");
		}
	}
	
	
	// ----------------- 重置计量
	public static class ResetFlowControl extends DialogControl implements ClickHandler {
		public ResetFlowDialog dialog = new ResetFlowDialog();

		@Override
		public void onClick(ClickEvent event) {
			if( !dialog.isValid() )
				return;
			String RESTful = "/";
			remoteRequest.DELETE("status/reset" + RESTful, null, myCaller);
		}

		@Override
		protected CustomDialog getDialog() {
			return dialog;
		}
	}
	
	public RismileUserStatusTable getTable() {
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
