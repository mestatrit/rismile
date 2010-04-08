package com.risetek.rismile.log.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestCallback;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.log.client.model.RismileTable;
import com.risetek.rismile.log.client.view.RismileTableView;

public abstract class RismileTableController implements RequestCallback {

	protected RequestFactory remoteRequest = new RequestFactory();
	
	// NAVIGATOR 按键的事件处理
	public class navigatorFirstClick implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			int offset;
			if( getTable().ASC )
				offset = 0;
			else
				offset = getTable().getSum() - getTable().getLimit();
				
			getTable().setOffset(offset);
			load();
		}
	}
	
	public class navigatorNextClick implements ClickHandler
	{

		public void onClick(ClickEvent event) {
			int offset;
			if( !getTable().ASC )
				offset = getTable().getOffset() - getTable().getLimit();
			else
				offset = getTable().getOffset() + getTable().getLimit();
			getTable().setOffset(offset);
			load();
		}
		
	}

	public class navigatorPrevClick implements ClickHandler
	{
		public void onClick(ClickEvent event) {
			int offset;
			if( getTable().ASC )
				offset = getTable().getOffset() - getTable().getLimit();
			else
				offset = getTable().getOffset() + getTable().getLimit();

			getTable().setOffset(offset);
			load();
		}
	}
	
	public class navigatorLastLastClick implements ClickHandler
	{
		public void onClick(ClickEvent event) {
			int offset;
			if( !getTable().ASC )
				offset = 0;
			else
				offset = getTable().getSum() - getTable().getLimit();
				
			getTable().setOffset(offset);
			load();
		}
		
	}

	public abstract RismileTable getTable();
	public abstract RismileTableView getView();
	
	// 载入数据。
	public abstract void load();
}
