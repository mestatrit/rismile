package com.risetek.rismile.system.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.utils.UI;
import com.risetek.rismile.system.client.control.SystemAllController;
import com.risetek.rismile.system.client.model.InterfEntry;
import com.risetek.rismile.system.client.model.RouterEntry;
import com.risetek.rismile.system.client.model.Service;
import com.risetek.rismile.system.client.model.SystemDataModel;

public class SystemView extends Composite {

	private static final Images images = (Images) GWT.create(Images.class);
	public interface Images extends ImageBundle {
		AbstractImagePrototype leftCorner();
		AbstractImagePrototype rightCorner();
	}

	final Grid dateGrid = new Grid(1,2);
//	final Grid serviceGrid = new Grid(2,3);
	final Grid netGrid = new Grid(2,4);
	final Grid routeGrid = new Grid(2,5);

	//private int nextHeaderIndex = 0;
	final FlexTable panel = new FlexTable();
	
	SystemAllController control;
	
	public SystemView(SystemAllController control) {
		this.control = control;

		panel.setHeight(Entry.SinkHeight);
		initWidget(panel);
		panel.setWidth("100%");
		//flexTable.setBorderWidth(1);

		setStyleName("rismile-system");

		final StackPanel stackPanel = new StackPanel();
		stackPanel.setWidth("100%");
		stackPanel.setHeight("100%");
		
		stackPanel.setStyleName("stack-panel");
		panel.setWidget(0, 0, stackPanel);
		//panel.getFlexCellFormatter().setColSpan(1, 0, 2);

		final VerticalPanel servicePanel = new VerticalPanel();
		servicePanel.setWidth("100%");
		servicePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		// 产品系统信息界面布局
		// dateGrid.setBorderWidth(1);
		dateGrid.setWidth("40%");
		dateGrid.addStyleName("date-table");
		servicePanel.add(dateGrid);

		/*
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
		netPanel.setWidth("100%");
		netPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		final Button netAddButton = new Button("添加IP", control.new addIPClickListener());
		netAddButton.setStyleName("big-button");
		netPanel.add(netAddButton);

		netGrid.setBorderWidth(1);
		netGrid.setWidth("90%");
		netGrid.setStyleName("grid2-table");
		netGrid.setBorderWidth(1);
		netGrid.setText(0, 0, "接口");
		netGrid.setText(0, 1, "IP地址");
		netGrid.setText(0, 2, "子网掩码");
		netGrid.setText(0, 3, "操作");
		netGrid.setStyleName("status-table");
		netGrid.getCellFormatter().setStyleName(0, 0, "head");
		netGrid.getCellFormatter().setStyleName(0, 1, "head");
		netGrid.getCellFormatter().setStyleName(0, 2, "head");
		netGrid.getCellFormatter().setStyleName(0, 3, "head");
		netPanel.add(netGrid);
		stackPanel.add(netPanel, UI.createHeaderHTML("网络接口"), true);

		// 路由信息界面布局
		final VerticalPanel routePanel = new VerticalPanel();
		routePanel.setWidth("100%");
		routePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		final Button routeAddButton = new Button("添加路由", control.new addRouterClickListener());
		routeAddButton.setStyleName("big-button");
		routePanel.add(routeAddButton);

		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("90%");
		routeGrid.setStyleName("grid2-table");
		routeGrid.setText(0, 0, "目的地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setText(0, 2, "接口");
		routeGrid.setText(0, 3, "网关");
		routeGrid.setText(0, 4, "操作");
		routeGrid.setStyleName("status-table");
		routeGrid.getCellFormatter().setStyleName(0, 0, "head");
		routeGrid.getCellFormatter().setStyleName(0, 1, "head");
		routeGrid.getCellFormatter().setStyleName(0, 2, "head");
		routeGrid.getCellFormatter().setStyleName(0, 3, "head");
		routeGrid.getCellFormatter().setStyleName(0, 4, "head");
		routePanel.add(routeGrid);
		stackPanel.add(routePanel, UI.createHeaderHTML("路由设置"), true);

		// 管理配置界面布局
		final Grid adminGrid = new Grid(2,3);
		//adminGrid.setBorderWidth(1);
		adminGrid.setStyleName("grid1-table");
		stackPanel.add(adminGrid, UI.createHeaderHTML("管理及配置"), true);
		adminGrid.setWidth("100%");

		final Button addAdminButton = new Button("添加管理员");
		addAdminButton.setStyleName("big-button");
		adminGrid.setWidget(0, 0, addAdminButton);
		addAdminButton.addClickListener(control.new addAdminClickListener());
		final Button delAdminButton = new Button("删除管理员", control.new delAdminClickListener());
		delAdminButton.setStyleName("big-button");
		adminGrid.setWidget(0, 1, delAdminButton);
		
		final Button upfileButton = new Button("升级程序",control.new uploadClickListener());
		adminGrid.setWidget(0, 2, upfileButton);
		upfileButton.setStyleName("big-button");

		final Button paraButton = new Button("恢复出厂参数", control.new resotreClickListener());
		adminGrid.setWidget(1, 0, paraButton);
		paraButton.setStyleName("big-button");

		final Button restartButton = new Button("重启设备", control.new resetClickListener());
		adminGrid.setWidget(1, 2, restartButton);
		restartButton.setStyleName("big-button");
		
		
		//----------------------
		//adminGrid.setWidget(1, 0, new HTML("<a href=\"forms/SwingPinger\">设备监视工具</a>"));


	}

/*
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
*/
	/*
	private String createHeaderHTML(Images images, String caption) {
		Grid captionGrid = new Grid(1,2);
		captionGrid.setWidth("100%");

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

		String captionInnerHtml = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>"
				+ innerHtml + "</b></td></tr></table>";

		return "<table align='left' cellpadding='0' cellspacing='0'><tbody>"
				+ "<tr><td class='box-10'>&nbsp;</td>" + "<td class='box-10'>&nbsp;</td>"
				+ "<td class='box-10'>&nbsp;</td>" + "</tr><tr>" + "<td></td>"
				+ "<td class='box-11'>" + captionInnerHtml + "</td>"
				+ "<td></td>" + "</tr></tbody></table>";
	}
	*/


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
	public String ip_address;
	public String ip_mask;

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
				this.ip_address = interfEntry.getAddress();
				this.ip_mask = interfEntry.getMask();
				button = new Button("更改", control.new modifyIPClickListener());
			} else {
				button = new Button("删除", control.new IpClickListener(interfEntry.getAddress()));
			}
			button.setStyleName("sys-button");
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
			Button button = new Button("删除", control.new RouteClickListener(routerEntry.getDest(), routerEntry.getMask()));
			button.setStyleName("sys-button");
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
