package com.risetek.rismile.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.risetek.rismile.client.dialog.AddOrModifyIpDialog;
import com.risetek.rismile.client.dialog.AddRouteDialog;
import com.risetek.rismile.client.dialog.AdminDialog;
import com.risetek.rismile.client.dialog.UpfileDialog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.model.SystemDataModel;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.SystemView;

public class SystemController implements RequestCallback {

	private RequestFactory remoteRequest = new RequestFactory();
	//private String systemAllPath = "SysStateXML";
	private String systemAllPath = "netstate";
	private String addIpPath = "config_ip_second";
	private String modifyIpPath = "config_ip";
	private String delIpPath = "del_ip";
	private String addRouterPath = "add_router";
	private String delRouterPath = "del_router";
	private String addAdminPath = "addwebpass";
	private String delAdminPath = "delwebpass";
	private String restoreParaPath = "restore";
	private String resetPath = "restart";

	private SystemDataModel data = new SystemDataModel();

	public SystemView view;

	public SystemController() {
		view = new SystemView(this);
	}

	public void load() {
		MessageConsole.setText("提取系统配置");  //wangx
		remoteRequest.get(systemAllPath, null, this);
	}

	public void addIp(String ip, String mask, RequestCallback callback) {

		String requestData = "ip_address=" + ip;
		requestData += "&mask_address=" + mask;
		remoteRequest.get(addIpPath, requestData, callback);

	}

	public void modifyIp(String ip, String mask, RequestCallback callback) {

		String requestData = "ip_address=" + ip;
		requestData += "&mask_address=" + mask;
		remoteRequest.get(modifyIpPath, requestData, callback);

	}

	public void addRouter(String dest, String mask, String gate, RequestCallback callback) {

		String requestData = "ip_address=" + dest;
		requestData += "&mask_address=" + mask;
		requestData += "&router_address=" + gate;
		remoteRequest.get(addRouterPath, requestData, callback);

	}

	public void addAdmin(String name, String password, String password2, RequestCallback callback) {

		String requestData = "new_username=" + name;
		requestData += "&new_password=" + password;
		requestData += "&new_password2=" + password2;
		remoteRequest.get(addAdminPath, requestData, callback);
	}

	public void delAdmin(String name, String password, RequestCallback callback) {

		String requestData = "username=" + name;
		requestData += "&old_password=" + password;
		remoteRequest.get(delAdminPath, requestData, callback);

	}

	public void onError(Request request, Throwable exception) {
		MessageConsole.setText("SystemAll 执行错误！");
	}

	public void onResponseReceived(Request request, Response response) {
		MessageConsole.setText("获得系统配置数据");  //wangx
		data.parseXML(response.getText());
		view.render(data);
	}

	public class RouteClickHandler implements ClickHandler , RequestCallback {
		private String ip;
		private String mask;

		public RouteClickHandler(String ip, String mask) {
			this.ip = ip;
			this.mask = mask;
		}

		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip + "\n" + "掩码:" + mask)) {
				String requestData = "ip_address=" + ip + "&mask_address=" + mask;
				remoteRequest.get(delRouterPath, requestData, this);
				((Button)event.getSource()).setEnabled(false);
			}
		}

		public void onError(Request request, Throwable exception) {
			SystemController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	public class IpClickHandler implements ClickHandler , RequestCallback {
		private String ip;

		public IpClickHandler(String ip) {
			this.ip = ip;
		}

		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip)) {
				String requestData = "ip_address=" + ip;
				remoteRequest.get(delIpPath, requestData, this);
				((Button)event.getSource()).setEnabled(false);
			}
		}

		public void onError(Request request, Throwable exception) {
			SystemController.this.onError(request, exception);
		}

		public void onResponseReceived(Request request, Response response) {
			load();
		}

	}

	public class resotreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			// Window.open("forms/restart", "_blank", "");

			if (Window.confirm("是否要恢复出厂参数？\n" + "恢复出厂参数后，IP地址为192.168.0.1 。")) {
				// TODO: 如何重定位到 192.168.0.1 首页？
				remoteRequest.get(restoreParaPath, null, SystemController.this);
			}

		}
	}

	public class resetClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			if (Window.confirm("是否要重启设备？")) {
				// TODO: 如何重定位到首页？
				remoteRequest.get(resetPath, null, SystemController.this);
			}
		}
	}

	public class rmonClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			Window.open("forms/rmon.jnlp", "_self", "");
		}
	}


	
	public class uploadClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			(new UpfileDialog()).show();
		}
	}

	// ----------------- 增加、修改IP地址
	public class addIPClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(AddOrModifyIpDialog.ADD);

			public void onClick(ClickEvent event) {
				if (dialog.isValid()) {
					addIp(IPConvert.long2IPString(dialog.ipBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				SystemController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}

	}

	public class modifyIPClickHandler implements ClickHandler {
		String ipadd;
		String ipmask;
		public modifyIPClickHandler(String ipadd, String ipmask)
		{
			this.ipadd = ipadd;
			this.ipmask = ipmask;
		}

		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show(ipadd, ipmask);
		}

		public class Control implements ClickHandler, RequestCallback {

			public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(AddOrModifyIpDialog.MODIFY);

			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					modifyIp(IPConvert.long2IPString(dialog.ipBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				SystemController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// ----------------- 增加路由 ------------------------------------------
	public class addRouterClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AddRouteDialog dialog = new AddRouteDialog();

			public void onClick(ClickEvent event) {
				if( dialog.isValid() )
				{
					addRouter(IPConvert.long2IPString(dialog.destBox.getText()),
							IPConvert.long2IPString(dialog.maskBox.getText()),
							IPConvert.long2IPString(dialog.gateBox.getText()), this);
					((Button)event.getSource()).setEnabled(false);
				}
				
			}

			public void onError(Request request, Throwable exception) {
				SystemController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// -- 管理员维护：删除管理员
	public class delAdminClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AdminDialog dialog = new AdminDialog(AdminDialog.DEL);

			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					delAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText(), this);
					((Button)event.getSource()).setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				SystemController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

	// 管理员维护 ：添加管理员
	public class addAdminClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			Control control = new Control();
			control.dialog.confirm.addClickHandler(control);
			control.dialog.show();
		}

		public class Control implements ClickHandler, RequestCallback {
			public AdminDialog dialog = new AdminDialog(AdminDialog.ADD);

			public void onClick(ClickEvent event) {
				if( dialog.isValid())
				{
					addAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText(),
							dialog.pwdBoxSe.getText(), this);
					dialog.confirm.setEnabled(false);
				}
			}

			public void onError(Request request, Throwable exception) {
				SystemController.this.onError(request, exception);
			}

			public void onResponseReceived(Request request, Response response) {
				if( dialog.processResponse(response))
					load();
			}
		}
	}

}
