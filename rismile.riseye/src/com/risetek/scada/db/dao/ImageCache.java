package com.risetek.scada.db.dao;

import java.util.ArrayList;
import java.util.Iterator;

import com.risetek.scada.client.ImgPack;
public class ImageCache {

	private static ArrayList<ImgPack> list = new ArrayList<ImgPack>();
	
	public static void flushImg(ImgPack img) {
		Iterator<ImgPack>  i = list.iterator();
		while( i.hasNext() )
		{
			ImgPack l = i.next();
			if( l.id.equalsIgnoreCase(img.id) && l.seq.equalsIgnoreCase(img.seq) )
			{
				img.GPS = l.GPS;
				list.remove(l);
				break;
			}
		}
		list.add(img);

		// TODO:
		// 按照什么顺序排列起来，使得终端的识别号不至于不断乱跳。
		
	}

	public static void flushGPS(String ident, String gps) {
		Iterator<ImgPack>  i = list.iterator();
		while( i.hasNext() )
		{
			ImgPack l = i.next();
			if( l.id.equalsIgnoreCase(ident) )
			{
				l.GPS = gps;
			}
		}
	}
	
	public static synchronized ArrayList<ImgPack> getGPS()
	{
		ArrayList<ImgPack> result = new ArrayList<ImgPack>();
		
		Iterator<ImgPack>  i = list.iterator();
		while( i.hasNext() )
		{
			ImgPack l = i.next();
			result.add(l.clone(false));
		}
		return result;

	}
	
	public static synchronized ArrayList<ImgPack> getList(String Ident, long Cookie) {
		ArrayList<ImgPack> result = new ArrayList<ImgPack>();
		
		Iterator<ImgPack>  i = list.iterator();
		while( i.hasNext() )
		{
			ImgPack l = i.next();
			if( Ident.equalsIgnoreCase(l.id+":"+l.seq)  && (Cookie != l.Cookie) )
				result.add(l.clone(true));
			else
				result.add(l.clone(false));
		}
		return result;
	}
	
}
