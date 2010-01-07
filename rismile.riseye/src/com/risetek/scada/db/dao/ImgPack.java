package com.risetek.scada.db.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	
	public ImgPack(String id, String seq, String stamp, int length)
	{
		this.id = id;
		this.seq = seq;
		this.stamp = stamp;
		image = new byte[length];
	}
	
	public byte[] toBytes()
	{
		// Serialize to a byte array
	    ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
	    try {
			ObjectOutputStream out = new ObjectOutputStream(bos) ;
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	    return bos.toByteArray();
	}
	
}
