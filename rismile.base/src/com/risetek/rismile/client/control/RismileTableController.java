package com.risetek.rismile.client.control;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.view.RismileTableView;

public abstract class RismileTableController implements RequestCallback {

	public RequestFactory remoteRequest = new RequestFactory();
	String tableName;
	
	public void changeTableData(String formFunction, String query, RequestCallback callback){
		remoteRequest.get(formFunction, query, callback);
	}

	
	public void loadTableData(String formFunction, String query){
		remoteRequest.get(formFunction, query, this);
	}

	public class navigatorFirstClick implements ClickListener
	{

		public void onClick(Widget sender) {
			getTable().setOffset(0);
			getView().navbar.enabelNavbar(false, false, false, false);
			getView().loadModel();
		}
		
	}
	
	// NAVIGATOR 按键的事件处理
	public class navigatorNextClick implements ClickListener
	{

		public void onClick(Widget sender) {
			int offset = getTable().getOffset() + getView().getDataRowCount();
			getTable().setOffset(offset);
			getView().navbar.enabelNavbar(false, false, false, false);
			getView().loadModel();
		}
		
	}

	public class navigatorLastLastClick implements ClickListener
	{

		public void onClick(Widget sender) {
			getView().startRow = getView().TotalRecord - getView().getDataRowCount();
			getView().navbar.enabelNavbar(false, false, false, false);
			getView().loadModel();
		}
		
	}

	public class navigatorPrevClick implements ClickListener
	{

		public void onClick(Widget sender) {
			getView().startRow -= getView().getDataRowCount();
			if (getView().startRow < 0) {
				getView().startRow = 0;
			}
			getView().navbar.enabelNavbar(false, false, false, false);
			getView().loadModel();
		}
		
	}
	
	public abstract RismileTable getTable();
	public abstract RismileTableView getView();
	
	
}
