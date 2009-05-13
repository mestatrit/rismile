package com.risetek.scada.server;

import java.io.IOException;
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
		GWT.log("camera", null);
		byte[] img = (byte[])req.getAttribute("img");
		ImageCache.imageCache.putImage(img);
	}
}