package com.risetek.rismile.client.view;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.SystemController;
import com.risetek.rismile.client.model.InterfEntry;
import com.risetek.rismile.client.model.RouterEntry;
import com.risetek.rismile.client.model.SystemDataModel;
import com.risetek.rismile.client.utils.UI;

public class SystemView extends ResizeComposite  implements IRisetekView {

	final Grid dateGrid = new Grid(1,2);
//	final Grid serviceGrid = new Grid(2,3);
	final Grid netGrid = new Grid(2,4);
	final Grid routeGrid = new Grid(2,5);

	final VerticalPanel panel = new VerticalPanel();
	final Grid adminGrid = new Grid(2,3);
	
	final Button netAddButton;	
	final Button routeAddButton;
	final Button addAdminButton;
	final Button delAdminButton;
	final Button upfileButton;
	final Button paraButton;
	final Button restartButton;
//	final StackPanel stackPanel = new StackPanel();
	final StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.PX);
	
	public SystemView() {
		/*
		panel.setHeight(Entry.SinkHeight);
		initWidget(panel);
		panel.setWidth("100%");
		stackPanel.setWidth("100%");
		stackPanel.setHeight("100%");
		panel.add(stackPanel);
		*/
		stackPanel.setHeight(Entry.SinkHeight);;
		stackPanel.setWidth("100%");
		initWidget(stackPanel);

		setStyleName("system");
		//panel.getFlexCellFormatter().setColSpan(1, 0, 2);

		// 网络配置信息界面布局
		final VerticalPanel netPanel = new VerticalPanel();
//		stackPanel.add(netPanel, UI.createHeaderHTML("网络接口"), true);
		stackPanel.add(netPanel, UI.createHeaderHTML("网络接口"), true, 33);
//		stackPanel.add(netPanel, "网络接口", true, 33);
		netPanel.setWidth("100%");
		netPanel.setHeight("100%");
		netPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		netPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		netAddButton = new Button("添加地址", new SystemController.addIPClickHandler());
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
//		stackPanel.add(routePanel, UI.createHeaderHTML("路由设置"), true);
		stackPanel.add(routePanel, UI.createHeaderHTML("路由设置"), true, 33);
//		stackPanel.add(routePanel, "路由设置", true, 33);
		routePanel.setWidth("100%");
		routePanel.setHeight("100%");
		routePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		routePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		routeAddButton = new Button("添加路由", new SystemController.addRouterClickHandler());
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
		mPanel.add(adminGrid);
		//adminGrid.setBorderWidth(1);
		mPanel.setStyleName("manage");
//		stackPanel.add(mPanel, UI.createHeaderHTML("管理及配置"), true);
		stackPanel.add(mPanel, UI.createHeaderHTML("管理及配置"), true, 33);
//		stackPanel.add(mPanel, "管理及配置", true, 33);
		adminGrid.setWidth("80%");

		addAdminButton = new Button("添加管理员");
		adminGrid.setWidget(0, 0, addAdminButton);
		addAdminButton.addClickHandler(new SystemController.addAdminClickHandler());
		delAdminButton = new Button("删除管理员", new SystemController.delAdminClickHandler());
		adminGrid.setWidget(0, 1, delAdminButton);
		
		upfileButton = new Button("升级程序",new SystemController.uploadClickHandler());
		adminGrid.setWidget(0, 2, upfileButton);

		paraButton = new Button("恢复出厂参数", new SystemController.resotreClickHandler());
		adminGrid.setWidget(1, 0, paraButton);

		restartButton = new Button("重启设备", new SystemController.resetClickHandler());
		adminGrid.setWidget(1, 2, restartButton);
	}

	protected void onLoad() {
		SystemController.load();
	}

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
				button = new Button("更改", new SystemController.modifyIPClickHandler(interfEntry.getAddress(), interfEntry.getMask()));
			} else {
				button = new Button("删除", new SystemController.IpClickHandler(interfEntry.getAddress()));
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
			Button button = new Button("删除", new SystemController.RouteClickHandler(routerEntry.getDest(), routerEntry.getMask()));
			routeGrid.setWidget(i + 1, 4, button);
		}
	}
	public void render(SystemDataModel data)
	{
		renderNetworkTable(data.getInterfList());
		renderRouterTable(data.getRouteList());
		if( data.rmon )
		{
			final Button rmonButton = new Button("下载监控程序", new SystemController.rmonClickHandler());
			rmonButton.addStyleName("config-Button");
			adminGrid.setWidget(1, 1, rmonButton);
		}
	}
	
	public void disablePrivate() {
		netAddButton.setEnabled(false);
		routeAddButton.setEnabled(false);
		addAdminButton.setEnabled(false);
		delAdminButton.setEnabled(false);
		upfileButton.setEnabled(false);
		paraButton.setEnabled(false);
		restartButton.setEnabled(false);
		setEditButtonPrivate(false);
	}

	public void enablePrivate() {
		netAddButton.setEnabled(true);
		routeAddButton.setEnabled(true);
		addAdminButton.setEnabled(true);
		delAdminButton.setEnabled(true);
		upfileButton.setEnabled(true);
		paraButton.setEnabled(true);
		restartButton.setEnabled(true);
		setEditButtonPrivate(true);
	}
	
	private void setEditButtonPrivate(boolean enabled){
		for(int i=0;i<netGrid.getRowCount();i++){
			if(i==0){
				continue;
			}
			Widget widget = netGrid.getWidget(i, 3);
			if(widget instanceof Button){
				Button button = (Button)widget;
				button.setEnabled(enabled);
			}
		}
		for(int i=0;i<routeGrid.getRowCount();i++){
			if(i==0){
				continue;
			}
			Widget widget = routeGrid.getWidget(i, 4);
			if(widget instanceof Button){
				Button button = (Button)widget;
				button.setEnabled(enabled);
			}
		}
	}

}
