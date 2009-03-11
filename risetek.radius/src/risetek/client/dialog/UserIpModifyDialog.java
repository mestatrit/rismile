package risetek.client.dialog;

import risetek.client.view.UserView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.SysLog;
import com.risetek.rismile.client.utils.Validity;

public class UserIpModifyDialog extends CustomDialog implements
		ClickListener {

	Label  oldNoteLabel = new Label();
	Label  oldValueLabel = new Label();
	public TextBox newValueBox = new TextBox();
	Label newNoteLabel = new Label();
	private Grid gridFrame = new Grid(2, 2);
	public String rowid;
	//private UserView parent;
	public UserIpModifyDialog(UserView parent) {
		super(parent);
		//this.parent = parent;
		add(new Label("请输入新的IP："),DockPanel.NORTH);
		gridFrame.setWidget(0, 0, oldNoteLabel);
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, newNoteLabel);
		gridFrame.setWidget(1, 1, newValueBox);
		
		newValueBox.setTabIndex(1);
		
		add(gridFrame, DockPanel.CENTER);
	}

	public void show(String tips_id, String tips_value) {
		/*
		setText("记录序号：" + parent.focusID);
		oldNoteLabel.setText("当前IP：");
		oldValueLabel.setText(parent.focusValue);
		newNoteLabel.setText("新的IP：");
		newValueBox.setText(parent.focusValue);
		*/
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		oldNoteLabel.setText("当前IP：");
		oldValueLabel.setText(tips_value);
		newNoteLabel.setText("新的IP：");
		newValueBox.setText(tips_value);
		super.show();
		newValueBox.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return newValueBox;
	}

	public boolean valid()
	{
		String check = Validity.validIpAddress(newValueBox.getText());
		if (null != check) {
			SysLog.log(check);
			setMessage(check);
			return false;
		}
		return true;
	}
	
}
