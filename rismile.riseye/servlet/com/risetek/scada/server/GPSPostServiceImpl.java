package com.risetek.scada.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risetek.scada.client.ImgPack;
import com.risetek.scada.db.dao.ImageCache;

@SuppressWarnings("serial")
public class GPSPostServiceImpl extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String ident = req.getParameter("id");
		if( ident == null )
			ident = "no id";
		
		String gps_message = req.getParameter("gps");
		if( gps_message == null )
			gps_message = "no gps";

		ImgPack img = new ImgPack();
		img.GPS = gps_message;
		ImageCache.imageCache.putGPS("", img);
	}

}