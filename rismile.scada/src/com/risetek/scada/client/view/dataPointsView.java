package com.risetek.scada.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.risetek.scada.client.Entry;
import com.risetek.scada.client.remote.RequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class dataPointsView extends Composite {

	private final Grid table = new Grid(1,1);
	TextBox userLabel = new TextBox();

	
	private class createPoints implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RequestFactory request = new RequestFactory();
			request.get("greet", "blob="+userLabel.getText(), new RequestCallback(){

				@Override
				public void onError(Request request, Throwable exception) {
					GWT.log("error", null);
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					GWT.log("ok", null);
				}
				
			});
		}
		
	}
	
	public dataPointsView() {

		table.setBorderWidth(1);

		table.setWidth("100%");

		final Grid pppGrid = new Grid(1,3);
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setWidget(0, 2, new Button("修改", new createPoints()));

		table.setHeight(Entry.SinkHeight);
		
		table.setWidget(0, 0, pppGrid);
		initWidget(table);
	}

}
