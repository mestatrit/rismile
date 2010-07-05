package com.risetek.keke.client.context;

import java.util.Vector;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;

public class RemoteResponse implements RequestCallback {

	@Override
	public void onError(Request request, Throwable exception) {
		PosContext.Log("Remote request:" + "error.");
	}

	private String getStickAttribute(Node node, String attribute) {
		Node n = node.getAttributes().getNamedItem(attribute);
		if( n != null )
			return n.getNodeValue();
		return null;
	}
	
	@Override
	public void onResponseReceived(Request request, Response response) {
		PosContext.Log("Remote: "+response.getText());
		Vector<String[]> sticklet = new Vector<String[]>();
		
		Document doc = XMLParser.parse(response.getText());
		NodeList list = doc.getElementsByTagName("stick");
		
		for(int loop=0; loop < list.getLength(); loop++) {
			String[] nodes = new String[4];
			Node node = list.item(loop);
			nodes[0] = getStickAttribute(node,"d");
			nodes[1] = getStickAttribute(node,"type");
			nodes[2] = getStickAttribute(node,"p");
			nodes[3] = node.getChildNodes().toString();
			sticklet.add(nodes);
		}
		
		String[][] a = new String[sticklet.size()][4];
		
		for( int loop = 0; loop < sticklet.size(); loop++) {
			String[] t = sticklet.elementAt(loop);
			a[loop][0] = t[0];
			a[loop][1] = t[1];
			a[loop][2] = t[2];
			a[loop][3] = t[3];
		}
		
		Sticklet s = Sticklets.loadSticklet(a);
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.CallerEvent(s));

	}

}
