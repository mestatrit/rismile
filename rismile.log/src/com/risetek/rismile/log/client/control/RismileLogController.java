package com.risetek.rismile.log.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.model.RismileLogTable;
import com.risetek.rismile.log.client.view.LogFilterDialog;
import com.risetek.rismile.log.client.view.RismileLogView;

public class RismileLogController extends RismileTableController implements RequestCallback {
	private static String loadForm = "SqlLogMessageXML";
	private static String emptyForm = "clearlog";

	// 视图实体。
	public RismileLogView view;
	// 数据实体
	public RismileLogTable data = new RismileLogTable();
	
	public RismileLogController() {
		// 视图实体。
		view = new RismileLogView(this);
		data.ASC = false;
		data.setLimit( view.grid.getRowCount() - 1 );
	}

	public void load(){
		MessageConsole.setText("提取运行记录数据");
		String query = "lpage="+data.getLimit()+"&offset="+data.getOffset()+"&like="+data.message_filer;
		remoteRequest.get(loadForm, query, this);
	}
	
	public class AutoRefreshClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			data.autoRefresh = !data.autoRefresh;
			load();
		}	
	}

	public class ClearLogAction implements ClickHandler , RequestCallback {

		public void onError(Request request, Throwable exception) {
			RismileLogController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			view.clearButton.setEnabled(true);
			load();
		}

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要清除日志?")) {
				view.clearButton.setEnabled(false);
				remoteRequest.get(emptyForm, null, this);
			}
		}
	}
	
	
	// ----------------- 设定用户信息过滤
	public class FilterLogAction implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			control control = new control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class control implements ClickHandler { //, RequestCallback {
			public LogFilterDialog dialog = new LogFilterDialog();

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					data.message_filer = dialog.filter.getText();
					dialog.hide();
					if(!("".equalsIgnoreCase(data.message_filer)))
						view.setBannerTips("记录信息被过滤");
					else
						view.setBannerTips("");
					load();
				}
			}
		}
	}
	
	
	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取运行记录数据失败");
	}

	public void onResponseReceived(Request request, Response response) {
		MessageConsole.setText("获得运行记录数据");
		data.parseXML(response.getText());
		view.render(data);
	}

	public RismileTable getTable() {
		return data;
	}

	public RismileTableView getView() {
		return view;
	}
	

}
