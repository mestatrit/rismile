package com.risetek.icons.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.risetek.icons.server.db.TreedIcons;

public class RESTfulIconNullListServiceImpl extends HttpServlet {
	private static final Logger log = Logger.getLogger(RESTfulIconNullListServiceImpl.class.getName());	

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
		
		PrintWriter write = resp.getWriter();
		if( callback != null )
			write.write(callback+"(\"");
			
		write.write("<?xml version='1.0' encoding='utf-8'?><icons>");
		List<TreedIcons> icons = TreedIcons.getIconNullList();
		for( TreedIcons icon:icons)
			write.write("<icon name='"+icon.getKey().getName()+"'/>");
		write.write("</icons>");
		
		if( callback != null )
			write.write("\")");
		
		write.flush();
		write.close();
	}


	// 上传图像
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		ServletFileUpload upload = new ServletFileUpload(); 
		try {
			FileItemIterator iterator = upload.getItemIterator(req);
			// TODO: FIXME:
			// 我们其实不支持多个文件同时上传的。
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				
//				String fileName = item.getFieldName();//item.getName();
				String fileName = req.getPathInfo();
				log.log(Level.INFO, "upload:"+fileName);
				fileName = fileName.replace("\\","/");
				fileName = fileName.replace("/nulllist/", "");
				fileName = fileName.substring(fileName.lastIndexOf("/")+1);
				InputStream stream = item.openStream();
				byte[] remoteImg = IOUtils.toByteArray(stream);
				TreedIcons icon = TreedIcons.getIcon(fileName);
				icon.setImage(remoteImg);
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
