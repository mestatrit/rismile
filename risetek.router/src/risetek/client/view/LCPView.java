package risetek.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LCPView {
	VerticalPanel panel = new VerticalPanel();
	
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
	
	public LCPView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");
		panel.setTitle("LCP");

		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");
		tempGrid.getColumnFormatter().setWidth(0, "50%");
		tempGrid.getColumnFormatter().setWidth(1, "50%");
		
		panel.add(tempGrid);
		
		final Grid pppGrid = new Grid(3,2);
		pppGrid.setBorderWidth(1);
		pppGrid.setWidth("100%");
		tempGrid.setWidget(0,0, pppGrid);
	
		pppGrid.setStyleName("if-Grid");
		pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "40%");
		pppGrid.getColumnFormatter().setWidth(1, "60%");
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setWidget(0, 1, userLabel);
		pppGrid.setText(1, 0, "密码:");
		pppGrid.setWidget(1, 1, passwordLabel);
		pppGrid.setWidget(2, 1, new Button("修改"));

		final Grid authGrid = new Grid(3,2);
		authGrid.setBorderWidth(1);
		authGrid.setWidth("100%");
		tempGrid.setWidget(0,1, authGrid);
		
		authGrid.setText(0,0,"认证方式:");
		authGrid.setWidget(1,0, chapmdCheckBox);
		chapmdCheckBox.setText("chapmd5");
		
		authGrid.setWidget(1,1, eapCheckBox);
		eapCheckBox.setText("eap");

		authGrid.setWidget(2,0, mschapCheckBox);
		mschapCheckBox.setText("ms-chap");

		authGrid.setWidget(2,1, papCheckBox);
		papCheckBox.setText("pap");

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
		outerPanel.add(panel);
	}

	public void render()
	{
		
	}
}
