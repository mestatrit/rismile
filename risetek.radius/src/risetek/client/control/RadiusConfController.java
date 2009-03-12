package risetek.client.control;

import risetek.client.dialog.RadiusConfigAcctDialog;
import risetek.client.dialog.RadiusConfigAuthDialog;
import risetek.client.dialog.RadiusConfigSecretDialog;
import risetek.client.model.RadiusConfModel;
import risetek.client.view.RadiusConfigView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;


public class RadiusConfController implements RequestCallback {
	private RequestFactory remoteRequest = new RequestFactory();
	private String confPath = "radiuscfg";
	
	public RadiusConfigView view;
	RadiusConfModel data = new RadiusConfModel();
	
	public RadiusConfController(){
		view = new RadiusConfigView(this);
	}
	
	public void load()
	{
		remoteRequest.get(confPath, null, this);
	}
	

	public void modify(String query, RequestCallback callback){
		remoteRequest.get(confPath, query, callback);
	}
	

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("RadiusConfController 执行错误");
	}


	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data);
	}
	
	//--------- Auth 修改控制
	public class authModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			Control control = new Control();
			RadiusConfigAuthDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(data.getAuthPort());
		}

		public class Control implements ClickListener, RequestCallback {
			public RadiusConfigAuthDialog dialog = new RadiusConfigAuthDialog(view);
			public void onClick(Widget sender) {
				String value = dialog.newValueBox.getText();
				if( dialog.isValid() )
				{
					SysLog.log(value);
					modify("authport=" + value , this);
					((Button)sender).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusConfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}
	
	//--------- Acct 修改控制
	public class acctModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			Control control = new Control();
			RadiusConfigAcctDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(data.getAcctPort());
		}
		public class Control implements ClickListener, RequestCallback {
			public RadiusConfigAcctDialog dialog = new RadiusConfigAcctDialog(view);
			public void onClick(Widget sender) {
				String value = dialog.newValueBox.getText();
				SysLog.log(value);
				modify("accport=" + value , this);
				dialog.confirm.setEnabled(false);
			}

			public void onError(Request request, Throwable exception) {
				RadiusConfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
			
		}
	}
	
	//--------- Secret 修改控制
	public class secretModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			Control control = new Control();
			RadiusConfigSecretDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(data.getSecretKey());
		}

		class Control implements ClickListener, RequestCallback {
			public RadiusConfigSecretDialog dialog = new RadiusConfigSecretDialog(view);
			public void onClick(Widget sender) {
				if( dialog.isValid())
				{
					String value = dialog.newValueBox.getText();
					SysLog.log(value);
					modify("secret=" + value , this);
					((Button)sender).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RadiusConfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
			
		}
		
	}
}
