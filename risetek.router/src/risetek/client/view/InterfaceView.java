package risetek.client.view;

import java.util.ArrayList;

import risetek.client.control.IfController;
import risetek.client.model.DialerInterfaceData;
import risetek.client.model.ifaceData;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;
import com.risetek.rismile.client.utils.UI;

public class InterfaceView extends Composite {

	final FlexTable routeGrid = new FlexTable();

	final VerticalPanel panel = new VerticalPanel();
	
	final Label userLabel = new Label();
	final Label passwordLabel = new Label("未设置");	// 我们不显示这个数据
	final Label mtuLabel = new Label();
	
	final CheckBox chapmdCheckBox = new CheckBox("CHAP认证");
	final CheckBox papCheckBox = new CheckBox("PAP 认证");
	final CheckBox natCheckBox = new CheckBox("NAT");
	//final CheckBox ondemandCheckBox = new CheckBox();
	final CheckBox mppcCheckBox = new CheckBox("MPPC 压缩");
	final CheckBox physCheckBox = new CheckBox("缺省链路");
	final CheckBox defaultRouterCheckBox = new CheckBox("作为缺省路由");
	
	IfController control;
	
	Grid statGrid = new Grid(4,2);
	
	public InterfaceView(IfController control) {
		this.control = control;
		panel.setHeight(Entry.SinkHeight);
		initWidget(panel);
		panel.setWidth("100%");

		setStyleName("system");

		final StackPanel stackPanel = new StackPanel();
		
		stackPanel.setWidth("100%");
		stackPanel.setHeight("100%");
		
		//stackPanel.setStyleName("stack-panel");
		panel.add(stackPanel);
		//panel.getFlexCellFormatter().setColSpan(1, 0, 2);

		// --- 网络状态显示
		FlexTable flexTable = new FlexTable();
		//flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.setStyleName("router-config");
		flexTable.getColumnFormatter().setWidth(0, "70%");
		flexTable.getColumnFormatter().setWidth(1, "30%");

		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);

		statGrid.setWidth("80%");
		//statGrid.setBorderWidth(1);
		statGrid.setStyleName("table");
		statGrid.getColumnFormatter().setStyleName(0, "head");

		statGrid.getColumnFormatter().setWidth(0, "30%");
		statGrid.getColumnFormatter().setWidth(1, "70%");
		
		
		statGrid.setText(0, 0, "接口状态");
		statGrid.setText(1, 0, "链路状态");
		statGrid.setText(2, 0, "链路类型");
		statGrid.setText(3, 0, "接口地址");
		//statGrid.setText(2, 0, "LinkStat");

		Grid operator = new Grid(3,1);
		operator.setStyleName("operator-table");
		operator.setWidget(0, 0, new Button("断开连接", control.new DisconnectListener()));
		operator.setWidget(1, 0, new Button("建立连接", control.new ConnectListener()));
		operator.setWidget(2, 0, new Button("更新状态", control.new ReloadStatListener()));
		// flexTable.setWidget(2, 1, new Button("删除接口", control.new RemoveInterfaceClick()));
		
		flexTable.setWidget(0,0, statGrid);
		flexTable.setWidget(0,1, operator);

		stackPanel.add(flexTable, UI.createHeaderHTML("运行状态"), true);
		
		
		// 网络配置信息界面布局--------------------------------------------

		flexTable = new FlexTable();
		// flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.getColumnFormatter().setWidth(0, "60%");
		flexTable.getColumnFormatter().setWidth(1, "40%");
		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.setStyleName("router-config");
		
		final Grid pppGrid = new Grid(3,3);
		//pppGrid.setBorderWidth(1);
		pppGrid.setStyleName("table");
		pppGrid.setWidth("90%");
		pppGrid.getColumnFormatter().setStyleName(0, "head");

		pppGrid.getColumnFormatter().setWidth(0, "20%");
		pppGrid.getColumnFormatter().setWidth(1, "60%");
		pppGrid.getColumnFormatter().setWidth(2, "20%");
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setWidget(0, 2, new Button("修改", control.new ModifyLCPUserButtonClick()));
		
		pppGrid.setText(1, 0, "密码:");
		pppGrid.setWidget(1, 1, passwordLabel);
		pppGrid.setWidget(1, 2, new Button("修改", control.new ModifyLCPPasswordButtonClick()));

		pppGrid.setText(2, 0, "MTU:");
		pppGrid.setWidget(2, 1, mtuLabel);
		pppGrid.setWidget(2, 2, new Button("修改", control.new ModifyMTUButtonClick()));
		
