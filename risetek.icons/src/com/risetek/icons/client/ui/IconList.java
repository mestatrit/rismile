package com.risetek.icons.client.ui;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class IconList extends Composite {
	private final int NumH = 5;
	private final int NumV = 7;
	private final VerticalPanel vpOuter = new VerticalPanel();
	private final Grid flows = new Grid(NumV, NumH);

	public IconList() {
		//setSize("100%", "100%");
		vpOuter.setBorderWidth(1);
		vpOuter.setSize("100%", "100%");
		vpOuter.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpOuter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flows.setBorderWidth(1);
		vpOuter.add(flows);
		initWidget(vpOuter);
	}

	@Override
	protected void onLoad() {
		try {
			RequestBuilder rqBuilder = new RequestBuilder(RequestBuilder.GET, "/list");
			//rqBuilder.setTimeoutMillis(1000);
			rqBuilder.sendRequest("", new RequestCallback(){

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("远端服务错误");
				}

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					String xml = response.getText();
					if( response.getStatusCode() != 200 ) {
						Window.alert(xml);
						return;
					}
					Document doc = XMLParser.parse(xml);
					NodeList list = doc.getElementsByTagName("icon");
					for(int loop=0; loop < list.getLength() && loop < (NumH*NumV); loop++) {
						Node node = list.item(loop);
						String name = getStickAttribute(node,"name");
						String image = node.getFirstChild().getNodeValue();
						Image eyes = new Image();
						eyes.setUrl("data:image/png;base64," + image);
						Grid tip = new Grid(2,1);
						tip.setWidget(0, 0, eyes);
						tip.setWidget(1, 0, new HTML(name));
						int h = loop / NumH;
						int v = loop % NumH;
						flows.setWidget(h, v, tip);
					}
					
					
				}});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	private static String getStickAttribute(Node node, String attribute) {
		Node n = node.getAttributes().getNamedItem(attribute);
		if( n != null )
			return n.getNodeValue();
		return null;
	}
}
