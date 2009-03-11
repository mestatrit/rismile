package com.risetek.rismile.client.control;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.model.RismileTable;
import com.risetek.rismile.client.view.RismileTableView;

public abstract class RismileTableController implements RequestCallback {

	public RequestFactory remoteRequest = new RequestFactory();
	
	// NAVIGATOR 按键的事件处理
	public class navigatorFirstClick implements ClickListener
	{
		public void onClick(Widget sender) {
			getTable().setOffset(getTable().getSum() - getTable().getLimit());
			load();
		}
	}
	
	public class navigatorNextClick implements ClickListener
	{

		public void onClick(Widget sender) {
			int offset = getTable().getOffset() - getTable().getLimit();
			getTable().setOffset(offset);
			load();
		}
		
	}

	public class navigatorPrevClick implements ClickListener
	{
		public void onClick(Widget sender) {
			int offset = getTable().getOffset() + getTable().getLimit();
			getTable().setOffset(offset);
			load();
		}
	}
	
	public class navigatorLastLastClick implements ClickListener
	{
		public void onClick(Widget sender) {
			getTable().setOffset(0);
			load();
		}
		
	}

	public abstract RismileTable getTable();
	public abstract RismileTableView getView();
	
	// 载入数据。
	public abstract void load();
}
