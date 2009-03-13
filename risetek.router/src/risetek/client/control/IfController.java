package risetek.client.control;

import risetek.client.dialog.AddInterfaceRouteDialog;
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

public class IfController implements RequestCallback {
	private static enum AUTH_TYPE {
		CHAPMD5, PAP, EAP, CHAPMS
	}

	private RequestFactory remoteRequest = new RequestFactory();
	final private static String ifpath = "Dialers";
	private String setPath = "ConfigDialers";

	IfModel data = new IfModel();
	// public InterfaceView2 view;
	public InterfaceView view;

	public IfController() {
		// view = new InterfaceView2(this);
		view = new InterfaceView(this);
	}

	public void getIf() {
		remoteRequest.get(ifpath, null, this);
	}

	public void load() {
		remoteRequest.get(ifpath, null, this);
	}

	private void setNAT(boolean on, RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( on )
			query += "nat outside";
		else
			query += "no nat";
		remoteRequest.get(setPath, query, callback);
	}

	private void setMPPC(boolean on, RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( on )
			query += "compress mppc";
		else
			query += "no compress mppc";
		remoteRequest.get(setPath, query, callback);
	}

	private void setRoute(boolean on, String dest, String mask ,RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( on )
			query += "route " + dest + " " + mask;
		else
			query += "no route " + dest;

		remoteRequest.get(setPath, query, callback);
	}
	
	
	private void setDefaultRoute(boolean on, RequestCallback callback) {
		setRoute(on, "0.0.0.0", "0.0.0.0" , callback);
	}
	
	private void ModifyLCPAuth(AUTH_TYPE authtype, boolean accepted,
			RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( !accepted )
			query += "no ";
		query += "ppp authentication ";

		if( authtype == AUTH_TYPE.CHAPMD5)
			query += "chap";
		else if( authtype == AUTH_TYPE.PAP)
			query += "pap";
		else if( authtype == AUTH_TYPE.EAP)
			query += "eap";
		else if( authtype == AUTH_TYPE.CHAPMS)
			query += "ms-chap";
		
		MessageConsole.setText("修改LCP授权方式");
		remoteRequest.get(setPath, query, callback);
	}

	private void setMTU(String value, RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=link mtu "+ value;
		remoteRequest.get(setPath, query, callback);
	}

	private void setLCPUser(String username, RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( "".equals(username))
			query += "no ppp username";
		else
			query +="ppp username "+username;
		remoteRequest.get(setPath, query, callback);
	}

	private void setLCPPassword(String password, RequestCallback callback) {
		String query = "interface=interface Dialer 0&commands=";
		if( "".equals(password))
			query += "no ppp password";
		else
			query +="ppp password "+ password;
			
		remoteRequest.get(setPath, query, callback);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("网络错误!");
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data.config);
	}

	public class ModifyLCPUserButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show(data.config.lcpdata.pppusername);
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyLCPUserDialog dialog = new ModifyLCPUserDialog(view);

			public Control() {
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if (dialog.isValid()) {
					setLCPUser(dialog.nameBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if (dialog.processResponse(response))
					load();
			}

		}
	}

	public class ModifyLCPPasswordButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickListener, RequestCallback {
			public ModifyLCPPasswordDialog dialog = new ModifyLCPPasswordDialog(
					view);

			public Control() {
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if (dialog.isValid()) {
					setLCPPassword(dialog.pwdBox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if (dialog.processResponse(response))
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

			public Control() {
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if (dialog.isValid()) {
					setMTU(dialog.newValuebox.getText(), this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if (dialog.processResponse(response))
					load();
			}

		}
	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyCHAPMD5Listener implements ClickListener,
			RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox) sender;
			ModifyLCPAuth(AUTH_TYPE.CHAPMD5, box.isChecked(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyPAPListener implements ClickListener, RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox) sender;
			ModifyLCPAuth(AUTH_TYPE.PAP, box.isChecked(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyNATListener implements ClickListener, RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox) sender;
			setNAT( box.isChecked(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyMPPCListener implements ClickListener, RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox) sender;
			setMPPC( box.isChecked() , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}
	// --------------------------------------------------------------------------------------------------------
	public class ModifyDefaultROUTEListener implements ClickListener, RequestCallback {
		public void onClick(Widget sender) {
			CheckBox box = (CheckBox) sender;
			setDefaultRoute( box.isChecked() , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}
	
	// 加入接口路由--------------------------------------------------------
	public class AddInterfaceRouteButtonClick implements ClickListener {
		public void onClick(Widget sender) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickListener, RequestCallback {
			public AddInterfaceRouteDialog dialog = new AddInterfaceRouteDialog(view);

			public Control() {
				dialog.confirm.addClickListener(this);
			}

			public void onClick(Widget sender) {
				if (dialog.isValid()) {
					setRoute(true, dialog.destBox.getText(), dialog.maskBox.getText() , this);
				}
			}

			public void onError(Request request, Throwable exception) {
				IfController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if (dialog.processResponse(response))
					load();
			}

		}
	}	

	// 删除接口路由--------------------------------------------------------
	public class DelInterfaceRouteButtonClick implements ClickListener , RequestCallback {
		private String dest;
		private String mask;
		public DelInterfaceRouteButtonClick(String dest, String mask)
		{
			this.dest = dest;
			this.mask = mask;
		}
		public void onClick(Widget sender) {
			MessageConsole.setText("删除接口路由");
			setRoute(false, dest, mask , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("删除接口路由完成");
			load();
		}

	}	

}
