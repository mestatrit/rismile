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
import com.risetek.rismile.client.control.AController;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.view.IRisetekView;

public class RadiusConfController extends AController {
	private RadiusConfController(){}
	
	public static RadiusConfController INSTANCE = new RadiusConfController();
	
	private static RequestFactory remoteRequest = new RequestFactory();
	private final static String confPath = "radiuscfg";
	
	public RadiusConfigView view = new RadiusConfigView();
	RadiusConfModel data = new RadiusConfModel();
	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("提取认证配置数据失败");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得认证配置数据");
			data.parseXML(response.getText());
			view.render(data);
		}
	}
	
	
	public static void load()
	{
		MessageConsole.setText("提取认证配置数据");
		remoteRequest.get(confPath, null, RemoteCaller);
	}
	

	public static void modify(String query, RequestCallback callback){
		remoteRequest.get(confPath, query, callback);
	}
	
	//--------- Auth 修改控制
	public static class authModifyClickListen implements ClickHandler
	{
		public class Control implements ClickHandler, RequestCallback {
			public RadiusConfigAuthDialog dialog = new RadiusConfigAuthDialog();

			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					String value = dialog.newValueBox.getText();
					SysLog.log(value);
					modify("authport=" + value , this);
					dialog.submit.setEnabled(false);
				}
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigAuthDialog dialog = control.dialog;
			dialog.submit.addClickHandler(control);
			dialog.show(INSTANCE.data.getAuthPort());
		}
	}
	
	//--------- Acct 修改控制
	public static class acctModifyClickListen implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigAcctDialog dialog = control.dialog;
			dialog.submit.addClickHandler(control);
			dialog.show(INSTANCE.data.getAcctPort());
		}
		public class Control implements ClickHandler, RequestCallback {
			public RadiusConfigAcctDialog dialog = new RadiusConfigAcctDialog();
			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() ){
					String value = dialog.newValueBox.getText();
					SysLog.log(value);
					modify("accport=" + value , this);
					dialog.submit.setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}

		}
	}
	
	//--------- Secret 修改控制
	public static class secretModifyClickListen implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			RadiusConfigSecretDialog dialog = control.dialog;
			dialog.submit.setText("修改");
			dialog.submit.addClickHandler(control);
			dialog.show(INSTANCE.data.getSecretKey());
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
				RemoteCaller.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
			
		}
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
		view.ProcessControlKey(keyCode, alt);
	}
}
