package risetek.client.view;

import risetek.client.control.ProduceHomeController;
import risetek.client.model.ProduceData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ImageBundle;
import com.risetek.rismile.client.Entry;

public class SystemHomeView extends Composite {

	private final Grid table = new Grid(1,2);
	private final Grid table2 = new Grid(4,1);
	private final Grid table1 = new Grid(4,1);
	private final Grid serial = new Grid(1, 1);
	private final Grid status = new Grid(1, 2);

	private final HTML title = (Entry.OEMFlag == Entry.OEM.risetek) ? 
			new HTML("<DIV class='sys-introH'>成都中联信通科技有限公司</DIV><DIV class='sys-introH'>专网认证服务器</DIV>" )
			: new HTML("<DIV class='sys-introH'>四川通发电信股份有限公司</DIV><DIV class='sys-introH'>专网认证服务器</DIV>");
	private final HTML feature = new HTML("<ul class='sys-intro'>"
			+ "<li class='sys-intro'>行业无线专网中完成认证授权功能</li>"
			+ "<li class='sys-intro'>实现设备号、用户名、密码和固定地址四绑定功能</li>"
			+ "<li class='sys-intro'>支持PAP，CHAP认证方式</li>"
			+ "<li class='sys-intro'>支持最大10000个用户</li>"
			+ "<li class='sys-intro'>采用高性能网络专用处理器</li>"
			+ "<li class='sys-intro'>平均认证时间约500毫秒</li>"
			+ "<li class='sys-intro'>超过一万条日志记录</li>"
			+ "<li class='sys-intro'>快速热备份切换</li>" + "</ul>");

	public ProduceHomeController control;

	public interface Images extends ImageBundle {

		AbstractImagePrototype p2();

		AbstractImagePrototype p3();

		AbstractImagePrototype p4();

		AbstractImagePrototype p5();
	}

	private static final Images images = (Images) GWT.create(Images.class);

	public SystemHomeView(ProduceHomeController control) {
		this.control = control;
		serial.setStyleName("info-table");
		// serial.setWidth("100%");
		// for debug layout.
/*
		table.setBorderWidth(1);
		table2.setBorderWidth(1);
		table1.setBorderWidth(1);
*/
		table.setWidth("90%");
		table2.setWidth("100%");
		// table2.setCellPadding(10);

		table1.setStyleName("images-table");
		table1.setHeight("100%");
		table1.setWidth("100%");
		// table3.setCellPadding(14);
		//table1.setWidget(0, 0, serial);
		table1.setWidget(0, 0, images.p2().createImage());
		table1.setWidget(1, 0, images.p5().createImage());
		table1.setWidget(2, 0, images.p4().createImage());
		table1.setWidget(3, 0, images.p3().createImage());

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
		setStyleName("radius-config");
	}

	public void render(ProduceData data) {
		serial.setText(0, 0, "产品系列号：" + data.serial);
		//info.setText(1, 0, data.version);
		status.setText(0, 0, data.status);
	}

	public void onLoad() {
		control.load();
	}
}
