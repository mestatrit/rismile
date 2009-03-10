package risetek.client.control;

import risetek.client.dialog.ModifyLCPUserDialog;
import risetek.client.model.IfModel;
import risetek.client.view.InterfaceView2;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
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
	
	private void setLCPUser(String username, String password, RequestCallback callback)
	{
		String query = "interface=interface Dialer 0&commands=";
		remoteRequest.get(setPath, query, callback);
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
	
	public class ModifyLCPUserButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyLCPUserDialog dialog = new ModifyLCPUserDialog(view);
			public Control()
			{
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				String username = dialog.nameBox.getText();
				String password = dialog.pwdBox.getText();
				if( password.equals(dialog.pwdBoxSe.getText()))
				{
					setLCPUser(username, password, this);
					dialog.onClick(sender);
				}
				else
				{
					dialog.setMessage("执行错误！");
				}
				
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				IfController.this.onResponseReceived(request, response);
			}
			
		}
		
	}
	
}
