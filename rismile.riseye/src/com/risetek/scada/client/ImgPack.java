package com.risetek.scada.client;

import java.io.Serializable;

public class ImgPack implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2652037641502798505L;
	/**
	 * 
	 */
	public String id;
	public String seq;
	public String stamp;
	public byte[] image;

	public ImgPack()
	{
		
	}

	public ImgPack(String id, String seq, String stamp, int length)
	{
		this.id = id;
		this.seq = seq;
		this.stamp = stamp;
		image = new byte[length];
	}

	public ImgPack(String id, String seq, String stamp, byte[] img)
	{
		this.id = id;
		this.seq = seq;
		this.stamp = stamp;
		image = img;
	}

}
