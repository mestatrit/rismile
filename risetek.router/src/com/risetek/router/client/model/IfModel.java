package com.risetek.router.client.model;

import com.google.gwt.xml.client.Element;

public class IfModel {

	private String name;
	private Element ifElement;
	
	public IfModel(String name, Element ifElement){
		this.name = name;
		this.ifElement = ifElement;
	}
	
	public String getName(){
		return name;
	}
	public Element getIfElement(){
		return ifElement;
	}
}
