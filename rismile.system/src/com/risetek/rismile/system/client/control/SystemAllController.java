package com.risetek.rismile.system.client.control;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.SysLog;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.client.utils.IPConvert;
import com.risetek.rismile.client.utils.Validity;
import com.risetek.rismile.system.client.dialog.AddOrModifyIpDialog;
import com.risetek.rismile.system.client.dialog.AddRouteDialog;
import com.risetek.rismile.system.client.dialog.AdminDialog;
import com.risetek.rismile.system.client.dialog.UpfileDialog;
import com.risetek.rismile.system.client.model.SystemDataModel;
import com.risetek.rismile.system.client.view.SystemView;

public class SystemAllController implements RequestCallback {

	private RequestFactory remoteRequest = new RequestFactory();
	private String systemAllPath = "SysStateXML";
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
	
	public SystemAllController(){
		view = new SystemView(this);
	}
	
	public void getSystemAll(){
	
		remoteRequest.get(systemAllPath, null, this);
	
	}
	
	public void addIp(String ip, String mask){
		
		String requestData = "ip_address="+ip;
		requestData += "&mask_address="+mask;
		remoteRequest.get(addIpPath, requestData, this);
	
	}
	public void modifyIp(String ip, String mask){
		
		String requestData = "ip_address="+ip;
		requestData += "&mask_address="+mask;
		remoteRequest.get(modifyIpPath, requestData, this);
	
	}
	public void delIp(String ip){
		
		String requestData = "ip_address="+ip;
		remoteRequest.get(delIpPath, requestData, this);
	
	}
	
	public void addRouter(String dest, String mask, String gate){
		
		String requestData = "ip_address="+dest;
		requestData += "&mask_address="+mask;
		requestData += "&router_address="+gate;
		remoteRequest.get(addRouterPath, requestData, this);
		
	}
	public void delRouter(String ip, String mask){
		
		String requestData = "ip_address="+ip+"&mask_address="+mask;
		remoteRequest.get(delRouterPath, requestData, this);
	
	}
	public void addAdmin(String name, String password, String password2){
		
		String requestData = "new_username="+name;
		requestData += "&new_password="+password;
		requestData += "&new_password2="+password2;
		remoteRequest.get(addAdminPath, requestData, this);
	}
	public void delAdmin(String name, String password){
		
		String requestData = "username="+name;
		requestData += "&old_password="+password;
		remoteRequest.get(delAdminPath, requestData, this);
		
	}
	public void restorePara(){
		
		remoteRequest.get(restoreParaPath, null, this);
		
	}
	public void reset(){
		remoteRequest.get(resetPath, null, this);
	}
	
