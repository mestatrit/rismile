package com.risetek.scada.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.scada.client.ImgPack;
import com.risetek.scada.client.view.mapsView.GPSService;
import com.risetek.scada.db.dao.ImageCache;

public class GPSServiceImpl extends RemoteServiceServlet implements	GPSService {

	private static final long serialVersionUID = 1854405110192888466L;

	@Override
	public ArrayList<ImgPack> getGPS(String cookie) {
		return ImageCache.getGPS();
		/*
		ImgPack img = ImageCache.imageCache.getGPS();
		
		if( cookie.equalsIgnoreCase(img.Cookie))
		{
			img.image = null;
		}
		GWT.log("GPSCache: "+img.GPS, null);
		return img;
		*/
	}

}
