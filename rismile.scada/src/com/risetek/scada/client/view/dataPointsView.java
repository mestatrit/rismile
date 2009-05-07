package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.scada.client.Entry;
import com.risetek.scada.client.remote.RequestFactory;

public class dataPointsView extends Composite {

	private final Grid table = new Grid(2,1);
	TextBox userLabel = new TextBox();
	private final VerticalPanel frame = new VerticalPanel();
	private final FlexTable datapoints = new FlexTable();

	
	private class createPoints implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			getDatas(userLabel.getText());
		}
	}
	
	
	private class dataPointsCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			GWT.log("error", null);
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			Document datas = XMLParser.parse(response.getText());
			Element datasElement = datas.getDocumentElement();
			NodeList nodelist = datasElement.getElementsByTagName("SET");
			for (int i = 0; i < nodelist.getLength(); i++) {
				datapoints.setText(i, 0, ((Element)nodelist.item(i)).getAttribute("id"));
				datapoints.setText(i, 1, nodelist.item(i).getFirstChild().getNodeValue());
			}
			GWT.log("ok", null);
		}
		
	}
	
	public void getDatas(String blob)
	{
		RequestFactory request = new RequestFactory();
		if( null == blob )
		{
			request.get("greet", null , new dataPointsCallback());
		}
		else
		{
			request.get("greet", "blob="+blob, new dataPointsCallback());
		}
	}
	
	public dataPointsView() {
		datapoints.setBorderWidth(1);
		table.setBorderWidth(1);

		table.setWidth("100%");

		final Grid pppGrid = new Grid(1,3);
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setWidget(0, 2, new Button("修改", new createPoints()));

		table.setWidget(0, 0, pppGrid);
		table.setWidget(1, 0, datapoints);
		
		frame.add(table);
		frame.setWidth("100%");		
		frame.setHeight(Entry.SinkHeight);
		initWidget(frame);
	}

	public void onLoad()
	{
		getDatas(null);
	}
}
