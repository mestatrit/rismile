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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.MessageConsole;

public class IfController implements RequestCallback {
	
	int DialerUnit;
	
	private static enum AUTH_TYPE {
		CHAPMD5, PAP, EAP, CHAPMS
	}

	private RequestFactory remoteRequest = new RequestFactory();
	private final String setPath = "ConfigDialers";
	String interfaceName = "interface=interface Dialer ";
	
	
	IfModel data;
	public InterfaceView view;

	public IfController(int unit) {
		DialerUnit = unit;
		data = new IfModel(unit);
		interfaceName += unit;
		view = new InterfaceView(this);
	}

	public void load() {
		remoteRequest.get("Dialers", "interface="+DialerUnit, this);
	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("网络错误!");
	}

	public void onResponseReceived(Request request, Response response) {
		data.parseXML(response.getText());
		view.render(data.config);
	}
	
	private void setNAT(boolean on, RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if( on )
			query += "nat outside";
		else
			query += "no nat";
		remoteRequest.get(setPath, query, callback);
	}

	private void setMPPC(boolean on, RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if( on )
			query += "compress mppc";
		else
			query += "no compress mppc";
		remoteRequest.get(setPath, query, callback);
	}

	private void setRoute(boolean on, String dest, String mask ,RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if( on )
			query += "route " + dest + " " + mask;
		else
			query += "no route " + dest;

		remoteRequest.get(setPath, query, callback);
	}
	
	
	private void setDefaultRoute(boolean on, RequestCallback callback) {
		setRoute(on, "0.0.0.0", "0.0.0.0" , callback);
	}

	private void setDefaultPhys(boolean on, RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if(on)
			query += data.defaultlink;
		else
			query += "no link";
		
		remoteRequest.get(setPath, query, callback);
	}
	
	private void ModifyLCPAuth(AUTH_TYPE authtype, boolean accepted,
			RequestCallback callback) {
		String query = interfaceName + "&commands=";
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
		String query = interfaceName + "&commands=link mtu "+ value;
		remoteRequest.get(setPath, query, callback);
	}

	private void setLCPUser(String username, RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if( "".equals(username))
			query += "no ppp username";
		else
			query +="ppp username "+username;
		remoteRequest.get(setPath, query, callback);
	}

	private void setLCPPassword(String password, RequestCallback callback) {
		String query = interfaceName + "&commands=";
		if( "".equals(password))
			query += "no ppp password";
		else
			query +="ppp password "+ password;
			
		remoteRequest.get(setPath, query, callback);
	}

	public class ModifyLCPUserButtonClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.show(data.config.lcpdata.pppusername);
		}

		public class Control implements ClickHandler, RequestCallback {
			public ModifyLCPUserDialog dialog = new ModifyLCPUserDialog();

			public Control() {
				dialog.confirm.addClickHandler(this);
			}

			public void onClick(ClickEvent event) {
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

	public class ModifyLCPPasswordButtonClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public ModifyLCPPasswordDialog dialog = new ModifyLCPPasswordDialog();

			public Control() {
				dialog.confirm.addClickHandler(this);
			}

			public void onClick(ClickEvent event) {
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

	public class ModifyMTUButtonClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.show(Integer.toString(data.config.linkdata.mtu));
		}

		public class Control implements ClickHandler, RequestCallback {
			public ModifyMTUDialog dialog = new ModifyMTUDialog();

			public Control() {
				dialog.confirm.addClickHandler(this);
			}

			public void onClick(ClickEvent event) {
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
	public class ModifyCHAPMD5Listener implements ClickHandler,
			RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			ModifyLCPAuth(AUTH_TYPE.CHAPMD5, box.getValue(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyPAPListener implements ClickHandler, RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			ModifyLCPAuth(AUTH_TYPE.PAP, box.getValue(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyNATListener implements ClickHandler, RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			setNAT( box.getValue(), this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// --------------------------------------------------------------------------------------------------------
	public class ModifyMPPCListener implements ClickHandler, RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			setMPPC( box.getValue() , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}
	// ------------------------------------------------------------------
	public class ModifyDefaultROUTEListener implements ClickHandler, RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			setDefaultRoute( box.getValue() , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}
	
	// ------------------------------------------------------------------
	public class ModifyDefaultPhysListener implements ClickHandler, RequestCallback {
		public void onClick(ClickEvent event) {
			CheckBox box = (CheckBox) event.getSource();
			setDefaultPhys( box.getValue() , this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// 加入接口路由--------------------------------------------------------
	public class AddInterfaceRouteButtonClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AddInterfaceRouteDialog dialog = new AddInterfaceRouteDialog();

			public Control() {
				dialog.confirm.addClickHandler(this);
			}

			public void onClick(ClickEvent event) {
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
	public class DelInterfaceRouteButtonClick implements ClickHandler , RequestCallback {
		private String dest;
		private String mask;
		public DelInterfaceRouteButtonClick(String dest, String mask)
		{
			this.dest = dest;
			this.mask = mask;
		}
		public void onClick(ClickEvent event) {
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
	
	// 删除接口的操作
	/*
	public class RemoveInterfaceClick implements ClickHandler {
		public void onClick(ClickEvent event) {
			index.removeInterfaceSink();
		}
	}
	*/

	// 断开连接  -----------------------------------------------------------------------
	public class DisconnectListener implements ClickHandler,
			RequestCallback {
		public void onClick(ClickEvent event) {
			remoteRequest.get("websexec", "commands=disconnect Dialer "+DialerUnit, this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}
	}

	// 建立连接  -----------------------------------------------------------------------
	public class ConnectListener implements ClickHandler,
			RequestCallback {
		public void onClick(ClickEvent event) {
			remoteRequest.get("websexec", "commands=connect Dialer "+DialerUnit, this);
		}

		public void onError(Request request, Throwable exception) {
			IfController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	// 更新数据  -----------------------------------------------------------------------
	public class ReloadStatListener implements ClickHandler {
		public void onClick(ClickEvent event) {
			load();
		}
	}

}
