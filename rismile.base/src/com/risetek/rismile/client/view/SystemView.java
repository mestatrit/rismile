package com.risetek.rismile.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.SystemController;
import com.risetek.rismile.client.model.InterfEntry;
import com.risetek.rismile.client.model.RouterEntry;
import com.risetek.rismile.client.model.SystemDataModel;
import com.risetek.rismile.client.utils.UI;

public class SystemView extends Composite {

	final Grid dateGrid = new Grid(1,2);
//	final Grid serviceGrid = new Grid(2,3);
	final Grid netGrid = new Grid(2,4);
	final Grid routeGrid = new Grid(2,5);

	final VerticalPanel panel = new VerticalPanel();
	
	SystemController control;
	
	public SystemView(SystemController control) {
		this.control = control;

		panel.setHeight(Entry.SinkHeight);
		initWidget(panel);
		panel.setWidth("100%");

		setStyleName("system");

		final StackPanel stackPanel = new StackPanel();
		stackPanel.setWidth("100%");
		stackPanel.setHeight("100%");
		
		panel.add(stackPanel);
		//panel.getFlexCellFormatter().setColSpan(1, 0, 2);

		/*
		final VerticalPanel servicePanel = new VerticalPanel();
		servicePanel.setWidth("100%");
		servicePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		// 产品系统信息界面布局
		// dateGrid.setBorderWidth(1);
		dateGrid.setWidth("40%");
		dateGrid.addStyleName("date-table");
		servicePanel.add(dateGrid);

		// 服务信息界面布局
		servicePanel.add(serviceGrid);
		//serviceGrid.setBorderWidth(1);
		serviceGrid.setWidth("90%");
		serviceGrid.setStyleName("status-table");
		serviceGrid.setBorderWidth(1);
		serviceGrid.setText(0, 0, "名称");
		serviceGrid.setText(0, 1, "版本号");
		serviceGrid.setText(0, 2, "运行状态");
		serviceGrid.setStyleName("status-table");
		serviceGrid.getCellFormatter().setStyleName(0, 0, "head");
		serviceGrid.getCellFormatter().setStyleName(0, 1, "head");
		serviceGrid.getCellFormatter().setStyleName(0, 2, "head");
		stackPanel.add(servicePanel, createHeaderHTML(images, "系统服务"), true);
		*/
		
		// 网络配置信息界面布局
		final VerticalPanel netPanel = new VerticalPanel();
		stackPanel.add(netPanel, UI.createHeaderHTML("网络接口"), true);
		netPanel.setWidth("100%");
		netPanel.setHeight("100%");
		netPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		netPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		final Button netAddButton = new Button("添加地址", control.new addIPClickHandler());
		netPanel.setStyleName("gray");
		netGrid.setBorderWidth(1);
		netGrid.setWidth("85%");
		netGrid.setText(0, 0, "接口");
		netGrid.setText(0, 1, "IP地址");
		netGrid.setText(0, 2, "子网掩码");
		netGrid.setWidget(0, 3, netAddButton);
		netGrid.getColumnFormatter().setWidth(0	, "15%");
		netGrid.getColumnFormatter().setWidth(1	, "34%");
		netGrid.getColumnFormatter().setWidth(2	, "34%");
		netGrid.getColumnFormatter().setWidth(3	, "17%");
		netGrid.getRowFormatter().setStyleName(0, "head");
		netPanel.add(netGrid);

		// 路由信息界面布局
		final VerticalPanel routePanel = new VerticalPanel();
		stackPanel.add(routePanel, UI.createHeaderHTML("路由设置"), true);
		routePanel.setWidth("100%");
		routePanel.setHeight("100%");
		routePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		routePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		final Button routeAddButton = new Button("添加路由", control.new addRouterClickHandler());
		routePanel.setStyleName("gray");
		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("90%");
		routeGrid.setText(0, 0, "目的地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setText(0, 2, "接口");
		routeGrid.setText(0, 3, "网关");
		routeGrid.setWidget(0, 4, routeAddButton);
		routeGrid.getColumnFormatter().setWidth(0 , "25%");
		routeGrid.getColumnFormatter().setWidth(1 , "25%");
		routeGrid.getColumnFormatter().setWidth(2 , "10%");
		routeGrid.getColumnFormatter().setWidth(3 , "25%");
		routeGrid.getColumnFormatter().setWidth(4 , "15%");
		routeGrid.getRowFormatter().setStyleName(0, "head");
		routePanel.add(routeGrid);

		// 管理配置界面布局
		final VerticalPanel mPanel = new VerticalPanel();
		mPanel.setWidth("100%");
		mPanel.setHeight("100%");
		mPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		final Grid adminGrid = new Grid(2,3);
		mPanel.add(adminGrid);
		//adminGrid.setBorderWidth(1);
		mPanel.setStyleName("manage");
		stackPanel.add(mPanel, UI.createHeaderHTML("管理及配置"), true);
		adminGrid.setWidth("80%");

		final Button addAdminButton = new Button("添加管理员");
		adminGrid.setWidget(0, 0, addAdminButton);
		addAdminButton.addClickHandler(control.new addAdminClickHandler());
		final Button delAdminButton = new Button("删除管理员", control.new delAdminClickHandler());
		adminGrid.setWidget(0, 1, delAdminButton);
		
		final Button upfileButton = new Button("升级程序",control.new uploadClickHandler());
		adminGrid.setWidget(0, 2, upfileButton);

		final Button paraButton = new Button("恢复出厂参数", control.new resotreClickHandler());
		adminGrid.setWidget(1, 0, paraButton);

		final Button restartButton = new Button("重启设备", control.new resetClickHandler());
		adminGrid.setWidget(1, 2, restartButton);
		
		
		//----------------------
		//adminGrid.setWidget(1, 0, new HTML("<a href=\"forms/SwingPinger\">设备监视工具</a>"));


	}

	protected void onLoad() {
		control.load();
	}
	/*
	private void renderServiceTable(Service service) {
		dateGrid.setText(0, 0, "当前日期:" + data.getDate());
		dateGrid.setText(0, 1, "当前时间:" + data.getTime());
		serviceGrid.setText(1, 0, service.getName());
		serviceGrid.setText(1, 1, service.getVersion());
		serviceGrid.setText(1, 2, service.getStatus());

	}
	*/
	//public String ip_address;
	//public String ip_mask;

	private void renderNetworkTable(List<InterfEntry> interfList) {

		int length = interfList.size();
		netGrid.resize(length + 1, 4);
		for (int i = 0; i < length; i++) {
			
			InterfEntry interfEntry = interfList.get(i);
			netGrid.setText(i + 1, 0, interfEntry.getInterf());
			netGrid.setText(i + 1, 1, interfEntry.getAddress());
			netGrid.setText(i + 1, 2, interfEntry.getMask());
			Button button;
			if (i == 0) {
				button = new Button("更改", control.new modifyIPClickHandler(interfEntry.getAddress(), interfEntry.getMask()));
			} else {
				button = new Button("删除", control.new IpClickHandler(interfEntry.getAddress()));
			}
			netGrid.setWidget(i + 1, 3, button);
		}
	}

	private void renderRouterTable(List<RouterEntry> routerList) {

		routeGrid.resize(routerList.size() + 1, 5);
		for (int i = 0; i < routerList.size(); i++) {
			RouterEntry routerEntry = routerList.get(i);
			routeGrid.setText(i + 1, 0, routerEntry.getDest());
			routeGrid.setText(i + 1, 1, routerEntry.getMask());
			routeGrid.setText(i + 1, 2, routerEntry.getInterf());
			routeGrid.setText(i + 1, 3, routerEntry.getGateway());
			Button button = new Button("删除", control.new RouteClickHandler(routerEntry.getDest(), routerEntry.getMask()));
			routeGrid.setWidget(i + 1, 4, button);
		}
	}
	public void render(SystemDataModel data)
	{
		//renderServiceTable(data.getService());
		renderNetworkTable(data.getInterfList());
		renderRouterTable(data.getRouteList());
	}
}
