package com.risetek.rismile.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.EnablePrivateEvent;
import com.risetek.rismile.client.dialog.AddOrModifyIpDialog;
import com.risetek.rismile.client.dialog.AddRouteDialog;
import com.risetek.rismile.client.dialog.AdminDialog;
import com.risetek.rismile.client.dialog.UpfileDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.model.SystemDataModel;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.SystemView;

public class SystemController extends AController {

	private SystemController(){}
	
	public static SystemController INSTANCE = new SystemController();
	private static RequestFactory remoteRequest = new RequestFactory("forms/");

	private final static String systemAllPath = "netstate";
	private final static String addIpPath = "config_ip_second";
	private final static String modifyIpPath = "config_ip";
	private final static String delIpPath = "del_ip";
	private final static String addRouterPath = "add_router";
	private final static String delRouterPath = "del_router";
	private final static String addAdminPath = "addwebpass";
	private final static String delAdminPath = "delwebpass";
	private final static String restoreParaPath = "restore";
	private final static String resetPath = "restart";

	private SystemDataModel data = new SystemDataModel();

	public SystemView view = new SystemView();

	
	private static final RequestCallback RemoteCaller = INSTANCE.new RemoteRequestCallback();
	class RemoteRequestCallback implements RequestCallback {

		@Override
		public void onError(Request request, Throwable exception) {
			MessageConsole.setText("获得系统配置数据错误！");
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			MessageConsole.setText("获得系统配置数据");
			data.parseXML(response.getText());
			view.render(data);
			RismileContext.fireEvent(new EnablePrivateEvent());
		}
	}	
	
	public static void load() {
		MessageConsole.setText("提取系统配置");
		remoteRequest.get(systemAllPath, null, RemoteCaller);
	}

	public static void addIp(String ip, String mask, RequestCallback callback) {

		String requestData = "ip_address=" + ip;
		requestData += "&mask_address=" + mask;
		remoteRequest.get(addIpPath, requestData, callback);

	}

	public static void modifyIp(String ip, String mask, RequestCallback callback) {

		String requestData = "ip_address=" + ip;
		requestData += "&mask_address=" + mask;
		remoteRequest.get(modifyIpPath, requestData, callback);

	}

	public static void addRouter(String dest, String mask, String gate, RequestCallback callback) {

		String requestData = "ip_address=" + dest;
		requestData += "&mask_address=" + mask;
		requestData += "&router_address=" + gate;
		remoteRequest.get(addRouterPath, requestData, callback);

	}

	public static void addAdmin(String name, String password, String password2, RequestCallback callback) {

		String requestData = "new_username=" + name;
		requestData += "&new_password=" + password;
		requestData += "&new_password2=" + password2;
		remoteRequest.get(addAdminPath, requestData, callback);
	}

	public static void delAdmin(String name, String password, RequestCallback callback) {

		String requestData = "username=" + name;
		requestData += "&old_password=" + password;
		remoteRequest.get(delAdminPath, requestData, callback);

	}


	public static class RouteClickHandler implements ClickHandler , RequestCallback {
		private String ip;
		private String mask;

		public RouteClickHandler(String ip, String mask) {
			this.ip = ip;
			this.mask = mask;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip + "\n" + "掩码:" + mask)) {
				String requestData = "ip_address=" + ip + "&mask_address=" + mask;
				remoteRequest.get(delRouterPath, requestData, this);
				((Button)event.getSource()).setEnabled(false);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			RemoteCaller.onError(request, exception);
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	public static class IpClickHandler implements ClickHandler , RequestCallback {
		private String ip;

		public IpClickHandler(String ip) {
			this.ip = ip;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip)) {
				String requestData = "ip_address=" + ip;
				remoteRequest.get(delIpPath, requestData, this);
				((Button)event.getSource()).setEnabled(false);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			RemoteCaller.onError(request, exception);
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	public static class resotreClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// Window.open("forms/restart", "_blank", "");

			if (Window.confirm("是否要恢复出厂参数？\n" + "恢复出厂参数后，IP地址为192.168.0.1 。")) {
				// TODO: 如何重定位到 192.168.0.1 首页？
				remoteRequest.get(restoreParaPath, null, RemoteCaller);
			}

		}
	}

	public static class resetClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要重启设备？")) {
				// TODO: 如何重定位到首页？
				remoteRequest.get(resetPath, null, RemoteCaller);
			}
		}
	}

	public static class rmonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Window.open("forms/rmon.jnlp", "_self", "");
		}
	}


	
	public static class uploadClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			(new UpfileDialog()).show();
		}
	}

	// ----------------- 增加、修改IP地址
	public static class addIPClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.submit.setText("添加");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(AddOrModifyIpDialog.ADD);

			@Override
			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					addIp(IPConvert.long2IPString(dialog.ipBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

	}

	public static class modifyIPClickHandler implements ClickHandler {
		String ipadd;
		String ipmask;
		public modifyIPClickHandler(String ipadd, String ipmask)
		{
			this.ipadd = ipadd;
			this.ipmask = ipmask;
		}

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.submit.setText("修改");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show(ipadd, ipmask);
		}

		public class Control implements ClickHandler, RequestCallback {

			public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(AddOrModifyIpDialog.MODIFY);

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					modifyIp(IPConvert.long2IPString(dialog.ipBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// ----------------- 增加路由 ------------------------------------------
	public static class addRouterClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.submit.setText("添加");
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AddRouteDialog dialog = new AddRouteDialog();

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					addRouter(IPConvert.long2IPString(dialog.destBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()),
							IPConvert.long2IPString(dialog.gateBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
				
			}

			@Override
			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// -- 管理员维护：删除管理员
	public static class delAdminClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AdminDialog dialog = new AdminDialog(AdminDialog.DEL);

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					delAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText(), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			@Override
			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// 管理员维护 ：添加管理员
	public static class addAdminClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.submit.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AdminDialog dialog = new AdminDialog(AdminDialog.ADD);

			@Override
			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					addAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText(),
							dialog.pwdBoxSe.getText(), this);
					dialog.submit.setEnabled(false);
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				RemoteCaller.onError(request, exception);
			}

			@Override
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
