package com.risetek.rismile.system.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ImageBundle;

public class SystemHomeView extends Composite {

	private FlexTable table = new FlexTable();
	private FlexTable table2 = new FlexTable();
	private FlexTable table3 = new FlexTable();

	public interface Images extends ImageBundle {

		AbstractImagePrototype p2();

		AbstractImagePrototype p3();

		AbstractImagePrototype p4();

		AbstractImagePrototype p5();
	}

	private static final Images images = (Images) GWT.create(Images.class);

	public SystemHomeView() {
		// for debug layout.
		// table.setBorderWidth(1);
		// table2.setBorderWidth(1);
		// table3.setBorderWidth(1);

		table.setWidth("100%");
		table2.setWidth("100%");
		table2.setCellPadding(10);

		table3.setHeight("100%");
		table3.setWidth("100%");
		table3.setCellPadding(14);
		table3.setWidget(1, 1, new HTML("&nbsp"));
		table3.setWidget(2, 1, images.p2().createImage());
		table3.setWidget(3, 1, images.p5().createImage());
		table3.setWidget(4, 1, images.p4().createImage());
		table3.setWidget(5, 1, images.p3().createImage());

		table2
				.setHTML(1, 1,
						"<DIV class='sys-introH'>成都中联信通科技有限公司</DIV><DIV class='sys-introH'>专网认证服务器</DIV>");
		table2.setHTML(2, 1, "<ul class='sys-intro'>" + "<li class='sys-intro'>行业无线专网中完成认证授权功能</li>"
				+ "<li class='sys-intro'>实现UIM卡、用户名、密码和固定IP四绑定功能</li>"
				+ "<li class='sys-intro'>支持PAP，CHAP认证方式</li>" + "<li class='sys-intro'>支持最大10000个用户</li>"
				+ "<li class='sys-intro'>采用高性能网络专用处理器</li>" + "<li class='sys-intro'>平均认证时间约500毫秒</li>"
				+ "<li class='sys-intro'>超过一万条日志记录</li>" + "<li class='sys-intro'>快速热备份切换</li>" + "</ul>");

		table.setWidget(1, 1, table3);
		table.setWidget(1, 2, table2);

		initWidget(table);

	}

}
