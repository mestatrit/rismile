package com.risetek.scada.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.risetek.scada.client.ImgPack;
import com.risetek.scada.client.view.cameraView.PhotoService;
import com.risetek.scada.db.dao.ImageCache;

public class PhotoServiceImpl extends RemoteServiceServlet implements	PhotoService {

	private static final long serialVersionUID = -5941194778985715033L;

	@Override
	public ArrayList<ImgPack> getPhoto(String ident, long cookie) {
		return ImageCache.getList(ident, cookie);
	}

}
