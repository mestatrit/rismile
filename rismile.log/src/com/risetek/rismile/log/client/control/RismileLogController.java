package com.risetek.rismile.log.client.control;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.RismileTableController;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.log.client.model.RismileLogTable;
import com.risetek.rismile.log.client.view.RismileLogView;

public class RismileLogController extends RismileTableController implements RequestCallback {
	private static String loadForm = "SqlLogMessageXML";
	private static String emptyForm = "clearlog";

	// 视图实体。
	public RismileLogView logView;
	// 数据实体
	public RismileLogTable table = new RismileLogTable();
	
	public RismileLogController() {
		// 视图实体。
		logView = new RismileLogView(this);
	}

	public void load(int limit, int offset){
		String query = "lpage="+limit+"&offset="+offset;
		remoteRequest.get(loadForm, query, this);
	}
	
	public void load(){
		String query = "lpage="+table.getLimit()+"&offset="+table.getOffset();
		remoteRequest.get(loadForm, query, this);
	}

	
	public void empty(){
		remoteRequest.get(emptyForm, null, this);
	}
	
	public class AutoRefreshClick implements ClickListener{

		public void onClick(Widget sender) {

			logView.autoRefresh = !logView.autoRefresh;
			if (logView.autoRefresh) {
				logView.TogAutoRefresh.setText("查看历史");
				setStartRow(0);
				logView.update();
			} else {
				logView.TogAutoRefresh.setText("自动更新");
				logView.loadModel();
			}
		}	
	}

	public class ClearLogAction implements ClickListener {

		public void onClick(Widget sender) {

			if (Window.confirm("是否要清除日志?")) {
				logView.clearButton.setEnabled(false);
				empty();
			}
		}
	}
	
	
	public void onError(Request request, Throwable exception) {
		
	}

	public void onResponseReceived(Request request, Response response) {
		table.parseXML(response.getText());
		logView.render(table);
	}

	public void setStartRow(int startRow){
		table.setOffset(startRow);
	}
	

}
