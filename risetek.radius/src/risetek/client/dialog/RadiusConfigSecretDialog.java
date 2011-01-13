package risetek.client.dialog;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class RadiusConfigSecretDialog extends CustomDialog {
	
	public final TextBox newValueBox = new TextBox();
	public RadiusConfigSecretDialog(){
		newValueBox.setTabIndex(1);
		label.setText("请输入新共享密钥：");
		setText("修改共享密钥");
		newValueBox.setWidth("340px");
		mainPanel.add(newValueBox);
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
		String check = Validity.validRadiusShareKey(newValueBox.getText());
		if(null != check){
			newValueBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
}
