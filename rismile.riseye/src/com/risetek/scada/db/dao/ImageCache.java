package com.risetek.scada.db.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.gwt.core.client.GWT;
public class ImageCache {

	public static ImageCache imageCache = new ImageCache();

	private Cache images;
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
				
/*				
				img_stub = new ImgPack("img_stub", "seq", "stamp", cachebuf);
*/
/*
				img_stub = new ImgPack("img_stub", "seq", "stamp", cachebuf.length);
				System.arraycopy(cachebuf, 0, img_stub.image, 0, cachebuf.length);
				*/

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
	
	
	private ImageCache() {

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			images = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			GWT.log("Create image cache failed", e);
		}
		byte[] cachebuf = null;
		
		// http://forums.smartclient.com/showthread.php?t=5258
		try {
			FileInputStream imgfile = new FileInputStream("image/p2.jpg");
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
/*				
				ImgPack img = new ImgPack("img_cache", "seq", "stamp", cachebuf);
*/
/*				
				ImgPack img = new ImgPack("img_cache", "seq", "stamp", cachebuf.length);
				System.arraycopy(cachebuf, 0, img.image, 0, cachebuf.length);
*/				

				ImgPack img = new ImgPack();
				img.id = "img_cache";
				img.seq = "seq";
				img.stamp = "stamp";
				img.image = cachebuf;

				putImage("", img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void putImage(String id, ImgPack image)
	{
		if(image == null) return;
		try {
			images.put("image", image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized ImgPack getImage()
	{
		ImgPack i = (ImgPack) images.get("image");
		if( i == null )
			return img_stub;
		return i;
	}
	
}
