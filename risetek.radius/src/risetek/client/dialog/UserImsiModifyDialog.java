package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserImsiModifyDialog extends CustomDialog {
	Label  oldNoteLabel = new Label();
	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	Label newNoteLabel = new Label();

	private Grid gridFrame = new Grid(2, 2);
	private UserView parent;
	public UserImsiModifyDialog(UserView parent) {
		super(parent);
		this.parent = parent;
		// Formater Debug
		gridFrame.setBorderWidth(1);
		add(new Label("请输入新的IMSI："),DockPanel.NORTH);
		gridFrame.setWidget(0, 0, oldNoteLabel);
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, newNoteLabel);
		gridFrame.setWidget(1, 1, newValueBox);
		
		newValueBox.setTabIndex(1);
		
		add(gridFrame, DockPanel.CENTER);
	}

	public void show() {
		setText("记录序号：" + parent.focusID);
		oldNoteLabel.setText("当前IMSI：");
		oldValueLabel.setText(parent.focusValue);
		newNoteLabel.setText("新的IMSI：");
		newValueBox.setText(parent.focusValue);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return newValueBox;
	}

	public boolean valid()
	{
		String check = Validity.validIMSI(newValueBox.getText());
		if (null != check) {
			setMessage(check);
			newValueBox.setFocus(true);
			return false;
		}
		return true;
	}
}
