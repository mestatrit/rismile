package com.risetek.scada.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		// TODO: no-cache
		resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		ServletOutputStream out = resp.getOutputStream();
		
		/*
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
		*/
		// http://forums.smartclient.com/showthread.php?t=5258
		try {
			FileInputStream imgfile = new FileInputStream("image/p3.jpg");

			try {
				byte[] buf = new byte[1024];
				int len;
				while ((len = imgfile.read(buf)) > 0) {
					out.write(buf, 0, len);
				}				
				
				imgfile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
		
		
	}

}