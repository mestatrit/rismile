package com.risetek.icons.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
import java.util.Map;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
*/
import com.google.gwt.core.client.GWT;
import com.risetek.icons.server.db.Icon;

public class IconListServiceImpl extends HttpServlet {

	private static final long serialVersionUID = -6332238708474515403L;

	// 获取图像列表。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter write = resp.getWriter();
		write.write("help me");
		String[] lists = Icon.getList();
		for( int loop = 0; loop < lists.length; loop++ )
			write.write(lists[loop]);
		write.flush();
		write.close();
	}

}
