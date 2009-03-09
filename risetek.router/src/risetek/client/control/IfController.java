package risetek.client.control;

import risetek.client.model.IfModel;
import risetek.client.view.InterfaceView2;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.http.RequestFactory;

public class IfController implements RequestCallback
{

	private RequestFactory remoteRequest = new RequestFactory();
	private String ifpath = "Dialers";
	private String setPath = "ConfigDialers";
	
	IfModel data = new IfModel();
	public InterfaceView2 view;
	
	public IfController(){
		view = new InterfaceView2(this);
	}
	
	public void getIf(){
		remoteRequest.get(ifpath, null, this);
	}
	public void setDialer(IAction action, String text)
	{
		if(text == null) return;
		String query = URL.encode(text);
		// remoteRequest.get(setPath, query, new PlainCallback(action));
	}



	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data);
	}
}
