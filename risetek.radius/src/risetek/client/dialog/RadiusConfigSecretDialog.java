package risetek.client.dialog;

import risetek.client.view.RadiusConfigView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class RadiusConfigSecretDialog extends CustomDialog {
	
	public TextBox newValueBox = new TextBox();
	public RadiusConfigSecretDialog(RadiusConfigView parent){
		super(parent);
		// 格式调试
		newValueBox.setTabIndex(1);
		add(new Label("请输入新共享密钥："),DockPanel.NORTH);
		setText("修改共享密钥");
		
		add(newValueBox, DockPanel.CENTER);
	}

	public void show(String value) {
		newValueBox.setText(value);
		super.show();
		newValueBox.setFocus(true);
		center();
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}

	public void setFirstFocus() {
		newValueBox.setFocus(true);
	}

}
