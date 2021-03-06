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
import com.risetek.icons.server.db.Icon;

public class IconScriptServiceImpl extends HttpServlet {

	private static final long serialVersionUID = -6332238708474515403L;

	// 获取图像列表。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript");
		resp.setHeader("Cache-Control" ,"no-cache");
		resp.setHeader("Pragma","no-cache");
		PrintWriter write = resp.getWriter();
		String callback = req.getParameter("callback");
		String iconName = req.getParameter("icons");
		Icon icon = Icon.getIcon(iconName);

		write.write(callback+"(\"<?xml version='1.0' encoding='utf-8'?><icons>");
		if( icon != null )
		{
			write.write("<icon name='"+icon.getKey().getName()+"'>");
			byte[] remoteImg = icon.getImage();
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
	        Image oldImage = ImagesServiceFactory.makeImage(remoteImg);
	        Transform resize = ImagesServiceFactory.makeResize(32, 32);
	        Image newImage = imagesService.applyTransform(resize, oldImage, ImagesService.OutputEncoding.PNG);
	        byte[] newImageData = newImage.getImageData();
			write.write(Base64.encode(newImageData));
			write.write("</icon>");
		}
		write.write("</icons>\")");
		
		write.flush();
		write.close();
	}

}