	public void onError(Request request, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	public void onResponseReceived(Request request, Response response) {
		String text = response.getText();
		data.parseXML(text);
		view.render(data);
	}

	public class RouteClickListener implements ClickListener {
		private String ip;
		private String mask;

		public RouteClickListener(String ip, String mask) {
			this.ip = ip;
			this.mask = mask;
		}

		public void onClick(Widget sender) {
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip + "\n" + "掩码:" + mask)) {
				delRouter(ip, mask );
				((Button) sender).setEnabled(false);
			}
		}

	}

	public class IpClickListener implements ClickListener {
		private String ip;

		public IpClickListener(String ip) {
			this.ip = ip;
		}

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub

			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip)) {

				delIp(ip );
				((Button) sender).setEnabled(false);
			}
		}

	}

	public class resotreClickListener implements ClickListener {
	
		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			// Window.open("forms/restart", "_blank", "");

			if (Window.confirm("是否要恢复出厂参数？\n"
					+ "恢复出厂参数后，IP地址为192.168.0.1 。")) {
				//view.paraButton.setEnabled(false);
				restorePara();
			}

		}
	}

	public class resetClickListener implements ClickListener {
		public void onClick(Widget sender) {
			if (Window.confirm("是否要重启设备？")) {
				//view.restartButton.setEnabled(false);
				reset();
			}
		}
	}
	
	public class uploadClickListener implements ClickListener {

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			(new UpfileDialog(view)).show();
		}
	}
	
	
	
	//----------------- 增加、修改IP地址
	public class addIPClickListener implements ClickListener {

		public void onClick(Widget sender) {
			AddIpControl control = new AddIpControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}

	}
	
	public class AddIpControl implements ClickListener, RequestCallback {
		public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(view, AddOrModifyIpDialog.ADD);
		public void onClick(Widget sender) {
			String text = dialog.ipBox.getText();
			String check = Validity.validIpAddress(text);
			if (null != check){
				dialog.ipBox.setFocus(true);
				Window.alert("IP"+check);
				return;
			}
			text = dialog.maskBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.maskBox.setFocus(true);
				Window.alert("子网掩码"+check);
				return;
			}
			addIp(IPConvert.long2IPString(dialog.ipBox.getText()), 
					IPConvert.long2IPString(dialog.maskBox.getText()));
			dialog.confirm.setEnabled(false);
			//unmask();
			//hide();
					
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			//load();
		}
	}	

	
	public class modifyIPClickListener implements ClickListener {

		public void onClick(Widget sender) {
			modifyIpControl control = new modifyIpControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}

	}
	
	public class modifyIpControl implements ClickListener, RequestCallback {
		public AddOrModifyIpDialog dialog = new AddOrModifyIpDialog(view, AddOrModifyIpDialog.MODIFY);
		public void onClick(Widget sender) {
			String text = dialog.ipBox.getText();
			String check = Validity.validIpAddress(text);
			if (null != check){
				dialog.ipBox.setFocus(true);
				Window.alert("IP"+check);
				return;
			}
			text = dialog.maskBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.maskBox.setFocus(true);
				Window.alert("子网掩码"+check);
				return;
			}
			modifyIp(IPConvert.long2IPString(dialog.ipBox.getText()), 
				IPConvert.long2IPString(dialog.maskBox.getText()));
			dialog.confirm.setEnabled(false);
			//unmask();
			//hide();
					
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			//load();
		}
	}	
	
	//----------------- 增加路由 ------------------------------------------
	public class addRouterClickListener implements ClickListener {
		public void onClick(Widget sender) {
			addRouterControl control = new addRouterControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}
	}

	public class addRouterControl implements ClickListener, RequestCallback {
		public AddRouteDialog dialog = new AddRouteDialog(view);
		public void onClick(Widget sender) {

			// TODO Auto-generated method stub
			String text = dialog.destBox.getText();
			String check = Validity.validIpAddress(text);
			if (null != check){
				dialog.destBox.setFocus(true);
				Window.alert("目的IP"+check);
				return;
			}
			text = dialog.maskBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.maskBox.setFocus(true);
				Window.alert("掩码"+check);
				return;
			}
			/*text = interfaceBox.getText();
			check = Validity.validRouteInterface(text);
			if (null != check){
				Window.alert(check);
				return;
			}*/
			text = dialog.gateBox.getText();
			check = Validity.validIpAddress(text);
			if (null != check){
				dialog.gateBox.setFocus(true);
				Window.alert("网关"+check);
				return;
			}
			
			addRouter(IPConvert.long2IPString(dialog.destBox.getText()), 
					IPConvert.long2IPString(dialog.maskBox.getText()),  
					IPConvert.long2IPString(dialog.gateBox.getText()));
			dialog.confirm.setEnabled(false);
			//unmask();
			//hide();
					
		}

		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			//load();
		}
	}

	// -- 管理员维护
	public class delAdminClickListener implements ClickListener {

		public void onClick(Widget sender) {
			delAdminControl control = new delAdminControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}
	}
	
	
	public class delAdminControl implements ClickListener, RequestCallback {
		public AdminDialog dialog = new AdminDialog(view, AdminDialog.DEL);
		public void onClick(Widget sender) {
			String text = dialog.nameBox.getText();
			String check = Validity.validAdminName(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			text = dialog.pwdBox.getText();
			check = Validity.validPassword(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			delAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText());
			dialog.confirm.setEnabled(false);
			//unmask();
			//hide();
					
		}
		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			//load();
		}
	}		

	public class addAdminClickListener implements ClickListener {

		public void onClick(Widget sender) {
			addAdminControl control = new addAdminControl();
			control.dialog.confirm.addClickListener(control);
			control.dialog.show();
		}
	}
	
	public class addAdminControl implements ClickListener, RequestCallback {
		public AdminDialog dialog = new AdminDialog(view, AdminDialog.DEL);
		public void onClick(Widget sender) {
			String text = dialog.nameBox.getText();
			String check = Validity.validAdminName(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			text = dialog.pwdBox.getText();
			check = Validity.validPassword(text);
			if (null != check){
				Window.alert(check);
				return;
			}
			if( !dialog.pwdBox.getText().equals(dialog.pwdBoxSe.getText())){
				dialog.pwdBox.setText("");
				dialog.pwdBoxSe.setText("");
				dialog.pwdBox.setFocus(true);
				Window.alert("两次输入的密码不符,请重新输入!");
				return;
			}
			addAdmin(dialog.nameBox.getText(), dialog.pwdBox.getText(), dialog.pwdBoxSe.getText());
			dialog.confirm.setEnabled(false);
			//unmask();
			//hide();
		}
		public void onError(Request request, Throwable exception) {
			
		}

		public void onResponseReceived(Request request, Response response) {
			view.unmask();
			dialog.hide();
			SysLog.log("remote execute");
			//load();
		}
	}		
	
}
