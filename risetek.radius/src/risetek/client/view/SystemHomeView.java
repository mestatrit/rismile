package risetek.client.view;

import risetek.client.control.ProduceHomeController;
import risetek.client.model.ProduceData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.RismileRuntimeHandler;
import com.risetek.rismile.client.RismileContext.RuntimeEvent;
import com.risetek.rismile.client.view.IRisetekView;

public class SystemHomeView extends Composite implements IRisetekView {

	static {
		RismileContext.addRunTimeHandler(new RismileRuntimeHandler(){

			@Override
			public void onRuntime(RuntimeEvent event) {
				status.setInnerText(event.getResults());
			}
		});
		
	}
	
	interface ProudceUiBinder extends UiBinder<Widget, SystemHomeView> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	@UiField SpanElement version;
	@UiField SpanElement serial;
	@UiField static SpanElement status;

	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
	}

	public SystemHomeView() {
		Widget w = uiBinder.createAndBindUi(this);
		w.setHeight(Entry.SinkHeight);
		initWidget(w);
	}

/*	
	private final Grid table = new Grid(1,2);
	private final Grid table2 = new Grid(5,1);
	private final Grid table1 = new Grid(4,1);
	private final Grid serial = new Grid(1, 1);
	private final Grid version = new Grid(1, 1);
	private final static Grid status = new Grid(1, 2);

	private final HTML title = (RismileContext.OEMFlag == RismileContext.OEM.risetek) ? 
			new HTML("<DIV>成都中联信通科技有限公司</DIV><DIV>专网认证服务器</DIV>" )
			: new HTML("<DIV>四川通发电信股份有限公司</DIV><DIV>专网认证服务器</DIV>");
	private final HTML feature = new HTML("<ul>"
			+ "<li>行业无线专网中完成认证授权功能</li>"
			+ "<li>实现设备号、用户名、密码和固定地址四绑定</li>"
			+ "<li>支持PAP，CHAP认证方式</li>"
			+ "<li>支持最大10000个用户</li>"
			+ "<li>采用高性能网络专用处理器</li>"
			+ "<li>平均认证时间约500毫秒</li>"
			+ "<li>超过一万条日志记录</li>"
			+ "<li>快速热备份切换</li>" + "</ul>");

	public interface Resources extends ClientBundle {
		final static Resources INSTANCE = GWT.create(Resources.class);
		
		@Source("p2.jpg")
		ImageResource p2();
		@Source("p3.jpg")
		ImageResource p3();
		@Source("p4.jpg")
		ImageResource p4();
		@Source("p5.jpg")
		ImageResource p5();
	}
	
	public SystemHomeView() {
		version.setStyleName("info-table");
		serial.setStyleName("info-table");
		title.setStyleName("sys-introH");
		feature.setStyleName("sys-intro");
		// serial.setWidth("100%");
		// for debug layout.
//		table.setBorderWidth(1);
//		table2.setBorderWidth(1);
		//table1.setBorderWidth(1);

		table.setWidth("90%");
		table2.setHeight("100%");
		table2.setWidth("100%");
		// table2.setCellPadding(10);

		table1.setStyleName("images-table");
		table1.setHeight("100%");
		table1.setWidth("100%");
		// table3.setCellPadding(14);
		//table1.setWidget(0, 0, serial);
		table1.setWidget(0, 0, new Image(Resources.INSTANCE.p2()));
		table1.setWidget(1, 0, new Image(Resources.INSTANCE.p5()));
		table1.setWidget(2, 0, new Image(Resources.INSTANCE.p4()));
		table1.setWidget(3, 0, new Image(Resources.INSTANCE.p3()));

		table2.setWidth("100%");
		table2.setHeight("100%");
		table2.getCellFormatter().setHorizontalAlignment(0,0,HasHorizontalAlignment.ALIGN_RIGHT);
		table2.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_RIGHT);

		table2.getCellFormatter().setHorizontalAlignment(2,0,HasHorizontalAlignment.ALIGN_LEFT);
		table2.getCellFormatter().setHorizontalAlignment(3,0,HasHorizontalAlignment.ALIGN_LEFT);
		
		table2.getCellFormatter().setHorizontalAlignment(4,0,HasHorizontalAlignment.ALIGN_RIGHT);
		table2.setWidget(0, 0, version);
		table2.setWidget(1, 0, serial);
		table2.setWidget(2, 0, title);
		table2.setWidget(3, 0, feature);
		table2.setWidget(4, 0, status);

		table.setWidget(0, 0, table1);
		table.setWidget(0, 1, table2);

		table.setHeight(Entry.SinkHeight);
		initWidget(table);
		setStyleName("radius-config");
	}
*/

	public void render(ProduceData data) {
		version.setInnerText(data.version);
		serial.setInnerText(data.serial);

		status.setInnerText(data.status);
	}

	public void onLoad() {
		ProduceHomeController.load();
	}

	public void disablePrivate() {
	}
	public void enablePrivate() {
	}
}
