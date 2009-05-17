package com.risetek.scada.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		GWT.log("camera", null);
		int ContentLength = req.getContentLength();
		GWT.log("content is: "+ContentLength, null);
		byte[] cachebuf = null;
		
		InputStream imgData =  req.getInputStream();
		try {
			byte[] buf = new byte[10240];
			int len;
			while ((len = imgData.read(buf)) > 0) {
				int oldlen;
				if(cachebuf == null )
					oldlen = 0;
				else
					oldlen = cachebuf.length;
				
				
				byte[] newbuf = new byte[oldlen + len];
				if( oldlen > 0)
					System.arraycopy(cachebuf, 0, newbuf, 0, oldlen);
				
				System.arraycopy(buf, 0, newbuf, oldlen, len);
				
				cachebuf = newbuf;
			}				
			
			imgData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		ImageCache.imageCache.putImage(cachebuf);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GWT.log("camera", null);
		byte[] img = (byte[])req.getAttribute("img");
		ImageCache.imageCache.putImage(img);
	
		byte[] cachebuf = null;
		
		// http://forums.smartclient.com/showthread.php?t=5258
		try {
			FileInputStream imgfile = new FileInputStream("image/p2.jpg");

			try {
				byte[] buf = new byte[10240];
				int len;
				while ((len = imgfile.read(buf)) > 0) {
					int oldlen;
					if(cachebuf == null )
						oldlen = 0;
					else
						oldlen = cachebuf.length;
					
					
					byte[] newbuf = new byte[oldlen + len];
					if( oldlen > 0)
						System.arraycopy(cachebuf, 0, newbuf, 0, oldlen);
					
					System.arraycopy(buf, 0, newbuf, oldlen, len);
					
					cachebuf = newbuf;
				}				
				
				imgfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ImageCache.imageCache.putImage(cachebuf);	
	
	}
}