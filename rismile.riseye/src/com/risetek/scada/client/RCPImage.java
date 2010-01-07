package com.risetek.scada.client;

import java.io.Serializable;

public class RCPImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5271068109652871230L;

	public String id;
	public String seq;
	public String stamp;
	public byte[] image;
	
}
