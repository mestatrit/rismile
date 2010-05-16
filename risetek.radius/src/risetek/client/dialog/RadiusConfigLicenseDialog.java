package risetek.client.dialog;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class RadiusConfigLicenseDialog extends CustomDialog {
	// License输入框
	public final TextBox licenseBox = new TextBox();
	// 授权用户数输入框
	public final TextBox newValueBox = new TextBox();

	private final Grid gridFrame = new Grid(2, 2);
	
	public RadiusConfigLicenseDialog(){
		label.setText("请在下面输入：");
		setText("设置授权密钥");
		
		gridFrame.setWidget(0,0,new Label("授权数：",false));
		gridFrame.setWidget(1,0,new Label("授权码：",false));
		
		gridFrame.setWidget(0,1,newValueBox);
		gridFrame.setWidget(1,1,licenseBox);
		
		newValueBox.setTabIndex(1);
		newValueBox.setWidth("340px");
		mainPanel.add(gridFrame);
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
