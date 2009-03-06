package risetek.client.dialog;

import risetek.client.view.RadiusConfigView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.DialogAction;
import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.utils.Validity;

public class RadiusConfigDialog extends CustomDialog{
	private static String[] radiusColumns = {"认证端口","计费端口","共享密匙"};
	private static String[] radiusRequestKey = {"authport","accport","secret"};
	
	private int code = 0;
	
	private Label oldNoteLabel = new Label();
	private Label oldValueLabel = new Label();
	
	private TextBox newValueBox = new TextBox();
	private Label newNoteLabel = new Label();
	
	private Grid gridFrame = new Grid(2, 2);
	private RadiusConfigView parent;
	
	public RadiusConfigDialog(){
		super();
		
		gridFrame.setWidget(0, 0, oldNoteLabel);
		gridFrame.setWidget(0, 1, oldValueLabel);
		gridFrame.setWidget(1, 0, newNoteLabel);
		gridFrame.setWidget(1, 1, newValueBox);
		newValueBox.setTabIndex(1);
		
		add(gridFrame, DockPanel.CENTER);
		confirm.addClickListener(new mClickListener());
	}

	public void show(RadiusConfigView parent, int code,String value) {
		this.parent = parent;
		this.code = code;
		add(new Label("请输入新"+radiusColumns[code]+ "："),DockPanel.NORTH);
		setText("修改"+radiusColumns[code]);
		oldNoteLabel.setText("当前"+radiusColumns[code]+":");
		oldValueLabel.setText(value);
		newNoteLabel.setText("新"+radiusColumns[code]+":");
		newValueBox.setText(value);
		super.show();
		newValueBox.setFocus(true);
		center();
	}

	class mClickListener implements ClickListener {
		public void onClick(Widget sender) {
			String value = newValueBox.getText();
			String check;
			switch(code){
				case 0:
					check = Validity.validRadiusPort(value);
					break;
				case 1:
					check = Validity.validRadiusPort(value);
					break;
				case 2:
					check = Validity.validRadiusShareKey(value);
					break;
				default:
					check = "从认证服务器配置表格取值出错!";
			}
			
			if (null == check) {
				//unmask();
				//hide();
				String requestData = radiusRequestKey[code]+"="+value;
				parent.radiusConfController.modify(requestData, new DialogAction(RadiusConfigDialog.this, parent));
				confirm.setEnabled(false);
				
				
			} else {
				newValueBox.setFocus(true);
				Window.alert(check);
			}
			
		}
	}

	public Widget getFirstTabIndex() {
		// TODO Auto-generated method stub
		return newValueBox;
	}

	public int getParentHeihgt() {
		// TODO Auto-generated method stub
		return parent.getHeight();
	}

	public void setFirstFocus() {
		// TODO Auto-generated method stub
		newValueBox.setFocus(true);
	}

}
