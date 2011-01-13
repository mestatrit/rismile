package risetek.client.dialog;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class RadiusConfigAcctDialog extends CustomDialog {
	
	public TextBox newValueBox = new TextBox();
	
	public RadiusConfigAcctDialog(){
		label.setText("请输入新计费端口：");
		setText("修改计费端口");
		newValueBox.setWidth("160px");
		newValueBox.setTabIndex(1);
		mainPanel.add(newValueBox);
		submit.setText("修改");
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
		String check = Validity.validRadiusPort(newValueBox.getText());
		if(null != check){
			newValueBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
	
}
