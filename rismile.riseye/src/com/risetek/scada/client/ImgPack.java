package com.risetek.scada.client;

import java.io.Serializable;

public class ImgPack implements Serializable{
	private static final long serialVersionUID = 2652037641502798505L;

	public String id;
	public String seq;
	public String stamp;
	public byte[] image;
	public String GPS;
	
	public String Cookie;
	
	public ImgPack()
	{
		Cookie = Long.toString(System.currentTimeMillis());
		GPS = "nogps";
	}
}
