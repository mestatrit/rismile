package com.risetek.rismile.client.model;

public class RouterEntry {

	private String dest;
	private String mask;
	private String interf;
	private String gateway;
	
	public RouterEntry(){
		
	}
	
	public RouterEntry(String dest, String mask, String interf,
			String gateway){
		
		this.dest = dest;
		this.mask = mask;
		this.interf = interf;
		this.gateway = gateway;
	}
	
	public void setDest(String dest){
		this.dest = dest;
	}
	
	public void setMask(String mask){
		this.mask = mask;
	}
	
	public void setInterf(String interf){
		this.interf = interf;
	}
	
	public void setGateway(String gateway){
		this.gateway = gateway;
	}
	
	public String getDest(){
		return dest;
	}
	
	public String getMask(){
		return mask;
	}
	
	public String getInterf(){
		return interf;
	}
	
	public String getGateway(){
		return gateway;
	}
}
