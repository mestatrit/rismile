package com.risetek.icons.server;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.risetek.icons.server.db.Icon;
import java.util.logging.Logger;

public class IconsServiceImpl extends HttpServlet {
	private static final long serialVersionUID = -5841554439548932384L;
	private static final Logger log = Logger.getLogger(IconsServiceImpl.class.getName());	
	// 获取图像。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/png");
		log.info("query:"+request);
		Icon icon = Icon.getIcon(request);
		if( icon != null) {
			ServletOutputStream os = resp.getOutputStream();
			os.write(icon.getImage());
			os.flush();
			os.close();
		}
	}

	// 上传图像
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//ServletFileUpload upload = new ServletFileUpload();
		resp.setContentType("text/plain");
	      
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		log.info("upload:"+request);
		int ContentLength = req.getContentLength();
		InputStream imgData =  req.getInputStream();
		try {
			byte[] remoteImg = new byte[ContentLength];
			while( imgData.available() < ContentLength ) {
				Thread.yield();
			}
			int len = 0;
			len = imgData.read(remoteImg);
			Icon icon = new Icon(request, remoteImg);
			icon.save();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			imgData.close();
		}
	}


}
