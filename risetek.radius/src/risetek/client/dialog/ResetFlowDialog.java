package risetek.client.dialog;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class ResetFlowDialog extends CustomDialog {

	Label  info = new Label("您确定重置流量计量？");

	public ResetFlowDialog() {
		//gridFrame.setBorderWidth(1);
		mainPanel.add(info);
	}

	public void show(String tips_id) {
		setText(tips_id);
		super.show();
	}

	public Widget getFirstTabIndex() {
		return info;
	}

	public boolean isValid()
	{
		return true;
	}
}
