package com.risetek.icons.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.risetek.icons.server.db.TreedIcons;

public class RESTfulIconServiceImpl extends HttpServlet {

	private static final long serialVersionUID = -1718754673082828661L;

	// 获取图像列表。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript");
		resp.setHeader("Cache-Control" ,"no-cache");
		resp.setHeader("Pragma","no-cache");
		String callback = req.getParameter("callback");

//		String REST = req.getPathInfo();
		String REST = req.getParameter("icons");
		
		PrintWriter write = resp.getWriter();
		if( callback != null )
			write.write(callback+"(\"");
			
		write.write("<?xml version='1.0' encoding='utf-8'?><icons>");
		if( REST != null )
		{
			TreedIcons icon = TreedIcons.getIcon(REST);
			byte[] img = icon.getImage();
			if( img != null ) {
				write.write("<icon name='"+icon.getKey().getName()+"'>");
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
		        Image oldImage = ImagesServiceFactory.makeImage(img);
		        Transform resize = ImagesServiceFactory.makeResize(32, 32);
		        Image newImage = imagesService.applyTransform(resize, oldImage, ImagesService.OutputEncoding.PNG);
		        byte[] newImageData = newImage.getImageData();
				write.write(Base64.encode(newImageData));
				write.write("</icon>");
			}
		}
		write.write("</icons>");
		
		if( callback != null )
			write.write("\")");
		
		write.flush();
		write.close();
	}

}
