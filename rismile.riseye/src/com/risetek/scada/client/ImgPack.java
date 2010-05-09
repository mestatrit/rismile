package com.risetek.scada.client;

import java.io.Serializable;

public class ImgPack implements Serializable{
	private static final long serialVersionUID = 2652037641502798505L;

	public String id;
	public String seq;
	public String stamp;
	public byte[] image;
	public String GPS;

	public long Cookie;
	
	public ImgPack()
	{
		Cookie = System.currentTimeMillis();
	}
	
	public ImgPack clone(boolean withimg)
	{
		ImgPack p = new ImgPack();
		p.id = id;
		p.seq = seq;
		p.stamp = stamp;
		p.GPS = GPS;
		p.Cookie = Cookie;
		if( withimg )
			p.image = image;
		return p;
	}
}
