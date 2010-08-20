package risetek.client.view;

import risetek.client.control.ProduceHomeController;
import risetek.client.model.ProduceData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.RismileContext;
import com.risetek.rismile.client.RismileContext.RismileRuntimeHandler;
import com.risetek.rismile.client.RismileContext.RuntimeEvent;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.view.IRisetekView;
import com.risetek.rismile.client.view.ViewComposite;

public class SystemHomeView extends ViewComposite implements IRisetekView {

	interface ProudceUiBinder extends UiBinder<Widget, SystemHomeView> {}
	private static ProudceUiBinder uiBinder = GWT.create(ProudceUiBinder.class);
	
	private final SystemHomeSider sider = new SystemHomeSider();
	
	public interface Images extends ClientBundle  {
		@Source("p2.jpg")		ImageResource  p2();
		@Source("p3.jpg")		ImageResource  p3();
		@Source("p4.jpg")		ImageResource  p4();
		@Source("p5.jpg")		ImageResource  p5();
	}

	public SystemHomeView() {
		Widget w = uiBinder.createAndBindUi(this);
		w.setWidth("100%");
		w.setHeight("100%");
		addLeftSide(w);
		addRightSide(sider);
	}

	public void render(ProduceData data) {
		sider.render(data);
	}

	public void disablePrivate() {
		// Nothing to do.
	}
	public void enablePrivate() {
		// Nothing to do.
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {
		// Nothing to do.
	}

	private class systemTips extends Grid {
		public systemTips(String title) {
			//setBorderWidth(1);
			resize(2, 1);
			setWidth("100%");
			setText(0, 0, title);
			setCellPadding(0);
			setCellSpacing(3);
			getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
			Style  style = getElement().getStyle();
			style.setBorderColor("#dce7f7");
			style.setBorderStyle(BorderStyle.SOLID);
			style.setBorderWidth(1, Unit.PX);
			
			style = getCellFormatter().getElement(0, 0).getStyle();
			style.setFontSize(12, Unit.PX);

			style = getCellFormatter().getElement(1, 0).getStyle();
			style.setFontSize(9, Unit.PX);
			style.setOverflow(Overflow.HIDDEN);
		}
		public void setTips(String tips) {
			setText(1, 0, tips);
		}
	}
	
	private class SystemHomeSider extends Composite {

		private final systemTips version = new systemTips("版本号:");
		private final systemTips serial = new systemTips("序列号:");
		private systemTips status;	// = new systemTips("已运行:");

		public SystemHomeSider() {
			final DockPanel dockpanel = new DockPanel();
			final VerticalPanel northpanel = new VerticalPanel();
			northpanel.setHeight("120px");
			northpanel.setSpacing(3);
			northpanel.setWidth("100%");
			northpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			northpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

			final VerticalPanel southpanel = new VerticalPanel();
			southpanel.setHeight("100%");
			southpanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

			initWidget(dockpanel);

			//dpanel.setBorderWidth(1);
			dockpanel.add(northpanel, DockPanel.NORTH);
			dockpanel.add(southpanel, DockPanel.SOUTH);
			dockpanel.setCellVerticalAlignment(southpanel, HasVerticalAlignment.ALIGN_BOTTOM);

			northpanel.add(version);
			northpanel.add(serial);
			
			//spanel.add(new Stick("info", "操作提示："));
			southpanel.add(new Stick("info", "左右方向键能用来选择功能标签。"));
			southpanel.add(new Stick("info", "Alt+L 能快捷访问特权登录按钮。"));
			southpanel.add(new Stick("info", "弹出的对话框，能用ESC键快速关闭。"));
			southpanel.add(new Stick("info", "未进入特权模式，您只能查看设备配置和运行情况。"));
			southpanel.add(new Stick("info", "特权登录后，您能修改、设置设备的配置信息。"));
//			southpanel.add(new Stick("info", "为系统安全，特权登录的口令只能通过管理员用控制台命令完成。"));
	
			dockpanel.setHeight("100%");
			
			// 触发新的心跳，加速运行时间的提取。
			Heartbeat.startHeartbeat();
			
			RismileContext.addRunTimeHandler(new RismileRuntimeHandler(){
				@Override
				public void onRuntime(RuntimeEvent event) {
					if( status == null ) {
						status = new systemTips("已运行:");
						northpanel.add(status);
					}
					status.setTips(event.getResults());
				}
			});
		
		}

		public void render(ProduceData data) {
			version.setTips(data.version);
			serial.setTips(data.serial);
		}

		public void onLoad() {
			ProduceHomeController.load();
		}
	}

}
