package risetek.client.view;

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
	final Label userLabel = new Label("");
	final Label passwordLabel = new Label("");
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
		
		final Grid pppGrid = new Grid();
		// DEBUG FORMATRT
		pppGrid.setBorderWidth(1);
		panel.add(pppGrid);
	
		
		pppGrid.resize(3, 3);
		pppGrid.setStyleName("if-Grid");
		pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "30%");
		pppGrid.getColumnFormatter().setWidth(1, "30%");
		pppGrid.getColumnFormatter().setWidth(2, "40%");
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setText(1, 0, "密码:");
		pppGrid.setText(2, 0, "重复密码:");
		
		pppGrid.setWidget(0, 1, userTextBox);
		pppGrid.setWidget(1, 1, passwordTextBox);
		pppGrid.setWidget(2, 1, repasswordTextBox);
		//repasswordTextBox.addChangeListener(this);
		
		pppGrid.setWidget(0, 2, userLabel);
		pppGrid.setWidget(1, 2, passwordLabel);
		pppGrid.setWidget(2, 2, repasswordLabel);

		final HorizontalPanel authPanel = new HorizontalPanel();
		panel.add(authPanel);
		
		final Label authLabel = new Label("认证方式:");
		authPanel.add(authLabel);
		
		authPanel.add(chapmdCheckBox);
		chapmdCheckBox.setText("chapmd5");
		
		authPanel.add(eapCheckBox);
		eapCheckBox.setText("eap");

		authPanel.add(mschapCheckBox);
		mschapCheckBox.setText("ms-chap");

		authPanel.add(papCheckBox);
		papCheckBox.setText("pap");

		final Label lcpLabel = new Label("LCP");
		panel.add(lcpLabel);

		final Grid lcpGrid = new Grid();
		panel.add(lcpGrid);
		lcpGrid.resize(2, 3);
		lcpGrid.setStyleName("if-Grid");
		lcpGrid.getColumnFormatter().setStyleName(0, "head");
		lcpGrid.getColumnFormatter().setWidth(0, "30%");
		lcpGrid.getColumnFormatter().setWidth(1, "30%");
		lcpGrid.getColumnFormatter().setWidth(2, "40%");
		keepaliveCheckBox.setText("keepalive(4-40 seconds):");

		
		/*		
		keepaliveCheckBox.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				if(keepaliveCheckBox.isChecked()){
					keepaliveTextBox.setEnabled(true);
				}else{
					keepaliveTextBox.setText("");
					keepaliveTextBox.setEnabled(false);
					keepaliveLabel.setText("");
				}
			}
			
		});
*/		
		lcpGrid.setWidget(0, 0, keepaliveCheckBox);
		lcpGrid.setText(1, 0, "redial:");
		
		lcpGrid.setWidget(0, 1, keepaliveTextBox);
		keepaliveTextBox.setWidth("100%");
		//keepaliveTextBox.addChangeListener(this);
		
		lcpGrid.setWidget(1, 1, redialTextBox);
		redialTextBox.setWidth("100%");

		lcpGrid.setWidget(0, 2, keepaliveLabel);
		lcpGrid.setWidget(1, 2, redialLabel);

		outerPanel.add(panel);
	}

	public void render()
	{
		
	}
}
