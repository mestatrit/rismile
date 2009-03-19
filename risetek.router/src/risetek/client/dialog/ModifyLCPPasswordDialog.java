package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class ModifyLCPPasswordDialog extends CustomDialog {

	public final PasswordTextBox pwdBox = new PasswordTextBox();
	public final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	public ModifyLCPPasswordDialog(){
		this.setText("设置拨号口令");
		VerticalPanel panel = new VerticalPanel();		
		addStyleName("dialog");
		panel.setBorderWidth(1);
		
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		panel.add(new Label("密码：",false));
		panel.add(pwdBox);
		panel.add(pwdBoxSe);
		pwdBox.setTabIndex(1);
		pwdBoxSe.setTabIndex(2);
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show()
	{
		super.show();
		pwdBox.setFocus(true);
	}
	
	public Widget getFirstTabIndex() {
		return pwdBox;
	}
	
	public boolean isValid()
	{
		if( pwdBox.getText().equals(pwdBoxSe.getText()))
			return true;
		return false;
	}
}
