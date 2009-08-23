package com.risetek.scada.server;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.risetek.scada.db.dao.ImageCache;

@SuppressWarnings("serial")
public class cameraPostServiceImpl extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int ContentLength = req.getContentLength();
		GWT.log("Posted picture content is: "+ContentLength, null);
		byte[] cachebuf = new byte[ContentLength];
		InputStream imgData =  req.getInputStream();
		try {
			imgData.read(cachebuf);
			imgData.close();
			ImageCache.imageCache.putImage(cachebuf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}