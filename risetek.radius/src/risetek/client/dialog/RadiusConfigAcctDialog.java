package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class RadiusConfigAcctDialog extends CustomDialog {
	
	public TextBox newValueBox = new TextBox();
	
	public RadiusConfigAcctDialog(){
		add(new Label("请输入新计费端口："),DockPanel.NORTH);
		setText("修改计费端口");
		newValueBox.setTabIndex(1);
		add(newValueBox, DockPanel.CENTER);
	}

	public void show(String value) {
		newValueBox.setText(value);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}

}
