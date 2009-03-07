package com.risetek.rismile.system.client.model;

import java.util.List;

public class SystemAll {
	
	private String date;
	private String time;
	
	private Service service;
	
	private List<InterfEntry> interfList;
	private List<RouterEntry> routerList;
	
	public SystemAll(String date, String time, Service service,
			List<InterfEntry> interfList, List<RouterEntry>routerList){
		
		this.date = date;
		this.time = time;
		this.service = service;
		this.interfList = interfList;
		this.routerList = routerList;
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
}
