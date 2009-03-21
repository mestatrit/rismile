package risetek.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RisetekSystemSink;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.sink.Sink.SinkInfo;
import com.risetek.rismile.client.utils.MessageConsole;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class index  extends  Entry implements ClickListener, RequestCallback {

	// 远程方法执行者。
	private RequestFactory remoteRequest = new RequestFactory();

	private Hyperlink link = new Hyperlink();
	
	public void loadSinks() {
		Entry.SinkHeight = "500px";
		
		list.addSink(RisetekHomeSink.init());
		list.addSink(RisetekSystemSink.init());
//		list.addSink(InterfaceSink.init());
		link.setText("添加接口");
		link.addClickListener(this);
		list.addExternalLink(link);
		// 通过请求获取接口数据来确定 interface Sink 的出现。
		remoteRequest.get("Dialers", "interface=0", this);
	}

	class dummyCreateInterface implements RequestCallback {
		public dummyCreateInterface()
		{
			// TODO: 在 EXEC 模式下执行命令。
			remoteRequest.get("ConfigDialers", "interface=interface Dialer 0", this);
		}
		
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("创建拨号接口失败");
		}

		public void onResponseReceived(Request request, Response response) {
			// 这个响应会被 index 的 onResponseReceived 处理。
			remoteRequest.get("Dialers", "interface=0", index.this);
		}
		
	}
	
	public void onClick(Widget sender) {
		//	TODO: 我们在这里发起一个创建接口0的远程请求，这个请求会被返回来形成interfaceSink的数据。
		new dummyCreateInterface();
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取拨号配置数据失败");
	}

	public void onResponseReceived(Request request, Response response) {
		Document customerDom = XMLParser.parse(response.getText());
		Element customerElement = customerDom.getDocumentElement();
		NodeList interfaces = customerElement.getElementsByTagName("interface");
		for( int i = 0 ; i < interfaces.getLength() ; i++)
		{
			if( "Dialer0".equals(((Element)interfaces.item(i)).getAttribute("name")))
			{
				SinkInfo is = InterfaceSink.init(0);
				list.addSink( is );
				link.removeFromParent();
				// show(is, true);
				break;
			}
		}
	}
	/*
	public static void removeSink(String name){
		list.removeSink(list.find(name));
	}

	public static void removeInterfaceSink()
	{
		removeSink("Interface0");
		list.addExternalLink(link);
	}
	*/
}
