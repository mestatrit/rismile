package risetek.client.control;

import risetek.client.dialog.RadiusConfigAcctDialog;
import risetek.client.dialog.RadiusConfigAuthDialog;
import risetek.client.dialog.RadiusConfigSecretDialog;
import risetek.client.model.RadiusConfModel;
import risetek.client.view.RadiusConfigView;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.SysLog;
import com.risetek.rismile.client.http.RequestFactory;


public class RadiusConfController implements RequestCallback {
	private RequestFactory remoteRequest = new RequestFactory();
	private String confPath = "radiuscfg";
	
	public RadiusConfigView view;
	RadiusConfModel radiusConfModel = new RadiusConfModel();
	
	
	public RadiusConfController(){
		view = new RadiusConfigView(this);
	}
	
	public void getConfAll(){
		remoteRequest.get(confPath, null, this);
	}
	public void modify(String query, RequestCallback callback){
		remoteRequest.get(confPath, query, callback);
	}
	

	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub
		
	}


	public void onResponseReceived(Request request, Response response) {
		String text = response.getText();
		radiusConfModel.parseXML(text);
		view.render(radiusConfModel);
	}
	
	//--------- Auth 修改控制
	public class authModifyControl implements ClickListener, RequestCallback {
		public RadiusConfigAuthDialog dialog = new RadiusConfigAuthDialog(view);
		public void onClick(Widget sender) {
			String value = dialog.newValueBox.getText();
			SysLog.log(value);
			modify("setAuthPort", this);
		}

		public void onError(Request request, Throwable exception) {
			// TODO Auto-generated method stub
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			getConfAll();
		}
		
	}
	
	public class authModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			authModifyControl control = new authModifyControl();
			RadiusConfigAuthDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(radiusConfModel.getAuthPort());
		}
	}
	
	//--------- Acct 修改控制
	public class acctModifyControl implements ClickListener, RequestCallback {
		public RadiusConfigAcctDialog dialog = new RadiusConfigAcctDialog(view);
		public void onClick(Widget sender) {
			String value = dialog.newValueBox.getText();
			SysLog.log(value);
			modify("setAcctPort", this);
		}

		public void onError(Request request, Throwable exception) {
			// TODO Auto-generated method stub
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			getConfAll();
		}
		
	}
	
	public class acctModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			acctModifyControl control = new acctModifyControl();
			RadiusConfigAcctDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(radiusConfModel.getAcctPort());
		}
	}
	
	//--------- Secret 修改控制
	class secretModifyControl implements ClickListener, RequestCallback {
		public RadiusConfigSecretDialog dialog = new RadiusConfigSecretDialog(view);
		public void onClick(Widget sender) {
			String value = dialog.newValueBox.getText();
			SysLog.log(value);
			modify("setSecret", this);
		}

		public void onError(Request request, Throwable exception) {
			// TODO Auto-generated method stub
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			getConfAll();
		}
		
	}
	
	public class secretModifyClickListen implements ClickListener
	{
		public void onClick(Widget sender) {
			secretModifyControl control = new secretModifyControl();
			RadiusConfigSecretDialog dialog = control.dialog;
			dialog.confirm.addClickListener(control);
			dialog.show(radiusConfModel.getSecretKey());
		}
	}
}
