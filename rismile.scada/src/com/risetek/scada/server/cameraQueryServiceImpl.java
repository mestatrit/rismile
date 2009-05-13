package com.risetek.scada.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.ImagesServicePb.ImageData;
import com.google.gwt.core.client.GWT;
import com.risetek.scada.db.dao.ImageCache;

@SuppressWarnings("serial")
public class cameraQueryServiceImpl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GWT.log("output picture", null);
		//resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/jpg");
		ServletOutputStream out = resp.getOutputStream();
		ImageData img = new ImageData();
		byte[] data = ImageCache.imageCache.getImage();
		if( data != null )
		{
			img.parseFrom(ImageCache.imageCache.getImage());
			out.write(img.getContentAsBytes());
			out.flush();
		}
		else
			GWT.log("no picture there", null);

	}

}