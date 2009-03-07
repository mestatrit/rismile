package com.risetek.rismile.system.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.view.IView;
import com.risetek.rismile.system.client.control.SystemAllController;
import com.risetek.rismile.system.client.dialog.AddOrModifyIpDialog;
import com.risetek.rismile.system.client.dialog.AddRouteDialog;
import com.risetek.rismile.system.client.dialog.AdminDialog;
import com.risetek.rismile.system.client.dialog.UpfileDialog;
import com.risetek.rismile.system.client.model.InterfEntry;
import com.risetek.rismile.system.client.model.RouterEntry;
import com.risetek.rismile.system.client.model.Service;
import com.risetek.rismile.system.client.model.SystemAll;

public class SystemView extends Composite implements IView {

	public interface Images extends ImageBundle {

		AbstractImagePrototype leftCorner();

		AbstractImagePrototype rightCorner();
	}

	private static final Images images = (Images) GWT.create(Images.class);
	final Grid dateGrid = new Grid();
	final Grid serviceGrid = new Grid();
	final Grid netGrid = new Grid();
	final Grid routeGrid = new Grid();

	final Label infoLabel = new Label("");
	final private Button netAddButton = new Button("添加IP");
	final private Button routeAddButton = new Button("添加路由");
	final private Button paraButton = new Button("恢复出厂参数");
	final private Button restartButton = new Button("重启设备");
	final private Button upfileButton = new Button("升级程序");
	private int nextHeaderIndex = 0;
	final FlexTable flexTable = new FlexTable();

	public SystemView() {
		flexTable.setStyleName("page-container");
		initWidget(flexTable);
		flexTable.setWidth("100%");
		HTML emptyHtml = new HTML("&nbsp;");
		flexTable.setWidget(0, 0, emptyHtml);
		emptyHtml.setHeight("1em");

		flexTable.setWidget(0, 1, infoLabel);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		infoLabel.setStyleName("sys-info-Label");

		final StackPanel stackPanel = new StackPanel();
		stackPanel.setWidth("100%");
		stackPanel.setHeight("433px");
		stackPanel.setStyleName("sys-stack-panel");
		flexTable.setWidget(1, 0, stackPanel);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);

		final VerticalPanel servicePanel = new VerticalPanel();
		servicePanel.setWidth("100%");
		servicePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		dateGrid.setWidth("40%");
		dateGrid.addStyleName("sys-date-table");
		dateGrid.resize(1, 2);
		servicePanel.add(dateGrid);

		serviceGrid.setBorderWidth(1);
		serviceGrid.setWidth("90%");
		serviceGrid.setStyleName("sys-status-table");
		serviceGrid.resize(2, 3);
		servicePanel.add(serviceGrid);

		stackPanel.add(servicePanel, createHeaderHTML(images, "系统服务"), true);

		final VerticalPanel netPanel = new VerticalPanel();
		netPanel.setWidth("100%");
		netPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		netAddButton.setStyleName("sys-big-button");
		netPanel.add(netAddButton);

		netGrid.setBorderWidth(1);
		netGrid.setWidth("90%");
		netGrid.setStyleName("sys-grid2-table");
		netGrid.resize(2, 4);
		netPanel.add(netGrid);

		stackPanel.add(netPanel, createHeaderHTML(images, "网络接口"), true);

		final VerticalPanel routePanel = new VerticalPanel();
		routePanel.setWidth("100%");
		routePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		routeAddButton.setStyleName("sys-big-button");
		routePanel.add(routeAddButton);

		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("90%");
		routeGrid.setStyleName("sys-grid2-table");
		routeGrid.resize(2, 5);
		routePanel.add(routeGrid);

		stackPanel.add(routePanel, createHeaderHTML(images, "路由"), true);

		final Grid adminGrid = new Grid();
		adminGrid.setWidth("100%");
		adminGrid.resize(2, 5);
		adminGrid.setStyleName("sys-grid1-table");

		stackPanel.add(adminGrid, createHeaderHTML(images, "管理及配置"), true);

