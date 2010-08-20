package com.risetek.rismile.client.view;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.control.SystemController;
import com.risetek.rismile.client.model.InterfEntry;
import com.risetek.rismile.client.model.RouterEntry;
import com.risetek.rismile.client.model.SystemDataModel;
import com.risetek.rismile.client.ui.Stick;
import com.risetek.rismile.client.utils.HorizontalTitlePanel;
import com.risetek.rismile.client.utils.KEY;

public class SystemView extends ViewComposite implements IRisetekView {

	private final StackLayoutPanel stackPanel = new StackLayoutPanel(Unit.PX);
	
	private managePanel manage;
	private routePanel route;
	private networkPanel network;
	public SystemView() {
		stackPanel.setHeight("100%");
		stackPanel.setWidth("100%");
		stackPanel.setStyleName("system");
	
		addLeftSide(stackPanel);
		addRightSide(new SystemSider());

		// 网络配置信息界面布局
		network = new networkPanel();
		stackPanel.add(network, new HorizontalTitlePanel("网络配置", true), UIConfig.ITitleHeight);
		// 路由信息界面布局
		route = new routePanel();
		stackPanel.add(route, new HorizontalTitlePanel("路由设置", false), UIConfig.ITitleHeight);
		// 管理配置界面布局
		manage = new managePanel();
		stackPanel.add(manage, new HorizontalTitlePanel("管理配置", false), UIConfig.ITitleHeight);
		
		stackPanel.addSelectionHandler(new SelectionHandler<Integer>(){

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				// Widget widget = stackPanel.getWidget(event.getSelectedItem());
				// TODO:
				// 做点什么?
			}});
		
	}

	@Override
	protected void onLoad() {
		SystemController.load();
	}

	public void render(SystemDataModel data) {
		if( network != null )
			network.renderNetworkTable(data.getInterfList());
		if( route != null )
			route.renderRouterTable(data.getRouteList());
		if( manage != null )
			manage.rmonButton.setVisible(data.rmon);
	}

	public void disablePrivate() {
		for( Widget widget:stackPanel )
			((IRisetekView)widget).disablePrivate();
	}

	public void enablePrivate() {
		for( Widget widget:stackPanel )
			((IRisetekView)widget).enablePrivate();
	}

	@Override
	public void ProcessControlKey(int keyCode, boolean alt) {

		switch (keyCode) {
		case KEY.UP:
			int index1 = stackPanel.getVisibleIndex();
			if (index1 > 0) {
				stackPanel.showWidget(index1 - 1);
			}
			break;
		case KEY.DOWN:
			int count = stackPanel.getWidgetCount();
			int index2 = stackPanel.getVisibleIndex();
			if (index2 < count - 1) {
				stackPanel.showWidget(index2 + 1);
			}
			break;
		default:
			break;
		}
		// 隐藏的情况下，不处理直接键盘消息。
		IRisetekView widget = (IRisetekView)stackPanel.getVisibleWidget();
		widget.ProcessControlKey(keyCode, alt);
	}

	private class SystemSider extends Composite {
		
		private final VerticalPanel spanel = new VerticalPanel();
		
		public SystemSider() {
			//spanel.setBorderWidth(1);
			spanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);

			spanel.add(new Stick("info", "上下键能用来滑动选择配置区。"));
			spanel.add(new Stick("info", "配置区的按钮在特权登录后才有效。"));
			spanel.add(new Stick("info", "Alt+（ 快捷键）与鼠标点击按钮具有同样的效果。"));

			spanel.add(new Stick("info", "能够在同一个网络接口上配置多个地址。"));
			spanel.add(new Stick("info", "网络接口的第一个网络地址只能修改，不能删除。"));
			spanel.add(new Stick("info", "可以设定多条路由，包括主机路由。"));
			spanel.add(new Stick("info", "网络地址和路由配置后立即生效，无需重启设备。"));
			spanel.setWidth("100%");
			initWidget(spanel);
		}
	}

	// 网络配置信息界面布局
	private class networkPanel extends Composite implements IRisetekView {
		private final Grid netGrid = new Grid(2, 4);
		private final Button netAddButton  = new Button("添加地址(M)",
				new SystemController.addIPClickHandler());

		public networkPanel() {
			final VerticalPanel vPanel = new VerticalPanel();
			vPanel.setWidth("100%");
			vPanel.setHeight("100%");
			vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			vPanel.setStyleName("gray");
			netGrid.setBorderWidth(1);
			netGrid.setWidth("85%");
			netGrid.setText(0, 0, "接口");
			netGrid.setText(0, 1, "IP地址");
			netGrid.setText(0, 2, "子网掩码");
			netGrid.setWidget(0, 3, netAddButton);
			ColumnFormatter formatter = netGrid.getColumnFormatter(); 
			formatter.setWidth(0, "15%");
			formatter.setWidth(1, "34%");
			formatter.setWidth(2, "34%");
			formatter.setWidth(3, "17%");
			netGrid.getRowFormatter().setStyleName(0, "head");
			vPanel.add(netGrid);
			initWidget(vPanel);
		}
		
		public void renderNetworkTable(List<InterfEntry> interfList) {

			int length = interfList.size();
			netGrid.resize(length + 1, 4);
			for (int i = 0; i < length; i++) {

				InterfEntry interfEntry = interfList.get(i);
				netGrid.setText(i + 1, 0, interfEntry.getInterf());
				netGrid.setText(i + 1, 1, interfEntry.getAddress());
				netGrid.setText(i + 1, 2, interfEntry.getMask());
				Button button;
				String buttonText;
				if (i < 11) {
					int key = i + 1;
					if (i == 0) {
						buttonText = "更改(" + key + ")";
					} else {
						buttonText = "删除(" + key + ")";
					}
				} else {
					buttonText = "删除";
				}
				if (i == 0) {
					button = new Button(buttonText,
							new SystemController.modifyIPClickHandler(interfEntry
									.getAddress(), interfEntry.getMask()));
				} else {
					button = new Button(buttonText,
							new SystemController.IpClickHandler(interfEntry
									.getAddress()));
				}
				button.setEnabled(false);
				netGrid.setWidget(i + 1, 3, button);
			}
		}

		private void netGridClickButton(int i) {
			if (i > netGrid.getRowCount() - 1) {
				return;
			}
			Button btn = (Button) netGrid.getWidget(i, 3);
			if (btn.isEnabled()) {
				btn.click();
			}
		}

		@Override
		public void ProcessControlKey(int keyCode, boolean alt) {
			switch (keyCode) {
			case KEY.K1:
			case KEY.K2:
			case KEY.K3:
			case KEY.K4:
			case KEY.K5:
			case KEY.K6:
			case KEY.K7:
			case KEY.K8:
			case KEY.K9:
				netGridClickButton(keyCode - KEY.K0);
				break;
			case KEY.M:
				if (netAddButton.isEnabled()) {
					netAddButton.click();
				}
				break;
			}
		}

		private void setEditButtonPrivate(boolean enabled) {
			for (int i = 0; i < netGrid.getRowCount(); i++) {
				if (i == 0) {
					continue;
				}
				Widget widget = netGrid.getWidget(i, 3);
				if (widget instanceof Button) {
					Button button = (Button) widget;
					button.setEnabled(enabled);
				}
			}
		}
		
		
		@Override
		public void disablePrivate() {
			netAddButton.setEnabled(false);
			setEditButtonPrivate(false);
		}

		@Override
		public void enablePrivate() {
			netAddButton.setEnabled(true);
			setEditButtonPrivate(true);
		}
	}
	
	private class routePanel extends Composite implements IRisetekView {
		private final Grid routeGrid = new Grid(2, 5);
		private final Button routeAddButton = new Button("添加路由(O)",
				new SystemController.addRouterClickHandler());
		public routePanel() {
			// 路由信息界面布局
			final VerticalPanel vPanel = new VerticalPanel();
			vPanel.setWidth("100%");
			vPanel.setHeight("100%");
			vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

			vPanel.setStyleName("gray");
			routeGrid.setBorderWidth(1);
			routeGrid.setWidth("90%");
			routeGrid.setText(0, 0, "目的地址");
			routeGrid.setText(0, 1, "掩码");
			routeGrid.setText(0, 2, "接口");
			routeGrid.setText(0, 3, "网关");
			routeGrid.setWidget(0, 4, routeAddButton);
			ColumnFormatter formatter = routeGrid.getColumnFormatter(); 
			formatter.setWidth(0, "25%");
			formatter.setWidth(1, "25%");
			formatter.setWidth(2, "10%");
			formatter.setWidth(3, "25%");
			formatter.setWidth(4, "15%");
			routeGrid.getRowFormatter().setStyleName(0, "head");
			vPanel.add(routeGrid);
			initWidget(vPanel);
		}

		public void renderRouterTable(List<RouterEntry> routerList) {

			routeGrid.resize(routerList.size() + 1, 5);
			for (int i = 0; i < routerList.size(); i++) {
				RouterEntry routerEntry = routerList.get(i);
				routeGrid.setText(i + 1, 0, routerEntry.getDest());
				routeGrid.setText(i + 1, 1, routerEntry.getMask());
				routeGrid.setText(i + 1, 2, routerEntry.getInterf());
				routeGrid.setText(i + 1, 3, routerEntry.getGateway());
				String buttonText;
				if (i < 11) {
					int key = i + 1;
					buttonText = "删除(" + key + ")";
				} else {
					buttonText = "删除";
				}
				Button button = new Button(buttonText,
						new SystemController.RouteClickHandler(routerEntry
								.getDest(), routerEntry.getMask()));
				button.setEnabled(false);
				routeGrid.setWidget(i + 1, 4, button);
			}
		}

		
		private void routeGridClickButton(int i) {
			if (i > routeGrid.getRowCount() - 1) {
				return;
			}
			Button btn = (Button) routeGrid.getWidget(i, 4);
			if (btn.isEnabled()) {
				btn.click();
			}
		}
		
		@Override
		public void ProcessControlKey(int keyCode, boolean alt) {
			switch(keyCode) {
			case KEY.O:
				if (routeAddButton.isEnabled()) {
					routeAddButton.click();
				}
				break;
			case KEY.K1:
			case KEY.K2:
			case KEY.K3:
			case KEY.K4:
			case KEY.K5:
			case KEY.K6:
			case KEY.K7:
			case KEY.K8:
			case KEY.K9:
				routeGridClickButton(keyCode - KEY.K0);
				break;			}
		}

		private void setEditButtonPrivate(boolean enabled) {
			for (int i = 0; i < routeGrid.getRowCount(); i++) {
				if (i == 0) {
					continue;
				}
				Widget widget = routeGrid.getWidget(i, 4);
				if (widget instanceof Button) {
					Button button = (Button) widget;
					button.setEnabled(enabled);
				}
			}
		}

		@Override
		public void disablePrivate() {
			routeAddButton.setEnabled(false);
			setEditButtonPrivate(false);
		}

		@Override
		public void enablePrivate() {
			routeAddButton.setEnabled(true);
			setEditButtonPrivate(true);
		}
	}
	
	private class managePanel extends Composite implements IRisetekView {
		private final Grid adminGrid = new Grid(2, 3);
		
		private final Button addAdminButton = new Button("添加管理员(A)", new SystemController.addAdminClickHandler());
		private final Button delAdminButton = new Button("删除管理员(Del)", new SystemController.delAdminClickHandler());
		private final Button upfileButton = new Button("升级程序(U)", new SystemController.uploadClickHandler());
		private final Button paraButton = new Button("恢复出厂参数(B)", new SystemController.resotreClickHandler());
		private final Button restartButton = new Button("重启设备(R)", new SystemController.resetClickHandler());
		public Button rmonButton = new Button("下载监控程序(S)",new SystemController.rmonClickHandler());

		public managePanel() {
			// 管理配置界面布局
			final VerticalPanel vPanel = new VerticalPanel();
			vPanel.setWidth("100%");
			vPanel.setHeight("100%");
			vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			vPanel.add(adminGrid);
			// adminGrid.setBorderWidth(1);
			vPanel.setStyleName("manage");
			adminGrid.setWidth("80%");
			adminGrid.setWidget(0, 0, addAdminButton);
			adminGrid.setWidget(0, 1, delAdminButton);
			adminGrid.setWidget(0, 2, upfileButton);
			adminGrid.setWidget(1, 0, paraButton);
			adminGrid.setWidget(1, 2, restartButton);

			rmonButton.addStyleName("config-Button");
			adminGrid.setWidget(1, 1, rmonButton);
			rmonButton.setVisible(false);
			
			initWidget(vPanel);
		}

		@Override
		public void ProcessControlKey(int keyCode, boolean alt) {
			switch (keyCode) {
			case KEY.DELETE:
				if (delAdminButton.isEnabled()) {
					delAdminButton.click();
				}
				break;
			case KEY.DEL:
				if (delAdminButton.isEnabled()) {
					delAdminButton.click();
				}
				break;
			case KEY.A:
				if (addAdminButton.isEnabled()) {
					addAdminButton.click();
				}
				break;
			case KEY.B:
				if (paraButton.isEnabled()) {
					paraButton.click();
				}
				break;
			case KEY.U:
				if (upfileButton.isEnabled()) {
					upfileButton.click();
				}
				break;
			case KEY.R:
				if (restartButton.isEnabled()) {
					restartButton.click();
				}
				break;
			case KEY.S:
				if (rmonButton.isEnabled()) {
					rmonButton.click();
				}
				break;
			}
		}

		@Override
		public void disablePrivate() {
			addAdminButton.setEnabled(false);
			delAdminButton.setEnabled(false);
			upfileButton.setEnabled(false);
			paraButton.setEnabled(false);
			restartButton.setEnabled(false);
			if( rmonButton.isVisible() )
				rmonButton.setEnabled(false);
		}

		@Override
		public void enablePrivate() {
			addAdminButton.setEnabled(true);
			delAdminButton.setEnabled(true);
			upfileButton.setEnabled(true);
			paraButton.setEnabled(true);
			restartButton.setEnabled(true);
			if( rmonButton.isVisible() )
				rmonButton.setEnabled(true);
		}
	}
	
}
