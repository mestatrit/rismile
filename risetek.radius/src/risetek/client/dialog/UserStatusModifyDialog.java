package risetek.client.dialog;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserStatusModifyDialog extends CustomDialog {
	public ListBox status = new ListBox();
	public String rowid;
	public UserStatusModifyDialog() {
		label.setText("变更用户级别：");
		status.setWidth("200px");
		status.setTabIndex(1);
		status.addItem("请指定用户级别……", "-1");
		status.addItem("不关注", "0");
		status.addItem("一般用户", "1");
		status.addItem("重点用户", "2");
		mainPanel.add(status);
	}

	public void show(String tips_id, String tips_value){
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		// FIXME TODO 谷歌的浏览器存在问题，只能这样处理了。
//		if( tips_value.length() != 1 && tips_value.charAt(0) != '\160')
		for(int i=0;i<status.getItemCount();i++){
			String value = status.getValue(i);
			if(tips_value.equals(value)){
				status.setSelectedIndex(i);
				break;
			}
		}
		super.show();
		status.setFocus(true);
	}

	public Widget getFirstTabIndex() {
		return status;
	}

	public boolean isValid() {
		int index = status.getSelectedIndex();
		if ("-1".equals(status.getValue(index))) {
			setMessage("没有指定用户级别！");
			status.setFocus(true);
			return false;
		}
		return true;
	}
}
