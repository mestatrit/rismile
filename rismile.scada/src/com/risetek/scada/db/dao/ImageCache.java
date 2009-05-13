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
import com.google.appengine.api.images.Image;
public class ImageCache {

	public static ImageCache imageCache = new ImageCache();

	private Cache image;

	
	// TODO: 我们需要一个缺省的图片！
	
	private ImageCache() {

		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			image = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			GWT.log("Create image cache failed", e);
		}
		
		//Image img = new Image();
		/*
		try {
			FileInputStream imgfile = new FileInputStream("/image/p2.jpg");
			byte[] b;
			try {
				imgfile.read(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	
	public void putImage(byte[] image)
	{
		this.image.put("image", image);
	}
	
	public byte[] getImage()
	{
		return (byte[])image.get("image");
	}
	
}
