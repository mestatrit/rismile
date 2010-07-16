package risetek.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.dialog.CustomDialog;

public class UserLoginTimeModifyDialog extends CustomDialog {

	public String rowid;
	TextBox startHour = new TextBox();
	TextBox startMinute = new TextBox();
	TextBox endHour = new TextBox();
	TextBox endMinute = new TextBox();
	
	public UserLoginTimeModifyDialog() {
		label.setText("变更用户登录时间段：：");
		setWidth("60px");
		mainPanel.add(initMainTable());
	}

	private Widget initMainTable(){
		Grid mainTable = new Grid(5, 5);
		startHour.setTabIndex(1);
		startHour.setWidth("30px");
		startMinute.setWidth("30px");
		endHour.setWidth("30px");
		endMinute.setWidth("30px");
		boolean readOnly = false;
		startHour.setReadOnly(readOnly);
		startMinute.setReadOnly(readOnly);
		endHour.setReadOnly(readOnly);
		endMinute.setReadOnly(readOnly);
		Button upSh = new Button("↑", new ChangeTimeAction(1, startHour));
		Button downSh = new Button("↓", new ChangeTimeAction(2, startHour));
		Button upSm = new Button("↑", new ChangeTimeAction(3, startMinute));
		Button downSm = new Button("↓", new ChangeTimeAction(4, startMinute));
		Button upEh = new Button("↑", new ChangeTimeAction(1, endHour));
		Button downEh = new Button("↓", new ChangeTimeAction(2, endHour));
		Button upEm = new Button("↑", new ChangeTimeAction(3, endMinute));
		Button downEm = new Button("↓", new ChangeTimeAction(4, endMinute));
		mainTable.setText(0, 0, "上线时间：");
		mainTable.setWidget(0, 1, startHour);
		mainTable.setText(0, 2, "点");
		mainTable.setWidget(0, 3, upSh);
		mainTable.setWidget(0, 4, downSh);
		mainTable.setWidget(1, 1, startMinute);
		mainTable.setText(1, 2, "分");
		mainTable.setWidget(1, 3, upSm);
		mainTable.setWidget(1, 4, downSm);
		mainTable.setText(3, 0, "下线时间：");
		mainTable.setWidget(3, 1, endHour);
		mainTable.setText(3, 2, "点");
		mainTable.setWidget(3, 3, upEh);
		mainTable.setWidget(3, 4, downEh);
		mainTable.setWidget(4, 1, endMinute);
		mainTable.setText(4, 2, "分");
		mainTable.setWidget(4, 3, upEm);
		mainTable.setWidget(4, 4, downEm);
		mainTable.getCellFormatter().setHeight(2, 0, "20px");
		return mainTable;
	}
	
	public void show(String tips_id, String tips_value){
		rowid = tips_id;
		setText("记录序号：" + tips_id);
		String[] times = tips_value.split("-");
		String[] startTime = times[0].split(":");
		String[] endTime = times[1].split(":");
		startHour.setText(startTime[0]);
		startMinute.setText(startTime[1]);
		endHour.setText(endTime[0]);
		endMinute.setText(endTime[1]);
		super.show();
		startHour.setFocus(true);
	}

	public String getTime(){
		String startTime = startHour.getText() + ":" + startMinute.getText();
		String endTime = endHour.getText() + ":" + endMinute.getText();
		return startTime + "-" + endTime;
	}
	
	public Widget getFirstTabIndex() {
		return startHour;
	}

	public boolean isValid()
	{
		if (null == startHour.getText()) {
			setMessage("上线时间不能为空");
			startHour.setFocus(true);
			return false;
		}
		if (null == startMinute.getText()) {
			setMessage("上线分钟不能为空");
			startMinute.setFocus(true);
			return false;
		}
		if (null == endHour.getText()) {
			setMessage("下线时间不能为空");
			endHour.setFocus(true);
			return false;
		}
		if (null == endMinute.getText()) {
			setMessage("下线分钟不能为空");
			endMinute.setFocus(true);
			return false;
		}
		return true;
	}
	
	public class ChangeTimeAction implements ClickHandler {
		int flag;
		TextBox widget;
		public ChangeTimeAction(int flag, TextBox widget){
			this.flag = flag;
			this.widget = widget;
		}
		
		public void onClick(ClickEvent event) {
			if(flag == 1){
				upHour(widget);
			} else if(flag == 2){
				downHour(widget);
			} else if(flag == 3){
				upMinute(widget);
			} else if(flag == 4){
				downMinute(widget);
			}
		}
		
		private void upHour(TextBox widget){
			String value = widget.getText();
			int hour;
			try{
				hour = Integer.parseInt(value);
			} catch (NumberFormatException e){
				setMessage("时间不合法！");
				return;
			}
			if(hour==23){
				hour = 0;
			} else {
				hour++;
			}
			String newValue = Integer.toString(hour);
			if(hour<10){
				newValue = "0" + newValue;
			}
			widget.setText(newValue);
		}
		
		private void downHour(TextBox widget){
			String value = widget.getText();
			int hour;
			try{
				hour = Integer.parseInt(value);
			} catch (NumberFormatException e){
				setMessage("时间不合法！");
				return;
			}
			if(hour==0){
				hour = 23;
			} else {
				hour--;
			}
			String newValue = Integer.toString(hour);
			if(hour<10){
				newValue = "0" + newValue;
			}
			widget.setText(newValue);
		}
		
		private void upMinute(TextBox widget){
			String value = widget.getText();
			int hour;
			try{
				hour = Integer.parseInt(value);
			} catch (NumberFormatException e){
				setMessage("时间不合法！");
				return;
			}
			if(hour==59){
				hour = 0;
			} else {
				hour++;
			}
			String newValue = Integer.toString(hour);
			if(hour<10){
				newValue = "0" + newValue;
			}
			widget.setText(newValue);
		}
		
		private void downMinute(TextBox widget){
			String value = widget.getText();
			int hour;
			try{
				hour = Integer.parseInt(value);
			} catch (NumberFormatException e){
				setMessage("时间不合法！");
				return;
			}
			if(hour==0){
				hour = 59;
			} else {
				hour--;
			}
			String newValue = Integer.toString(hour);
			if(hour<10){
				newValue = "0" + newValue;
			}
			widget.setText(newValue);
		}
	}
}
