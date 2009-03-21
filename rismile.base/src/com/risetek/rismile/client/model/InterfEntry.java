package com.risetek.rismile.client.model;

public class InterfEntry {

	private String interf;
	private String address;
	private String mask;
	
	public InterfEntry(){
		
	}
	
	public InterfEntry(String interf, String address, String mask){
		
		this.interf = interf;
		this.address = address;
		this.mask = mask;
	}
	
	public void setInterf(String interf){
		this.interf = interf;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setMask(String mask){
		this.mask = mask;
	}
	
	public String getInterf(){
		return interf;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getMask(){
		return mask;
	}
}
