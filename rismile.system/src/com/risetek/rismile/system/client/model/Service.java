package com.risetek.rismile.system.client.model;

public class Service {

	private String name;
	private String status;
	private String version;
	
	public Service(){
		
	}
	
	public Service(String name, String status, String version){
		this.name = name;
		this.status = status;
		this.version = version;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public void setVersion(String version){
		this.version = version;
	}
	
	public String getName(){
		return name;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getVersion(){
		return version;
	}
}
