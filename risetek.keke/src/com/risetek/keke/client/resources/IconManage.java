package com.risetek.keke.client.resources;

import java.util.HashMap;
import java.util.Random;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public class IconManage {
	public static String getDefault() {
		return "20090218213212783";
	}
	
	
	public interface Images extends ClientBundle  {
		
		@Source("20090218213158120.png")		ImageResource  I20090218213158120();
		@Source("20090218213158200.png")		ImageResource  I20090218213158200();
		@Source("20090218213158214.png")		ImageResource  I20090218213158214();
		@Source("20090218213158800.png")		ImageResource  I20090218213158800();
		@Source("20090218213158872.png")		ImageResource  I20090218213158872();
		@Source("20090218213158904.png")		ImageResource  I20090218213158904();
		
		@Source("20090218213211612.png")		ImageResource  I20090218213211612();
		@Source("20090218213211718.png")		ImageResource  I20090218213211718();
		@Source("20090218213212220.png")		ImageResource  I20090218213212220();
		@Source("20090218213212783.png")		ImageResource  I20090218213212783();
		@Source("20090218213213314.png")		ImageResource  I20090218213213314();
		@Source("20090218213214862.png")		ImageResource  I20090218213214862();
		@Source("20090218213215625.png")		ImageResource  I20090218213215625();
		@Source("20090218213215859.png")		ImageResource  I20090218213215859();
		@Source("20090218213216656.png")		ImageResource  I20090218213216656();
		@Source("20090218213217243.png")		ImageResource  I20090218213217243();
		@Source("20090218213218178.png")		ImageResource  I20090218213218178();
		@Source("20090218213218568.png")		ImageResource  I20090218213218568();
		@Source("20090218213219389.png")		ImageResource  I20090218213219389();
		@Source("20090218213219741.png")		ImageResource  I20090218213219741();
		@Source("20090218213222605.png")		ImageResource  I20090218213222605();
		@Source("20090218213222671.png")		ImageResource  I20090218213222671();
		@Source("20090218213227180.png")		ImageResource  I20090218213227180();
		@Source("20090218213227509.png")		ImageResource  I20090218213227509();
		
	}
	public static Images imgSrc = GWT.create(Images.class);

	private HashMap<String , ImageResource> v = new HashMap<String , ImageResource>();
	
	public IconManage() {

		v.put("20090218213158120", imgSrc.I20090218213158120());
		v.put("20090218213158200", imgSrc.I20090218213158200());
		v.put("20090218213158214", imgSrc.I20090218213158214());
		v.put("20090218213158800", imgSrc.I20090218213158800());
		v.put("20090218213158872", imgSrc.I20090218213158872());
		v.put("20090218213158904", imgSrc.I20090218213158904());
		
		v.put("20090218213211612", imgSrc.I20090218213211612());
		v.put("20090218213211718", imgSrc.I20090218213211718());
		v.put("20090218213212220", imgSrc.I20090218213212220());
		v.put("20090218213212783", imgSrc.I20090218213212783());
		v.put("20090218213213314", imgSrc.I20090218213213314());
		v.put("20090218213214862", imgSrc.I20090218213214862());
		v.put("20090218213215625", imgSrc.I20090218213215625());
		v.put("20090218213215859", imgSrc.I20090218213215859());
		v.put("20090218213216656", imgSrc.I20090218213216656());
		v.put("20090218213217243", imgSrc.I20090218213217243());
		v.put("20090218213218178", imgSrc.I20090218213218178());
		v.put("20090218213218568", imgSrc.I20090218213218568());
		v.put("20090218213219389", imgSrc.I20090218213219389());
		v.put("20090218213219741", imgSrc.I20090218213219741());
		v.put("20090218213222605", imgSrc.I20090218213222605());
		v.put("20090218213222671", imgSrc.I20090218213222671());
		v.put("20090218213227180", imgSrc.I20090218213227180());
		v.put("20090218213227509", imgSrc.I20090218213227509());

		v.put("Logout", imgSrc.I20090218213227509());
		v.put("Login", imgSrc.I20090218213158800());
		v.put("Password", imgSrc.I20090218213218178());
		v.put("Error", imgSrc.I20090218213212220());
	}
	
	static Random looker = new Random();
	
	public static ImageResource getIcon(String name) {
		if( "<img/>".equals(name)) {
			// 返回随机图标？
			int size = INSTANCE.v.size();
			size = looker.nextInt(size);
			ImageResource i = (ImageResource) INSTANCE.v.values().toArray()[size];
			return i;
		}
		// TODO: FIXME: 这是临时措施。
		name = name.replace("<img>", "");
		name = name.replace("</img>", "");
		ImageResource i = INSTANCE.v.get(name);
		if( i == null )
			i = INSTANCE.v.get("20090218213212783");

		return i;
	}

	
	static IconManage INSTANCE = new IconManage();
}
