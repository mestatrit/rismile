package risetek.client.control;

import risetek.client.dialog.ModifyLCPPasswordDialog;
import risetek.client.dialog.ModifyLCPUserDialog;
import risetek.client.dialog.ModifyMTUDialog;
import risetek.client.model.IfModel;
import risetek.client.view.InterfaceView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;

public class IfController implements RequestCallback
{
	private static int AUTH_CHAPMD5 = 0;
	
	private RequestFactory remoteRequest = new RequestFactory();
	private String ifpath = "Dialers";
	private String setPath = "ConfigDialers";
	
	IfModel data = new IfModel();
//	public InterfaceView2 view;
	public InterfaceView view;
	
	public IfController(){
//		view = new InterfaceView2(this);
		view = new InterfaceView(this);
	}
	
	public void getIf(){
		remoteRequest.get(ifpath, null, this);
	}

	public void load(){
		remoteRequest.get(ifpath, null, this);
	}
	
	private void ModifyLCPAuth(int authtype, boolean accepted, RequestCallback callback)
	{
		String query = "interface=interface Dialer 0&commands=";
		remoteRequest.get(setPath, query, callback);
	}
	
	private void setLCPUser(String username, RequestCallback callback)
	{
		String query = "interface=interface Dialer 0&commands=";
		remoteRequest.get(setPath, query, callback);
	}

	private void setLCPPassword(String username, RequestCallback callback)
	{
		String query = "interface=interface Dialer 0&commands=";
		remoteRequest.get(setPath, query, callback);
	}
	
	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("网络错误!");
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data);
	}
	
	public class ModifyLCPUserButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show(data.config.lcpdata.pppusername);
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyLCPUserDialog dialog = new ModifyLCPUserDialog(view);
			public Control()
			{
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if( dialog.isValid() )
				{
					setLCPUser(dialog.nameBox.getText(), this);
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
	
	public class ModifyLCPPasswordButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyLCPPasswordDialog dialog = new ModifyLCPPasswordDialog(view);
			public Control()
			{
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if( dialog.isValid() )
				{
					setLCPPassword(dialog.pwdBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if(dialog.processResponse(response))
					load();
			}
			
		}
	}

	public class ModifyMTUButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show(Integer.toString(data.config.linkdata.mtu));
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyMTUDialog dialog = new ModifyMTUDialog(view);
			public Control()
			{
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if( dialog.isValid() )
				{
					setLCPPassword(dialog.newValuebox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if(dialog.processResponse(response))
					load();
			}
			
		}
	}

	public class ModifyCHAPMD5Listener implements ClickListener , RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox)sender;
			ModifyLCPAuth(AUTH_CHAPMD5, ( box.isChecked() != data.config.lcpdata.accept_chap ), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			IfController.this.onResponseReceived(request, response);
		}

	}

}
