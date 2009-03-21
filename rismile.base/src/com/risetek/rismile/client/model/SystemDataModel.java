package com.risetek.rismile.client.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class SystemDataModel {
	
	private String date;
	private String time;
	
	private Service service;
	
	private List<InterfEntry> interfList;
	private List<RouterEntry> routerList;
	
	public SystemDataModel()
	{
		
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public Service getService(){
		return service;
	}
	
	public List<InterfEntry> getInterfList(){
		return interfList;
	}
	
	public List<RouterEntry> getRouteList(){
		return routerList;
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
	
	private void parseSystemAll(Element systemAllXml) {
		/*
		date = ((Element) systemAllXml.getElementsByTagName(
				"currnet_date").item(0)).getFirstChild().getNodeValue();
		time = ((Element) systemAllXml.getElementsByTagName(
				"currnet_time").item(0)).getFirstChild().getNodeValue();
		Element serviceXml = (Element) systemAllXml.getElementsByTagName(
				"service").item(0);
		service = parseService(serviceXml);
		*/

		Element networksXml = (Element) systemAllXml.getElementsByTagName("networks").item(0);
		
		interfList = parseInterf(networksXml);

		Element routerXml = (Element) systemAllXml.getElementsByTagName("ROUTER").item(0);
		routerList = parseRouter(routerXml);
		
	}
	
	public void parseXML(String text)
	{
		Document customerDom = XMLParser.parse(text);
		Element customerElement = customerDom.getDocumentElement();

		XMLParser.removeWhitespace(customerElement);

		parseSystemAll(customerElement);
	}
}
