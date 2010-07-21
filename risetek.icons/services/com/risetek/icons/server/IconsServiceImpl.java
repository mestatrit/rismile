package com.risetek.icons.server;

import java.io.IOException;
import java.io.InputStream;
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

public class IconsServiceImpl extends HttpServlet {
	//private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private static final long serialVersionUID = -5841554439548932384L;

	// 获取图像。
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/png");
		GWT.log("query:"+request);
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
		/*
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("file1");
		blobstoreService.
		*/
		String request = req.getRequestURI().substring(7);	// Skip /icons/
		int ContentLength = req.getContentLength();
		InputStream imgData =  req.getInputStream();
		try {
			byte[] remoteImg = new byte[ContentLength];
			imgData.read(remoteImg);
			Icon icon = new Icon(request, remoteImg);
			icon.save();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			imgData.close();
		}

	}


}
