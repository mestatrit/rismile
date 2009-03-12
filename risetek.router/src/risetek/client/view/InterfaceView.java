package risetek.client.view;

import java.util.ArrayList;

import risetek.client.control.IfController;
import risetek.client.model.IfModel;
import risetek.client.model.ifaceData;

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

	final Grid serviceGrid = new Grid(2,3);
	final Grid netGrid = new Grid(2,4);
	final Grid routeGrid = new Grid(2,5);

	final FlexTable panel = new FlexTable();
	
	final Label userLabel = new Label("未设置");
	final Label passwordLabel = new Label("未设置");
	final Label mtuLabel = new Label("MTU值");
	
	final CheckBox chapmdCheckBox = new CheckBox("CHAP认证");
	final CheckBox papCheckBox = new CheckBox("PAP 认证");
	final CheckBox natCheckBox = new CheckBox("网络地址转换（NAT）");
	//final CheckBox ondemandCheckBox = new CheckBox();
	final CheckBox mppcRadioButton = new CheckBox("MPPC 压缩");
	final CheckBox defaultRouter = new CheckBox("作为缺省路由");
	
	
	
	IfController control;
	
	public InterfaceView(IfController control) {
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

		// 网络配置信息界面布局
		final VerticalPanel dialbase = new VerticalPanel();
		dialbase.setWidth("100%");
		dialbase.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		
		
		FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		dialbase.add(flexTable);
		flexTable.setStyleName("router-config");
		
		final Grid pppGrid = new Grid(3,3);
		pppGrid.setBorderWidth(1);
		pppGrid.setWidth("100%");
	
		pppGrid.setStyleName("if-Grid");
		pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "40%");
		pppGrid.getColumnFormatter().setWidth(1, "40%");
		pppGrid.getColumnFormatter().setWidth(2, "20%");
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setWidget(0, 2, new Button("修改", control.new ModifyLCPUserButtonClick()));
		
		pppGrid.setText(1, 0, "密码:");
		pppGrid.setWidget(1, 1, passwordLabel);
		pppGrid.setWidget(1, 2, new Button("修改", control.new ModifyLCPUserButtonClick()));

		pppGrid.setText(2, 0, "MTU:");
		pppGrid.setWidget(2, 1, mtuLabel);
		pppGrid.setWidget(2, 2, new Button("修改", control.new ModifyLCPUserButtonClick()));
		
		flexTable.setWidget(1, 0, pppGrid);

		
		
		FlexTable tempGrid = new FlexTable();
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");
		//tempGrid.setWidget(0,1, authGrid);

		//tempGrid.getFlexCellFormatter().setColSpan(0, 0, 2);
		//tempGrid.setText(0,0,"认证方式:");
		
		tempGrid.setWidget(0,0, chapmdCheckBox);
		//chapmdCheckBox.setText("CHAP认证");
		
		//authGrid.setWidget(1,1, eapCheckBox);
		//eapCheckBox.setText("eap");

		//authGrid.setWidget(2,0, mschapCheckBox);
		//mschapCheckBox.setText("ms-chap");

		tempGrid.setWidget(0,1, papCheckBox);

		tempGrid.setWidget(1,0, natCheckBox);
		
		tempGrid.setWidget(1,1, mppcRadioButton);
		
		tempGrid.setWidget(2,0, defaultRouter);

		flexTable.setWidget(1, 1, tempGrid);
		
		stackPanel.add(dialbase, createHeaderHTML("拨号接口基本参数"), true);

		// 路由信息界面布局
		final VerticalPanel routePanel = new VerticalPanel();
		routePanel.setWidth("100%");
		routePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		final Button routeAddButton = new Button("添加路由");
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
		stackPanel.add(routePanel, createHeaderHTML("接口路由设置"), true);

		// 管理配置界面布局
		final Grid adminGrid = new Grid(2,3);
		//adminGrid.setBorderWidth(1);
		adminGrid.setStyleName("grid1-table");
		stackPanel.add(adminGrid, createHeaderHTML("拨号接口高级配置"), true);
		adminGrid.setWidth("100%");

		final Button addAdminButton = new Button("添加管理员");
		addAdminButton.setStyleName("big-button");
		adminGrid.setWidget(0, 0, addAdminButton);
		final Button delAdminButton = new Button("删除管理员");
		delAdminButton.setStyleName("big-button");
		adminGrid.setWidget(0, 1, delAdminButton);
		
		final Button upfileButton = new Button("升级程序");
		adminGrid.setWidget(0, 2, upfileButton);
		upfileButton.setStyleName("big-button");

		final Button paraButton = new Button("恢复出厂参数");
		adminGrid.setWidget(1, 0, paraButton);
		paraButton.setStyleName("big-button");

		final Button restartButton = new Button("重启设备");
		adminGrid.setWidget(1, 2, restartButton);
		restartButton.setStyleName("big-button");
		
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
	

	public void render(IfModel data)
	{
		userLabel.setText(data.config.lcpdata.pppusername);
		passwordLabel.setText(data.config.lcpdata.ppppassword);
		mtuLabel.setText(Integer.toString(data.config.linkdata.mtu));
		
		chapmdCheckBox.setChecked(data.config.lcpdata.accept_chap);
		papCheckBox.setChecked(data.config.lcpdata.accept_pap);
		
		natCheckBox.setChecked(data.config.ifacedata.nat);
		//mppcRadioButton.setEnabled(data.config.linkdata.)
		
		ifaceData conf = data.config.ifacedata;
		ArrayList<ifaceData.router> list = conf.routers;
		for( int loop = 1; loop < list.size()+1; loop++)
		{
			ifaceData.router r = list.get(loop-1);
			if("0.0.0.0".equals(r.dest))
			{
				defaultRouter.setChecked(true);
				break;
			}
		}

		
}
}
