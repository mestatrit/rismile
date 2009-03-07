package com.risetek.rismile.system.client.control;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.ModelCallback;
import com.risetek.rismile.client.control.PlainCallback;
import com.risetek.rismile.client.http.RequestFactory;
import com.risetek.rismile.system.client.model.InterfEntry;
import com.risetek.rismile.system.client.model.RouterEntry;
import com.risetek.rismile.system.client.model.Service;
import com.risetek.rismile.system.client.model.SystemAll;

public class SystemAllController {

	private RequestFactory objectFactory;
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
	
	public SystemAllController(){
		
		objectFactory = RequestFactory.getInstance();
	
	}
	
	public void getSystemAll(IAction action){
	
		objectFactory.get(systemAllPath, null, new SystemAllCallback(action));
	
	}
	
	public void addIp(String ip, String mask, IAction action){
		
		String requestData = "ip_address="+ip;
		requestData += "&mask_address="+mask;
		objectFactory.get(addIpPath, requestData, new PlainCallback(action));
	
	}
	public void modifyIp(String ip, String mask, IAction action){
		
		String requestData = "ip_address="+ip;
		requestData += "&mask_address="+mask;
		objectFactory.get(modifyIpPath, requestData, new PlainCallback(action));
	
	}
	public void delIp(String ip, IAction action){
		
		String requestData = "ip_address="+ip;
		objectFactory.get(delIpPath, requestData, new PlainCallback(action));
	
	}
	
	public void addRouter(String dest, String mask, String gate, IAction action){
		
		String requestData = "ip_address="+dest;
		requestData += "&mask_address="+mask;
		requestData += "&router_address="+gate;
		objectFactory.get(addRouterPath, requestData, new PlainCallback(action));
		
	}
	public void delRouter(String ip, String mask, IAction action){
		
		String requestData = "ip_address="+ip+"&mask_address="+mask;
		objectFactory.get(delRouterPath, requestData, new PlainCallback(action));
	
	}
	public void addAdmin(String name, String password, String password2, IAction action){
		
		String requestData = "new_username="+name;
		requestData += "&new_password="+password;
		requestData += "&new_password2="+password2;
		objectFactory.get(addAdminPath, requestData, new PlainCallback(action));
	}
	public void delAdmin(String name, String password, IAction action){
		
		String requestData = "username="+name;
		requestData += "&old_password="+password;
		objectFactory.get(delAdminPath, requestData, new PlainCallback(action));
		
	}
	public void restorePara(IAction action){
		
		objectFactory.get(restoreParaPath, null, new PlainCallback(action));
		
	}
	public void reset(IAction action){
		objectFactory.get(resetPath, null, new PlainCallback(action));
	}
	
	class SystemAllCallback extends ModelCallback{

		public SystemAllCallback(IAction action) {
			super(action);
			// TODO Auto-generated constructor stub
		}

		public void onResponse(Response response) {
			// TODO Auto-generated method stub
			if(response.getStatusCode() == 200){
				
				Document customerDom = XMLParser.parse(response.getText());
				Element customerElement = customerDom.getDocumentElement();

				XMLParser.removeWhitespace(customerElement);

				NodeList error = customerElement.getElementsByTagName("ERROR");

				if (error.getLength() > 0
						&& error.item(0).getNodeName().equals("ERROR")) {
				
					action.onFailure(error.item(0).getFirstChild().getNodeValue());
				}else {
					SystemAll systemAll = parseSystemAll(customerElement);
					action.onSuccess(systemAll);
				}
			}else{
				action.onUnreach("请求失败！返回："+response.getStatusText()+"。");
			}
		}
		
	}
	private SystemAll parseSystemAll(Element systemAllXml) {
		
		String date = ((Element) systemAllXml.getElementsByTagName(
				"currnet_date").item(0)).getFirstChild().getNodeValue();
		String time = ((Element) systemAllXml.getElementsByTagName(
				"currnet_time").item(0)).getFirstChild().getNodeValue();

		Element serviceXml = (Element) systemAllXml.getElementsByTagName(
				"service").item(0);
		Service service = parseService(serviceXml);

		Element networksXml = (Element) systemAllXml.getElementsByTagName(
				"networks").item(0);
		
		List<InterfEntry> interfList = parseInterf(networksXml);

		Element routerXml = (Element) systemAllXml.getElementsByTagName(
				"ROUTER").item(0);
		List<RouterEntry> routerList = parseRouter(routerXml);
		
		SystemAll systemAll = new SystemAll(date, time, service, interfList, routerList);
		return systemAll;
	}

	private Service parseService(Element serviceXml) {
		Element radius = (Element) serviceXml.getElementsByTagName("radius").item(
				0);
		
		String name = "认证服务";
		String status = ((Element) radius.getElementsByTagName(
				"status").item(0)).getFirstChild().getNodeValue();
		String version = ((Element) radius.getElementsByTagName(
				"version").item(0)).getFirstChild().getNodeValue();
		Service service = new Service(name, status, version);
		return service;
	}

	private List<InterfEntry> parseInterf(Element networksXml) {

		NodeList interfaces = networksXml.getElementsByTagName("interface");
		List<InterfEntry> interfList = new ArrayList<InterfEntry>();
		
		for (int i = 0; i < interfaces.getLength(); i++) {
			Element e = (Element) interfaces.item(i);
			if (e.getAttribute("name").equals("lo0")) {
				continue;
			}
			String interf = e.getAttribute("name");
			String address = ((Element) e.getElementsByTagName("ip_address")
					.item(0)).getFirstChild().getNodeValue();
			String mask = ((Element) e.getElementsByTagName("ip_mask")
					.item(0)).getFirstChild().getNodeValue();
			
			InterfEntry interfEntry = new InterfEntry(interf, address, mask);
			interfList.add(interfEntry);
		}
		return interfList;
	}

	private List<RouterEntry> parseRouter(Element routerXml) {
		
		List<RouterEntry> routerList = new ArrayList<RouterEntry>();
		NodeList items = routerXml.getElementsByTagName("ITEM");
		for (int i = 0; i < items.getLength(); i++) {
			Element e = (Element) items.item(i);
			String dest = ((Element) e.getElementsByTagName("dst_addr").item(0))
					.getFirstChild().getNodeValue();
			String mask = ((Element) e.getElementsByTagName("msk_addr").item(0))
					.getFirstChild().getNodeValue();
			String interf = ((Element) e.getElementsByTagName("interface").item(0)).getFirstChild().getNodeValue();
			
			String gateway = ((Element) e.getElementsByTagName("gate").item(0)).getFirstChild().getNodeValue();
			
			RouterEntry routerEntry = new RouterEntry(dest, mask, interf, gateway);
			routerList.add(routerEntry);
			
		}
		return routerList;
	}
}
