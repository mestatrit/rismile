package com.risetek.scada.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.risetek.scada.client.Entry;

public class SystemHomeView extends Composite {

	private final Grid table = new Grid(1,2);
	private final Grid table2 = new Grid(4,1);
	private final Grid table1 = new Grid(4,1);
	private final Grid serial = new Grid(1, 1);
	private final Grid status = new Grid(1, 2);

	private final HTML title = new HTML(
			"<DIV class='sys-introH'>成都中联信通科技有限公司</DIV><DIV class='sys-introH'>专网认证服务器</DIV>");
	private final HTML feature = new HTML("<ul class='sys-intro'>" + "<li class='sys-intro'>性能平滑</li>"
				+ "<li class='sys-intro'>支持多接口</li>"
				+ "<li class='sys-intro'>MPPC压缩算法</li>"
				+ "<li class='sys-intro'>PPTP、PPPoE等协议</li>"
				+ "<li class='sys-intro'>多重拨号</li>"
				+ "<li class='sys-intro'>按需拨号</li>" + "</ul>");

	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
	}
	
	private static final Images images = (Images) GWT.create(Images.class);

	public SystemHomeView() {

/*
		table.setBorderWidth(1);
		table2.setBorderWidth(1);
		table1.setBorderWidth(1);
*/
		table.setWidth("90%");
		table2.setWidth("100%");
		// table2.setCellPadding(10);

		table1.setHeight("100%");
		table1.setWidth("100%");
		// table3.setCellPadding(14);
		//table1.setWidget(0, 0, serial);
		table1.setWidget(0, 0, new Image(images.p2()));
		table1.setWidget(1, 0, new Image(images.p5()));
		table1.setWidget(2, 0, new Image(images.p4()));
		table1.setWidget(3, 0, new Image(images.p3()));

		table2.getCellFormatter().setHorizontalAlignment(0,0,HasHorizontalAlignment.ALIGN_RIGHT);
		table2.getCellFormatter().setHorizontalAlignment(3,0,HasHorizontalAlignment.ALIGN_RIGHT);
		table2.setWidget(0, 0, serial);
		table2.setWidget(1, 0, title);
		table2.setWidget(2, 0, feature);
		table2.setWidget(3, 0, status);

		table.setWidget(0, 0, table1);
		table.setWidget(0, 1, table2);

		table.setHeight(Entry.SinkHeight);
		initWidget(table);
	}

}
