package com.risetek.rismile.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.EnablePrivateEvent;
import com.risetek.rismile.client.dialog.PrivateDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.XMLDataParse;
import com.risetek.rismile.client.view.IRisetekView;

public class EntryController extends AController {

	private EntryController(){}
	
	public static EntryController INSTANCE = new EntryController();
	
	private static RequestFactory remoteRequest = new RequestFactory();
	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();

	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			if( XMLDataParse.getElementNumber(response.getText(), "OK") > 0 ) { 
				Entry.login = true;
				Entry.enable.setText("退出特权(L)");
				RismileContext.fireEvent(new EnablePrivateEvent());
			} else {
				PrivateControl control = new PrivateControl();
				control.dialog.submit.setText("登录");
				control.dialog.submit.addClickHandler(control);
				control.dialog.show();
			}
		}
	}
	
	public class PrivateControl implements ClickHandler, RequestCallback {
		
		public PrivateDialog dialog = new PrivateDialog();

		public void onClick(ClickEvent event) {
			if( dialog.isValid())
			{
				login(dialog.pwdBox.getText(), this);
			}
		}
		public void onError(Request request, Throwable exception) {
		}
		public void onResponseReceived(Request request, Response response) {
			if( dialog.processResponse(response))
			{
			
			if( XMLDataParse.getElementNumber(response.getText(), "OK") > 0 ) {
				Entry.login = true;
				Entry.enable.setText("退出特权(L)");
				RismileContext.fireEvent(new EnablePrivateEvent());
			} else {
				PrivateControl control = new PrivateControl();
				control.dialog.submit.setText("登录");
				control.dialog.submit.addClickHandler(control);
				control.dialog.show();
			}
			}
		}
		public void login(String password, RequestCallback callback) {
			String requestData = "password=" + password;
			remoteRequest.get(checkPath, requestData, callback);

		}
	}

	private final static String checkPath = "penable";
	
	public static void load() {
		MessageConsole.setText("检测权限");
		remoteRequest.get(checkPath, null, RemoteCaller);
	}
	
	@Override
	public void disablePrivate() {
	}

	@Override
	public void enablePrivate() {
	}

	@Override
	public void doAction(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IRisetekView getView() {
		// TODO Auto-generated method stub
		return null;
	}
}