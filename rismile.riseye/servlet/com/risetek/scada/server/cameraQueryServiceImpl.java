package com.risetek.scada.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gwt.core.client.GWT;
import com.risetek.scada.db.dao.ImageCache;

@SuppressWarnings("serial")
public class cameraQueryServiceImpl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GWT.log("output picture", null);
		// 我们通过请求附带的时间戳来避免客户端的cache。
		//resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/jpg");
		ServletOutputStream out = resp.getOutputStream();
		byte[] data = ImageCache.imageCache.getImage();
		if( data != null )
			out.write(data, 0, data.length);
		out.flush();
		out.close();
	}

}