package risetek.client.dialog;

import risetek.client.view.InterfaceView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class ModifyMTUDialog extends CustomDialog {

	public final TextBox newValuebox = new TextBox();
	InterfaceView parent;
	
	public ModifyMTUDialog(InterfaceView parent){
		super(parent);
		this.parent = parent;
		this.setText("设置MTU值");
		VerticalPanel panel = new VerticalPanel();		
		addStyleName("dialog");
		panel.setBorderWidth(1);
		
		//panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSpacing(10);
		panel.setBorderWidth(0);
		
		panel.add(new Label("MTU：",false));
		panel.add(newValuebox);
		
		newValuebox.setTabIndex(1);

		add(panel,DockPanel.CENTER);

		setSize("280","200");
	}
	public void show(String tips_value)
	{
		newValuebox.setText(tips_value);
		super.show();
		newValuebox.setFocus(true);
	}
	
	public Widget getFirstTabIndex() {
		return newValuebox;
	}
	
	public boolean isValid()
	{
		return true;
	}
}
