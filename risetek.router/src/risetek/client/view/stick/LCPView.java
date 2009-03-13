package risetek.client.view.stick;

import risetek.client.control.IfController;
import risetek.client.model.DialerInterfaceData;
import risetek.client.model.IfModel;
import risetek.client.model.lcpData;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LCPView {
	
	final TextBox userTextBox = new TextBox();
	final PasswordTextBox passwordTextBox = new PasswordTextBox();
	final PasswordTextBox repasswordTextBox = new PasswordTextBox();
	final Label userLabel = new Label("未设置");
	final Label passwordLabel = new Label("未设置");
	final Label repasswordLabel = new Label("");
	final CheckBox chapmdCheckBox = new CheckBox();
	final CheckBox eapCheckBox = new CheckBox();
	final CheckBox mschapCheckBox = new CheckBox();
	final CheckBox papCheckBox = new CheckBox();

	final CheckBox keepaliveCheckBox = new CheckBox();
	final TextBox keepaliveTextBox = new TextBox();
	final TextBox redialTextBox = new TextBox();
	final Label keepaliveLabel = new Label("");
	final Label redialLabel = new Label("");
	
	IfController control;
	public LCPView(Panel outerPanel, IfController control)
	{
		this.control = control;

		final FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		outerPanel.add(flexTable);
		flexTable.setStyleName("router-config");
		
		final HTML authTitleHTML = new HTML("网络链路参数");
		authTitleHTML.setStyleName("table-title");
		flexTable.setWidget(0, 0, authTitleHTML);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		final Grid pppGrid = new Grid(2,3);
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
		flexTable.setWidget(1, 0, pppGrid);

		final FlexTable authGrid = new FlexTable();
		authGrid.setBorderWidth(1);
		authGrid.setWidth("100%");
		//tempGrid.setWidget(0,1, authGrid);

		authGrid.getFlexCellFormatter().setColSpan(0, 0, 2);
		
		authGrid.setText(0,0,"认证方式:");
		
		authGrid.setWidget(1,0, chapmdCheckBox);
		chapmdCheckBox.setText("chapmd5");
		
		//authGrid.setWidget(1,1, eapCheckBox);
		//eapCheckBox.setText("eap");

		//authGrid.setWidget(2,0, mschapCheckBox);
		//mschapCheckBox.setText("ms-chap");

		authGrid.setWidget(1,1, papCheckBox);
		papCheckBox.setText("pap");

		flexTable.setWidget(1, 1, authGrid);
		/*
		
		// ---------------------------------------------------------
		final Label lcpLabel = new Label("LCP");
		panel.add(lcpLabel);

		final Grid lcpGrid = new Grid(2,3);
		panel.add(lcpGrid);
		lcpGrid.setStyleName("if-Grid");
		lcpGrid.getColumnFormatter().setStyleName(0, "head");
		lcpGrid.getColumnFormatter().setWidth(0, "30%");
		lcpGrid.getColumnFormatter().setWidth(1, "30%");
		lcpGrid.getColumnFormatter().setWidth(2, "40%");
		keepaliveCheckBox.setText("keepalive(4-40 seconds):");

		
		lcpGrid.setWidget(0, 0, keepaliveCheckBox);
		lcpGrid.setText(1, 0, "redial:");
		
		lcpGrid.setWidget(0, 1, keepaliveTextBox);
		keepaliveTextBox.setWidth("100%");
		//keepaliveTextBox.addChangeListener(this);
		
		lcpGrid.setWidget(1, 1, redialTextBox);
		redialTextBox.setWidth("100%");

		lcpGrid.setWidget(0, 2, keepaliveLabel);
		lcpGrid.setWidget(1, 2, redialLabel);
*/
	}

	public void render(DialerInterfaceData data)
	{
		lcpData lcpdata = data.lcpdata;

		userLabel.setText(lcpdata.pppusername);
		passwordLabel.setText(lcpdata.ppppassword);
		
		chapmdCheckBox.setChecked(lcpdata.accept_chap);
		eapCheckBox.setChecked(lcpdata.accept_eap);
		mschapCheckBox.setChecked(lcpdata.accept_mschap);
		papCheckBox.setChecked(lcpdata.accept_pap);
		
	}
}
