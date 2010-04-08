package com.risetek.rismile.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
//import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.control.SystemController;
import com.risetek.rismile.client.model.InterfEntry;
import com.risetek.rismile.client.model.RouterEntry;
import com.risetek.rismile.client.model.SystemDataModel;

public class SystemView extends ResizeComposite {
	interface localUiBinder extends UiBinder<Widget, SystemView> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);

	SystemController control;
	
	@UiField
	Button addManager;

	@UiField
	Button delManager;

	@UiField
	Button upgrade;

	@UiField
	Button monitor;
	
	@UiField
	Button restore;
	
	@UiField
	Button reset;
	
	@UiField
	Grid netGrid;
	@UiField
	Grid routeGrid;

	public interface SystemViewStyle extends CssResource {
	    String head();
	}
	
	@UiField public static SystemViewStyle style;
	
	public SystemView(SystemController control) {
		this.control = control;
		Widget w = uiBinder.createAndBindUi(this);
//		w.setHeight(Entry.SinkHeight); //wangx
		initWidget(w);
		
		addManager.addClickHandler(control.new addAdminClickHandler());
		delManager.addClickHandler(control.new delAdminClickHandler());
		upgrade.addClickHandler(control.new uploadClickHandler());
		monitor.addClickHandler(control.new rmonClickHandler());
		restore.addClickHandler(control.new resotreClickHandler());
		reset.addClickHandler(control.new resetClickHandler());
		
		netGrid.resize(1, 4);
		netGrid.setText(0, 0, "接口");
		netGrid.setText(0, 1, "IP地址");
		netGrid.setText(0, 2, "子网掩码");
		netGrid.setWidget(0, 3, new Button("添加地址", control.new addIPClickHandler()));
		netGrid.getRowFormatter().setStyleName(0, style.head());
		netGrid.getColumnFormatter().setWidth(0	, "15%");
		netGrid.getColumnFormatter().setWidth(1	, "34%");
		netGrid.getColumnFormatter().setWidth(2	, "34%");
		
		routeGrid.resize(1, 5);
		routeGrid.setText(0, 0, "目的地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setText(0, 2, "接口");
		routeGrid.setText(0, 3, "网关");
		routeGrid.getRowFormatter().setStyleName(0, style.head());

		routeGrid.getColumnFormatter().setWidth(0 , "25%");
		routeGrid.getColumnFormatter().setWidth(1 , "25%");
		routeGrid.getColumnFormatter().setWidth(2 , "10%");
		routeGrid.getColumnFormatter().setWidth(3 , "25%");
		
		routeGrid.setWidget(0, 4, new Button("添加路由", control.new addRouterClickHandler()));
	}

	protected void onLoad() {
		control.load();
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
		renderNetworkTable(data.getInterfList());
		renderRouterTable(data.getRouteList());
		if (data.rmon) {
			monitor.setVisible(true);
		}
		
	}
}
