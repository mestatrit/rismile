package risetek.client.view;

import java.util.ArrayList;

import risetek.client.control.IfController;
import risetek.client.model.DialerInterfaceData;
import risetek.client.model.IfModel;
import risetek.client.model.ifaceData;
import risetek.client.view.stick.AdvancedView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.risetek.rismile.client.Entry;

public class InterfaceView extends Composite {

	final FlexTable routeGrid = new FlexTable();

	final FlexTable panel = new FlexTable();
	
	final Label userLabel = new Label();
	final Label passwordLabel = new Label("****");	// 我们不显示这个数据
	final Label mtuLabel = new Label();
	
	final CheckBox chapmdCheckBox = new CheckBox("CHAP认证");
	final CheckBox papCheckBox = new CheckBox("PAP 认证");
	final CheckBox natCheckBox = new CheckBox("NAT");
	//final CheckBox ondemandCheckBox = new CheckBox();
	final CheckBox mppcCheckBox = new CheckBox("MPPC 压缩");
	final CheckBox defaultRouterCheckBox = new CheckBox("作为缺省路由");
	
	IfController control;
	
	public InterfaceView(IfController control) {
		this.control = control;
		panel.setHeight(Entry.SinkHeight);
		initWidget(panel);
		panel.setWidth("100%");

		setStyleName("rismile-system");

		final StackPanel stackPanel = new StackPanel();
		stackPanel.setWidth("100%");
		stackPanel.setHeight("100%");
		
		stackPanel.setStyleName("stack-panel");
		panel.setWidget(0, 0, stackPanel);
		//panel.getFlexCellFormatter().setColSpan(1, 0, 2);

		// --- 网络状态显示
		FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.getColumnFormatter().setWidth(0, "50%");
		flexTable.getColumnFormatter().setWidth(1, "50%");
		flexTable.setWidget(0, 0, new Button("断开连接"));
		flexTable.setWidget(0, 1, new Button("建立连接"));
		flexTable.setWidget(1, 0, new Button("更新状态"));
		flexTable.setStyleName("router-config");
		stackPanel.add(flexTable, createHeaderHTML("拨号接口运行状态"), true);
		
		
		// 网络配置信息界面布局

		flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.getColumnFormatter().setWidth(0, "50%");
		flexTable.getColumnFormatter().setWidth(1, "50%");
		flexTable.setStyleName("router-config");
		
		final Grid pppGrid = new Grid(3,3);
		pppGrid.setBorderWidth(1);
		pppGrid.setWidth("80%");
	
		// pppGrid.setStyleName("if-Grid");
		// pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "30%");
		pppGrid.getColumnFormatter().setWidth(1, "50%");
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
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("80%");
		tempGrid.getColumnFormatter().setWidth(0, "50%");
		
		//tempGrid.setWidget(0,1, authGrid);

		//tempGrid.getFlexCellFormatter().setColSpan(0, 0, 2);
		//tempGrid.setText(0,0,"认证方式:");
		
		tempGrid.setWidget(0,0, chapmdCheckBox);
		chapmdCheckBox.addClickListener(control.new ModifyCHAPMD5Listener());
		//chapmdCheckBox.setText("CHAP认证");
		
		//authGrid.setWidget(1,1, eapCheckBox);
		//eapCheckBox.setText("eap");

		//authGrid.setWidget(2,0, mschapCheckBox);
		//mschapCheckBox.setText("ms-chap");

		tempGrid.setWidget(0,1, papCheckBox);
		papCheckBox.addClickListener(control.new ModifyPAPListener());

		tempGrid.setWidget(1,0, natCheckBox);
		natCheckBox.addClickListener(control.new ModifyNATListener());
		
		tempGrid.setWidget(1,1, mppcCheckBox);
		mppcCheckBox.addClickListener(control.new ModifyMPPCListener());
		
		tempGrid.setWidget(2,0, defaultRouterCheckBox);
		defaultRouterCheckBox.addClickListener(control.new ModifyDefaultROUTEListener());
		
		flexTable.setWidget(0, 1, tempGrid);
		
		stackPanel.add(flexTable, createHeaderHTML("拨号接口基本配置"), true);

		// 接口路由设置

		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("100%");
		routeGrid.setStyleName("router-config");
		/*
		routeGrid.getCellFormatter().setStyleName(0, 0, "head");
		routeGrid.getCellFormatter().setStyleName(0, 1, "head");
		routeGrid.getCellFormatter().setStyleName(0, 2, "head");
		*/
		routeGrid.setText(0, 0, "目标地址");
		routeGrid.setText(0, 1, "掩码");
		routeGrid.setWidget(0, 2, new Button("添加路由", control.new AddInterfaceRouteButtonClick()));
//		routeGrid.setStyleName("status-table");
		stackPanel.add(routeGrid, createHeaderHTML("拨号接口路由设置"), true);

		// 管理配置界面布局
		flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		flexTable.setStyleName("router-config");
		stackPanel.add(flexTable, createHeaderHTML("拨号接口高级配置"), true);
		new AdvancedView(flexTable);
	}

	private String createHeaderHTML(String caption) {
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
	


	protected void onLoad() {
		control.load();
	}
	

	public void render(DialerInterfaceData data)
	{
		userLabel.setText(data.lcpdata.pppusername);
//		passwordLabel.setText(data.config.lcpdata.ppppassword);
		mtuLabel.setText(Integer.toString(data.linkdata.mtu));
		
		chapmdCheckBox.setChecked(data.lcpdata.accept_chap);
		papCheckBox.setChecked(data.lcpdata.accept_pap);
		
		natCheckBox.setChecked(data.ifacedata.nat);
		mppcCheckBox.setChecked(data.ifacedata.mppc);
		
		ifaceData conf = data.ifacedata;

		defaultRouterCheckBox.setChecked(conf.haveDefaultRoute);
		
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
