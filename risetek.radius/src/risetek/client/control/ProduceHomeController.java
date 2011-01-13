package risetek.client.control;

import risetek.client.model.ProduceData;
import risetek.client.view.SystemHomeView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IRisetekView;

public class ProduceHomeController extends AController {

	private ProduceHomeController(){}
	public static ProduceHomeController INSTANCE = new ProduceHomeController();
	private static RequestFactory remoteRequest = new RequestFactory("app/build");
	private ProduceData data = new ProduceData();
	public SystemHomeView view = new SystemHomeView();

	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取产品信息失败数据");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得产品信息数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}
	
	
	public static void load() {
		MessageConsole.setText("提取产品信息数据");
		remoteRequest.get(null, null, RemoteCaller);
	}

	@Override
	public void disablePrivate() {
		view.disablePrivate();
	}

	@Override
	public void enablePrivate() {
		view.enablePrivate();
	}

	@Override
	public IRisetekView getView() {
		return view;
	}

	@Override
	public void doAction(int keyCode, boolean alt) {
		// TODO Auto-generated method stub
		
	}
}
