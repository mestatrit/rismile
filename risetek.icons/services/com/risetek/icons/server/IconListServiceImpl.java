package com.risetek.icons.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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

public class IconListServiceImpl extends HttpServlet {

	private static final long serialVersionUID = -6332238708474515403L;

	// 获取图像列表。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter write = resp.getWriter();
		write.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><icons>");
		List<Icon> list = Icon.getList();
		Iterator<Icon> i = list.iterator();
		while (i.hasNext()) {
			Icon icon = i.next();
			write.write("<icon name=\""+icon.getKey().getName()+"\">");
			byte[] remoteImg = icon.getImage();
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
	        Image oldImage = ImagesServiceFactory.makeImage(remoteImg);
	        Transform resize = ImagesServiceFactory.makeResize(32, 32);
	        Image newImage = imagesService.applyTransform(resize, oldImage, ImagesService.OutputEncoding.PNG);
	        byte[] newImageData = newImage.getImageData();
			write.write(Base64.encode(newImageData));
			write.write("</icon>");
		}
		write.write("</icons>");
		write.flush();
		write.close();
	}

}
