package com.risetek.rismile.log.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.dialog.FilterDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.NavBar.NavEvent;
import com.risetek.rismile.client.view.NavBar.NavHandler;
import com.risetek.rismile.log.client.model.RismileLogTable;
import com.risetek.rismile.log.client.view.RismileLogView;

public class RismileLogController extends AController {

	public static RismileLogController INSTANCE = new RismileLogController(); 
	private static RequestFactory remoteRequest = new RequestFactory("log/");
	private final static String loadForm = "info/";
	private final static String emptyForm = "delete/";

	// 视图实体。
	public final RismileLogView view = new RismileLogView();
	// 数据实体
	public final RismileLogTable data = new RismileLogTable(false);
	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取运行记录数据失败");
			load();
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得运行记录数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}
	
	
	private RismileLogController() {
		// 视图实体。
		data.setLimit( view.grid.getRowCount() - 1 );
		view.navbar.addNavHandler(new NavHandler(){
			@Override
			public void onNav(NavEvent event) {
				data.moveDir(event.getResult());
				load();
			}
		});
	}

	public static void load(){
		MessageConsole.setText("提取运行记录数据");
		if( INSTANCE.data.autoRefresh )
			INSTANCE.data.setOffset(0);
		String RESTful = INSTANCE.data.getLimit()+"/"+INSTANCE.data.getOffset();
		if( !"".equalsIgnoreCase(INSTANCE.data.filer)) {
			RESTful += "/" + INSTANCE.data.filer;
		}
		remoteRequest.get(loadForm + RESTful, null, RemoteCaller);
	}
	
	public static class AutoRefreshClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			INSTANCE.data.autoRefresh = !INSTANCE.data.autoRefresh;
			load();
		}	
	}

	public static class ClearLogAction implements ClickHandler , RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			RemoteCaller.onError(request, exception);
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			INSTANCE.view.clearButton.setEnabled(true);
			load();
		}

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除日志?")) {
				INSTANCE.view.clearButton.setEnabled(false);
				remoteRequest.DELETE(emptyForm, null, ClearLogAction.this);
			}
		}
	}
	
	
	// ----------------- 设定用户信息过滤
	public static class FilterLogAction implements ClickHandler {

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
				if (dialog.isValid()) {
					INSTANCE.data.filer = dialog.filter.getText();
					// 查询条件变更，需要归位。
					INSTANCE.data.setOffset(0);
					dialog.hide();
					if(!("".equalsIgnoreCase(INSTANCE.data.filer)))
						INSTANCE.view.setBannerTips("记录信息被过滤");
					else
						INSTANCE.view.setBannerTips("");
					load();
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
		view.ProcessControlKey(keyCode,alt);
	}

}
