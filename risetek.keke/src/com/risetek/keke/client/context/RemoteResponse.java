package com.risetek.keke.client.context;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class RemoteResponse implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
		PosContext.Log("Remote request:" + "error.");
	}

	@Override
	public void onResponseReceived(Request request, Response response) {
		PosContext.Log("Remote: "+response.getText());
		
		Document customerDom = XMLParser.parse(response.getText());
		
		NodeList list = customerDom.getElementsByTagName("stick");
		
		for(int loop=0; loop < list.getLength(); loop++) {
			String[] nodes = new String[4];
			Node node = list.item(loop);
			nodes[0] = node.getAttributes().getNamedItem("d").getNodeValue();
			nodes[1] = node.getAttributes().getNamedItem("type").getNodeValue();
			nodes[2] = node.getNodeValue();
			nodes[3] = node.getFirstChild().getNodeValue(); 
			PosContext.Log("Response: "+nodes[0]);
			PosContext.Log("Response: "+nodes[1]);
			PosContext.Log("Response: "+nodes[2]);
			PosContext.Log("Response: "+nodes[3]);
		}
	}

}
