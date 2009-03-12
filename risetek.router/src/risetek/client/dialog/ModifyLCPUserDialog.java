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

public class ModifyLCPUserDialog extends CustomDialog {

	public final TextBox nameBox = new TextBox();
	InterfaceView parent;
	
	public ModifyLCPUserDialog(InterfaceView parent){
		super(parent);
		this.parent = parent;
		this.setText("设置用户名称");
		VerticalPanel panel = new VerticalPanel();		
		addStyleName("dialog");
		panel.setBorderWidth(1);
		
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		panel.add(new Label("管理员名称：",false));
		panel.add(nameBox);
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show(String tips_username)
	{
		nameBox.setText(tips_username);
		super.show();
		nameBox.setFocus(true);
		center();
	}
	
	public Widget getFirstTabIndex() {
		return nameBox;
	}
	
	public boolean isValid()
	{
		return true;
	}
}
