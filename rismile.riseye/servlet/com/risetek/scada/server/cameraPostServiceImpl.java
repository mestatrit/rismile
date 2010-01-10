package com.risetek.scada.server;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.risetek.scada.db.dao.ImageCache;
import com.risetek.scada.db.dao.ImgPack;

@SuppressWarnings("serial")
public class cameraPostServiceImpl extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int ContentLength = req.getContentLength();
		GWT.log("Posted picture content is: "+ContentLength, null);

		String ident = req.getParameter("id");
		if( ident == null )
			ident = "no id";
		
		String seq = req.getParameter("sq");
		if( seq == null )
			seq = "no seq";

		String stamp = req.getParameter("timestamp");
		if( stamp == null )
			stamp = "local:"+ new Long(System.currentTimeMillis()).toString();
		
//		ImgPack img = new ImgPack(ident, seq, stamp, ContentLength);
		
		byte[] remoteImg = new byte[ContentLength];
		
		InputStream imgData =  req.getInputStream();
		try {
			imgData.read(remoteImg);
			imgData.close();
			
			ImgPack img = new ImgPack();
			img.id = ident;
			img.seq = seq;
			img.stamp = stamp;
			img.image = remoteImg;
			
			ImageCache.imageCache.putImage("", img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}