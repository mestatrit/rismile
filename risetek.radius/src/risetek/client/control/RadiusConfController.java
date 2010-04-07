package risetek.client.control;

import risetek.client.dialog.RadiusConfigAcctDialog;
import risetek.client.dialog.RadiusConfigAuthDialog;
import risetek.client.dialog.RadiusConfigSecretDialog;
import risetek.client.model.RadiusConfModel;
import risetek.client.view.RadiusConfigView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
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
		MessageConsole.setText("提取认证配置数据");
		remoteRequest.get(confPath, null, this);
	}
	

	public void modify(String query, RequestCallback callback){
		remoteRequest.get(confPath, query, callback);
	}
	

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("提取认证配置数据失败");
	}


	public void onResponseReceived(Request request, Response response)
	{
		MessageConsole.setText("获得认证配置数据");
		data.parseXML(response.getText());
		view.render(data);
	}
	
	//--------- Auth 修改控制
	public class authModifyClickListen implements ClickHandler
	{
		public class Control implements ClickHandler, RequestCallback {
			public RadiusConfigAuthDialog dialog = new RadiusConfigAuthDialog();

			public void onError(Request request, Throwable exception) {
				RadiusConfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}

			@Override
			public void onClick(ClickEvent event) {
				String value = dialog.newValueBox.getText();
				if( dialog.isValid() )
				{
					SysLog.log(value);
					modify("authport=" + value , this);
					((Button)event.getSource()).setEnabled(false);
				}
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigAuthDialog dialog = control.dialog;
			dialog.confirm.addClickHandler(control);
			dialog.show(data.getAuthPort());
		}
	}
	
	//--------- Acct 修改控制
	public class acctModifyClickListen implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigAcctDialog dialog = control.dialog;
			dialog.confirm.addClickHandler(control);
			dialog.show(data.getAcctPort());
		}
		public class Control implements ClickHandler, RequestCallback {
			public RadiusConfigAcctDialog dialog = new RadiusConfigAcctDialog();
			@Override
			public void onClick(ClickEvent event) {
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
	public class secretModifyClickListen implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigSecretDialog dialog = control.dialog;
			dialog.confirm.addClickHandler(control);
			dialog.show(data.getSecretKey());
		}

		class Control implements ClickHandler, RequestCallback {
			public RadiusConfigSecretDialog dialog = new RadiusConfigSecretDialog();
			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					String value = dialog.newValueBox.getText();
					SysLog.log(value);
					modify("secret=" + value , this);
					((Button)event.getSource()).setEnabled(false);
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
