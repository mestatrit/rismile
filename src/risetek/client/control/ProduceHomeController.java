package risetek.client.control;

import risetek.client.model.ProduceData;
import risetek.client.view.SystemHomeView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;

public class ProduceHomeController implements RequestCallback {

	private RequestFactory remoteRequest = new RequestFactory();

	private ProduceData data = new ProduceData();

	public SystemHomeView view;

	public ProduceHomeController() {
		view = new SystemHomeView(this);
	}

	public void load() {
		MessageConsole.setText("提取产品信息数据");
		remoteRequest.get("SysStateXML", null, this);
	}

	public void onResponseReceived(Request request, Response response) {
		MessageConsole.setText("获得产品信息数据");
		data.parseXML(response.getText());
		view.render(data);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取产品信息失败数据");
	}
}
