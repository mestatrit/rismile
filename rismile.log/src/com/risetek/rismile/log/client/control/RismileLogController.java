package com.risetek.rismile.log.client.control;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.model.RismileLogTable;
import com.risetek.rismile.log.client.view.RismileLogView;

public class RismileLogController extends RismileTableController implements RequestCallback {
	private static String loadForm = "SqlLogMessageXML";
	private static String emptyForm = "clearlog";

	// 视图实体。
	public RismileLogView logView;
	// 数据实体
	public RismileLogTable data = new RismileLogTable();
	
	public RismileLogController() {
		// 视图实体。
		logView = new RismileLogView(this);
		data.setLimit(20);
	}

	public void load(){
		String query = "lpage="+data.getLimit()+"&offset="+data.getOffset();
		remoteRequest.get(loadForm, query, this);
	}
	
	public void empty(){
		remoteRequest.get(emptyForm, null, this);
	}
	
	public class AutoRefreshClick implements ClickListener{

		public void onClick(Widget sender) {
			data.autoRefresh = !data.autoRefresh;
			load();
		}	
	}

	public class ClearLogAction implements ClickListener {

		public void onClick(Widget sender) {
			logView.mask();
			if (Window.confirm("是否要清除日志?")) {
				logView.clearButton.setEnabled(false);
				empty();
			}
			logView.unmask();
		}
	}
	
	
	public void onError(Request request, Throwable exception) {
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		logView.render(data);
	}

	public RismileTable getTable() {
		return data;
	}

	public RismileTableView getView() {
		return logView;
	}
	

}
