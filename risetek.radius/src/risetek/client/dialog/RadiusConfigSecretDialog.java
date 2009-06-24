package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class RadiusConfigSecretDialog extends CustomDialog {
	
	public TextBox newValueBox = new TextBox();
	public RadiusConfigSecretDialog(){
		// 格式调试
		//newValueBox.setTabIndex(1);
		add(new Label("请输入新共享密钥："),DockPanel.NORTH);
		setText("修改共享密钥");
		newValueBox.setWidth("140px");
		add(newValueBox, DockPanel.CENTER);
		setSize("220","180");
	}

	public void show(String value) {
		newValueBox.setText(value);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}
	
	public boolean isValid()
	{
		return true;
	}
}
