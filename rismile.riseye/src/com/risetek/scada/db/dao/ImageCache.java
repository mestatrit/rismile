package com.risetek.scada.db.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.risetek.scada.client.ImgPack;
public class ImageCache {

	public static ArrayList<ImgPack> list = new ArrayList<ImgPack>();
	
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
	
	static ImgPack img_stub;
	// http://forums.smartclient.com/showthread.php?t=5258
	static {
		try {
			byte[] cachebuf = null;
			FileInputStream imgfile = new FileInputStream("image/p3.jpg");
			try {
				byte[] buf = new byte[10240];
				int len;
				while ((len = imgfile.read(buf)) > 0) {
					int oldlen;
					if(cachebuf == null )
						oldlen = 0;
					else
						oldlen = cachebuf.length;
					
					
					byte[] newbuf = new byte[oldlen + len];
					if( oldlen > 0)
						System.arraycopy(cachebuf, 0, newbuf, 0, oldlen);
					
					System.arraycopy(buf, 0, newbuf, oldlen, len);
					
					cachebuf = newbuf;
				}				
				
				imgfile.close();
				img_stub = new ImgPack();
				img_stub.id = "img_stub";
				img_stub.seq = "seq";
				img_stub.stamp = "stamp";
				img_stub.image = cachebuf;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
	
//	public static synchronized ArrayList<ImgPack> getList(String Ident, String Cookie) {
	public static synchronized ArrayList<ImgPack> getList(String Ident, long Cookie) {
		ArrayList<ImgPack> result = new ArrayList<ImgPack>();
		
		Iterator<ImgPack>  i = list.iterator();
		while( i.hasNext() )
		{
			ImgPack l = i.next();
//			if( Ident.equalsIgnoreCase(l.id+":"+l.seq)  && !Cookie.equalsIgnoreCase(l.Cookie) )
			if( Ident.equalsIgnoreCase(l.id+":"+l.seq)  && (Cookie != l.Cookie) )
				result.add(l.clone(true));
			else
				result.add(l.clone(false));
		}
		return result;
	}
	
}
