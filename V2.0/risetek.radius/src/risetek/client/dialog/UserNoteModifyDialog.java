package risetek.client.dialog;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserNoteModifyDialog extends CustomDialog {
	public TextBox note = new TextBox();

	public String rowid;
	public UserNoteModifyDialog() {
		add(new Label("请输入新备注："),DockPanel.NORTH);
		note.setWidth("260px");
		add(note,DockPanel.CENTER);
		note.setTabIndex(1);
		setSize("320","140");
	}

	public void show(String tips_id, String tips_value){
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		// FIXME TODO 谷歌的浏览器存在问题，只能这样处理了。
//		if( tips_value.length() != 1 && tips_value.charAt(0) != '\160')  //wangx
			note.setText(tips_value);
		super.show();
		note.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return note;
	}

	public boolean isValid()
	{
		if (null == note.getText()) {
			setMessage("备注不能为空");
			note.setFocus(true);
			return false;
		}
		return true;
	}
}
