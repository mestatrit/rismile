package risetek.client.dialog;

import risetek.client.view.InterfaceView2;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class ModifyLCPUserDialog extends CustomDialog {

	//Widget widget;

	public final TextBox nameBox = new TextBox();
	public final PasswordTextBox pwdBox = new PasswordTextBox();
	public final PasswordTextBox pwdBoxSe = new PasswordTextBox();
	
	//private final Label nameLabel = new Label("管理员名称：",false);
	private final Label pwdLabel = new Label("密码：",false);
	private final Label pwdLabelSe = new Label("重复密码：",false);
	private final Grid gridFrame = new Grid(3,2);

	InterfaceView2 parent;
	
	public ModifyLCPUserDialog(InterfaceView2 parent){
		super(parent);
		this.parent = parent;
		VerticalPanel panel = new VerticalPanel();		
		addStyleName("dialog");
		panel.setBorderWidth(1);
		
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		panel.add(new Label("管理员名称：",false));
		panel.add(nameBox);
		panel.add(new Label("密码：",false));
		panel.add(pwdBox);
		panel.add(pwdBoxSe);
		
		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show(){
		super.show();
		nameBox.setFocus(true);
		center();
	}
	
	public Widget getFirstTabIndex() {
		return nameBox;
	}
}
