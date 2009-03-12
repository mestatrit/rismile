package risetek.client.dialog;

import risetek.client.view.InterfaceView;
import risetek.client.view.InterfaceView2;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class ModifyLCPPasswordDialog extends CustomDialog {

	public final PasswordTextBox pwdBox = new PasswordTextBox();
	public final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	InterfaceView parent;
	
	public ModifyLCPPasswordDialog(InterfaceView parent){
		super(parent);
		this.parent = parent;
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
		
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show(String tips_username)
	{
		super.show();
		pwdBox.setFocus(true);
		center();
	}
	
	public Widget getFirstTabIndex() {
		return pwdBox;
	}
	
	public boolean isValid()
	{
		return true;
	}
}
