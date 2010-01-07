package com.risetek.scada.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.scada.client.RCPImage;
import com.risetek.scada.client.view.cameraView.PhotoService;
import com.risetek.scada.db.dao.ImageCache;
import com.risetek.scada.db.dao.ImgPack;

public class PhotoServiceImpl extends RemoteServiceServlet implements	PhotoService {

	private static final long serialVersionUID = -5941194778985715033L;

	@Override
	public RCPImage getPhoto(String id) {
		ImgPack img1 = ImageCache.imageCache.getImage();
		RCPImage img = new RCPImage();
		img.id = img1.id;
		img.seq = img1.seq;
		img.stamp = img1.stamp;
		img.image = img1.image.clone();
		return img;
	}

}
