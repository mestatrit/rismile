package com.risetek.icons.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.risetek.icons.server.db.Icon;

import java.util.logging.Level;
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

		resp.setCharacterEncoding("UTF-8");
		ServletFileUpload upload = new ServletFileUpload(); 
		try {
			FileItemIterator iterator = upload.getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				String fileName = item.getName().replace("\\","/");
				fileName = fileName.replace("/icons/", "");
				fileName = fileName.substring(fileName.lastIndexOf("/")+1);
				InputStream stream = item.openStream();
				byte[] remoteImg = IOUtils.toByteArray(stream);
				Icon icon = new Icon(fileName, remoteImg);
				icon.save();
			}
		} catch (FileUploadException e1) {
			log.log(Level.WARNING, e1.toString());
		}
		PrintWriter pw= resp.getWriter();
		pw.write("上传成功");
		pw.flush();
		pw.close();
	}
}
