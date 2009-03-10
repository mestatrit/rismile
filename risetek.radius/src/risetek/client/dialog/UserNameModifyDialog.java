package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class UserNameModifyDialog extends CustomDialog {
	Label  oldNoteLabel = new Label();
	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	Label newNoteLabel = new Label();
	private Grid gridFrame = new Grid(2, 2);
	private UserView parent;
	public UserNameModifyDialog(UserView parent) {
		super(parent);
		this.parent = parent;
		add(new Label("请输入新的用户名："),DockPanel.NORTH);
		gridFrame.setWidget(0, 0, oldNoteLabel);
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, newNoteLabel);
		gridFrame.setWidget(1, 1, newValueBox);
		newValueBox.setTabIndex(1);
		add(gridFrame, DockPanel.CENTER);
	}

	public void show() {
		setText("记录序号：" + parent.focusID);
		oldNoteLabel.setText("当前用户名：");
		oldValueLabel.setText(parent.focusValue);
		newNoteLabel.setText("新的用户名：");
		newValueBox.setText(parent.focusValue);
		
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return newValueBox;
	}

	public boolean valid()
	{
		String check = Validity.validUserName(newValueBox.getText());
		if( null != check )
		{
			newValueBox.setFocus(true);
			setMessage(check);
			return false;
		}
		return true;
	}
}
