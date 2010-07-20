package com.risetek.icons.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.risetek.icons.client.resources.IconManage;
import com.risetek.icons.server.db.Icon;

public class IconsServiceImpl extends HttpServlet {

	private static final long serialVersionUID = -5841554439548932384L;

	// 获取图像。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/png");
		GWT.log("query:"+request);
//		ImageResource res =  IconManage.getIcon(request);
		//resp.getWriter().write();
		
	}

	// 上传图像
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		byte[] image = request.getBytes();
		GWT.log("upload :"+request);
		Icon icon = new Icon(request, image);
		icon.save();
	}


}