		flexTable.setWidget(0, 0, pppGrid);
		
		FlexTable tempGrid = new FlexTable();
		//tempGrid.setBorderWidth(1);
		tempGrid.setWidth("90%");
		tempGrid.getColumnFormatter().setWidth(0, "50%");
		tempGrid.setStyleName("checkbox-table");
		
		//tempGrid.setWidget(0,1, authGrid);

		//tempGrid.getFlexCellFormatter().setColSpan(0, 0, 2);
		//tempGrid.setText(0,0,"认证方式:");
		
		tempGrid.setWidget(0,0, chapmdCheckBox);
		chapmdCheckBox.addClickHandler(control.new ModifyCHAPMD5Listener());

		tempGrid.setWidget(0,1, papCheckBox);
		papCheckBox.addClickHandler(control.new ModifyPAPListener());

		tempGrid.setWidget(1,0, natCheckBox);
		natCheckBox.addClickHandler(control.new ModifyNATListener());
		
		tempGrid.setWidget(1,1, mppcCheckBox);
		mppcCheckBox.addClickHandler(control.new ModifyMPPCListener());
		
		tempGrid.setWidget(2,0, defaultRouterCheckBox);
		defaultRouterCheckBox.addClickHandler(control.new ModifyDefaultROUTEListener());
		
		tempGrid.setWidget(2,1, physCheckBox);
		physCheckBox.addClickHandler(control.new ModifyDefaultPhysListener());

		flexTable.setWidget(0, 1, tempGrid);
		
		stackPanel.add(flexTable, UI.createHeaderHTML("基本配置"), true);

		// 接口路由设置
		flexTable = new FlexTable();
		//flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.setStyleName("router-config");
		flexTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		flexTable.setWidget(0, 0, routeGrid);
		
		//routeGrid.setBorderWidth(1);
		routeGrid.setWidth("80%");
		routeGrid.setStyleName("table");
		routeGrid.getRowFormatter().setStyleName(0, "head");

		routeGrid.getColumnFormatter().setWidth(0, "40%");
		routeGrid.getColumnFormatter().setWidth(1, "40%");

		routeGrid.setText(0, 0, "目标地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setWidget(0, 2, new Button("添加路由", control.new AddInterfaceRouteButtonClick()));

		stackPanel.add(flexTable, UI.createHeaderHTML("路由设置"), true);

		// 管理配置界面布局
		/*
		flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.setStyleName("router-config");
		stackPanel.add(flexTable, UI.createHeaderHTML("高级配置"), true);
		new AdvancedView(flexTable);
		*/
	}

	public void onLoad() {
		control.load();
	}

	public void render(DialerInterfaceData data)
	{
		statGrid.setText(0,1, data.statdata.BundStatus1);
		statGrid.setText(1,1, data.statdata.BundStatus2);
		statGrid.setText(2,1, data.statdata.LinkType);
		statGrid.setText(3,1, "本地:"+data.statdata.self_address+" 远端:"+data.statdata.peer_address);
		
		
		userLabel.setText(data.lcpdata.pppusername);
		if( ! "".equals(data.lcpdata.ppppassword ))
			passwordLabel.setText("****");
		mtuLabel.setText(Integer.toString(data.linkdata.mtu));
		
		chapmdCheckBox.setValue(data.lcpdata.accept_chap);
		papCheckBox.setValue(data.lcpdata.accept_pap);
		
		natCheckBox.setValue(data.ifacedata.nat);
		mppcCheckBox.setValue(data.ifacedata.mppc);
		physCheckBox.setValue(data.linkdata.phys_default);
		
		ifaceData conf = data.ifacedata;

		defaultRouterCheckBox.setValue(conf.haveDefaultRoute);
		
		ArrayList<ifaceData.router> list = conf.routers;

		for( int loop = 0; loop < list.size(); loop++)
		{
			ifaceData.router r = list.get(loop);
			routeGrid.setText(loop+1, 0, r.dest);
			routeGrid.setText(loop+1, 1, r.mask);
			routeGrid.setWidget(loop+1, 2, new Button("删除路由", control.new DelInterfaceRouteButtonClick(r.dest, r.mask)));
		}
		// 我们需要排除第一行，是标题。
		while ( (routeGrid.getRowCount() - 1 ) >  list.size())
		{
			routeGrid.removeRow(routeGrid.getRowCount() - 1 );
		}
	}
	
}
