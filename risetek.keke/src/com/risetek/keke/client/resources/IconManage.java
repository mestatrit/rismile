package com.risetek.keke.client.resources;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class IconManage {
	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
		
		@Source("20090218213158120.png")		ImageResource  I20090218213158120();
		@Source("20090218213158200.png")		ImageResource  I20090218213158200();
		@Source("20090218213158214.png")		ImageResource  I20090218213158214();
		@Source("20090218213158800.png")		ImageResource  I20090218213158800();
		@Source("20090218213158872.png")		ImageResource  I20090218213158872();
		@Source("20090218213158904.png")		ImageResource  I20090218213158904();
		
		
	}
	public static Images imgSrc = GWT.create(Images.class);

	private HashMap<String , ImageResource> v = new HashMap<String , ImageResource>();
	
	public IconManage() {
		v.put("p2", imgSrc.p2());
		v.put("p3", imgSrc.p3());
		v.put("p4", imgSrc.p4());
		v.put("p5", imgSrc.p5());

		v.put("20090218213158120", imgSrc.I20090218213158120());
		v.put("20090218213158200", imgSrc.I20090218213158200());
		v.put("20090218213158214", imgSrc.I20090218213158214());
		v.put("20090218213158800", imgSrc.I20090218213158800());
		v.put("20090218213158872", imgSrc.I20090218213158872());
		v.put("20090218213158904", imgSrc.I20090218213158904());
	}
	
	public static ImageResource getIcon(String name) {
		ImageResource i = INSANCE.v.get(name);
		if( i == null )
			i = INSANCE.v.get("p2");
		return i;
	}

	
	static IconManage INSANCE = new IconManage();
}