		final Button addAdminButton = new Button("添加管理员");
		addAdminButton.setStyleName("sys-big-button");
		adminGrid.setWidget(0, 0, addAdminButton);
		addAdminButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				(new AdminDialog(SystemView.this, sender, AdminDialog.ADD))
						.show();
			}

		});
		final Button delAdminButton = new Button("删除管理员");
		delAdminButton.setStyleName("sys-big-button");
		adminGrid.setWidget(0, 1, delAdminButton);
		delAdminButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				(new AdminDialog(SystemView.this, sender, AdminDialog.DEL))
						.show();
			}

		});

		adminGrid.setWidget(0, 2, paraButton);
		paraButton.setStyleName("sys-big-button");
		paraButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				// Window.open("forms/restart", "_blank", "");

				if (Window.confirm("是否要恢复出厂参数？\n"
						+ "恢复出厂参数后，IP地址为192.168.0.1 。")) {
					paraButton.setEnabled(false);
					
					systemAllController.restorePara(new VerifyAction(paraButton, false));
				}

			}
		});

		adminGrid.setWidget(0, 3, restartButton);
		restartButton.setStyleName("sys-big-button");
		restartButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				if (Window.confirm("是否要重启设备？")) {
					restartButton.setEnabled(false);
					systemAllController.reset(new VerifyAction(restartButton, false));
				}
			}

		});

		adminGrid.setWidget(0, 4, upfileButton);
		upfileButton.setStyleName("sys-big-button");
		upfileButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				(new UpfileDialog(SystemView.this)).show();
			}

		});
		
		//adminGrid.setWidget(1, 0, new HTML("<a href=\"forms/SwingPinger\">设备监视工具</a>"));

		initTable();
	}

	public String ip_address;
	public String ip_mask;
	public SystemAllController systemAllController = new SystemAllController();

	public int getHeight() {
		return flexTable.getOffsetHeight();
	}

	private String createHeaderHTML(Images images, String caption) {
		Grid captionGrid = new Grid();
		captionGrid.setWidth("100%");
		captionGrid.resize(1, 2);

		HTML captionHtml = new HTML(caption);
		captionHtml.setStyleName("stack-caption");
		HTML figureHtml = new HTML("&nbsp;");
		figureHtml.setStyleName("stack-figure");

		captionGrid.setWidget(0, 0, captionHtml);
		captionGrid.getCellFormatter().setWidth(0, 0, "90%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_TOP);
		captionGrid.setWidget(0, 1, figureHtml);
		captionGrid.getCellFormatter().setWidth(0, 1, "10%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);

		String innerHtml = DOM.getInnerHTML(captionGrid.getElement());

		boolean isTop = (nextHeaderIndex == 0);
		nextHeaderIndex++;

		String captionInnerHtml = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>"
				+ innerHtml + "</b></td></tr></table>";

		return "<table align='left' cellpadding='0' cellspacing='0'"
				+ (isTop ? " class='is-top'" : "") + "><tbody>"
				+ "<tr><td class='box-00'>" + images.leftCorner().getHTML()
				+ "</td>" + "<td class='box-10'>&nbsp;</td>"
				+ "<td class='box-20'>" + images.rightCorner().getHTML()
				+ "</td>" + "</tr><tr>" + "<td class='box-01'>&nbsp;</td>"
				+ "<td class='box-11'>" + captionInnerHtml + "</td>"
				+ "<td class='box-21'>&nbsp;</td>" + "</tr></tbody></table>";
	}

	private void initTable() {
		netAddButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				// final ExtElement element = Ext.get(flexTable.getElement());
				// element.mask();
				(new AddOrModifyIpDialog(SystemView.this, sender,
						AddOrModifyIpDialog.ADD)).show();
			}

		});
		routeAddButton.addClickListener(new ClickListener() {

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				(new AddRouteDialog(SystemView.this, sender)).show();
			}

		});
		serviceGrid.setText(0, 0, "名称");
		serviceGrid.setText(0, 1, "版本号");
		serviceGrid.setText(0, 2, "运行状态");
		serviceGrid.getCellFormatter().setStyleName(0, 0,
				"sys-status-table-head");
		serviceGrid.getCellFormatter().setStyleName(0, 1,
				"sys-status-table-head");
		serviceGrid.getCellFormatter().setStyleName(0, 2,
				"sys-status-table-head");

		netGrid.setText(0, 0, "接口");
		netGrid.setText(0, 1, "IP地址");
		netGrid.setText(0, 2, "子网掩码");
		netGrid.setText(0, 3, "操作");
		netGrid.getCellFormatter().setStyleName(0, 0, "sys-status-table-head");
		netGrid.getCellFormatter().setStyleName(0, 1, "sys-status-table-head");
		netGrid.getCellFormatter().setStyleName(0, 2, "sys-status-table-head");
		netGrid.getCellFormatter().setStyleName(0, 3, "sys-status-table-head");

		routeGrid.setText(0, 0, "目的地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setText(0, 2, "接口");
		routeGrid.setText(0, 3, "网关");

		routeGrid.setText(0, 4, "操作");
		routeGrid.getCellFormatter()
				.setStyleName(0, 0, "sys-status-table-head");
		routeGrid.getCellFormatter()
				.setStyleName(0, 1, "sys-status-table-head");
		routeGrid.getCellFormatter()
				.setStyleName(0, 2, "sys-status-table-head");
		routeGrid.getCellFormatter()
				.setStyleName(0, 3, "sys-status-table-head");
		routeGrid.getCellFormatter()
				.setStyleName(0, 4, "sys-status-table-head");
	}
	/**
	* This method is called immediately after a widget becomes attached to the
	* browser's document.
	*/
	protected void onLoad() {
		
		loadModel();
	}
	
	public void loadModel() {
		// TODO Auto-generated method stub
		systemAllController.getSystemAll(new SysModelAction());
	}
	

	private void fillTable(SystemAll systemAll) {
		
		dateGrid.setText(0, 0, "当前日期:" + systemAll.getDate());
		dateGrid.setText(0, 1, "当前时间:" + systemAll.getTime());
		
		fillServiceTable(systemAll.getService());
		fillNetworkTable(systemAll.getInterfList());
		fillRouterTable(systemAll.getRouteList());

	}

	private void fillServiceTable(Service service) {
	
		serviceGrid.setText(1, 0, service.getName());
		serviceGrid.setText(1, 1, service.getVersion());
		serviceGrid.setText(1, 2, service.getStatus());

	}

	private void fillNetworkTable(List<InterfEntry> interfList) {

		int length = interfList.size();
		netGrid.resize(length + 1, 4);
		for (int i = 0; i < length; i++) {
			
			InterfEntry interfEntry = interfList.get(i);
			netGrid.setText(i + 1, 0, interfEntry.getInterf());
			netGrid.setText(i + 1, 1, interfEntry.getAddress());
			netGrid.setText(i + 1, 2, interfEntry.getMask());
			Button button;
			if (i == 0) {
				this.ip_address = interfEntry.getAddress();
				this.ip_mask = interfEntry.getMask();
				button = new Button("更改");
				button.addClickListener(new ClickListener() {

					public void onClick(Widget sender) {
						// TODO Auto-generated method stub
						(new AddOrModifyIpDialog(SystemView.this, sender,
								AddOrModifyIpDialog.MODIFY)).show();
					}

				});
			} else {
				button = new Button("删除");
				button.addClickListener(new IpClickListener(interfEntry.getAddress()));
			}
			button.setStyleName("sys-button");
			netGrid.setWidget(i + 1, 3, button);
		}
	}

	private void fillRouterTable(List<RouterEntry> routerList) {

		routeGrid.resize(routerList.size() + 1, 5);
		for (int i = 0; i < routerList.size(); i++) {
			RouterEntry routerEntry = routerList.get(i);
			routeGrid.setText(i + 1, 0, routerEntry.getDest());
			routeGrid.setText(i + 1, 1, routerEntry.getMask());
			routeGrid.setText(i + 1, 2, routerEntry.getInterf());
			routeGrid.setText(i + 1, 3, routerEntry.getGateway());
			Button button = new Button("删除");
			button.setStyleName("sys-button");
			button.addClickListener(new RouteClickListener(routerEntry.getDest(), routerEntry.getMask()));
			routeGrid.setWidget(i + 1, 4, button);
		}
	}

	private class IpClickListener implements ClickListener {
		private String ip;

		public IpClickListener(String ip) {
			this.ip = ip;
		}

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub

			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip)) {

				systemAllController.delIp(ip, new VerifyAction(sender, true));
				((Button) sender).setEnabled(false);
			}
		}

	}

	private class RouteClickListener implements ClickListener {
		private String ip;
		private String mask;

		public RouteClickListener(String ip, String mask) {
			this.ip = ip;
			this.mask = mask;
		}

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if (Window.confirm("是否要删除?\n" + "IP地址:" + ip + "\n" + "掩码:" + mask)) {
				systemAllController.delRouter(ip, mask, new VerifyAction(sender, true));
				((Button) sender).setEnabled(false);
			}
		}

	}

	class SysModelAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			if(object instanceof SystemAll){
				fillTable((SystemAll)object);
			}
		}
		
	}
	class VerifyAction extends ViewAction{

		Widget sender;
		boolean reload;
		
		public VerifyAction(Widget sender, boolean reload){
			this.sender = sender;
			this.reload = reload;
		}
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			if (sender != null) {
				((Button) sender).setEnabled(true);
			}
			if(object instanceof String){
				MessageConsole.setText((String)object);
			}
			if(reload){
				loadModel();
			}
		}
		
	}
}
