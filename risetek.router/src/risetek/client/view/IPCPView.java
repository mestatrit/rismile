package risetek.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IPCPView {

	VerticalPanel panel = new VerticalPanel();
	final CheckBox dnsCheckBox = new CheckBox();
	final CheckBox vjcompCheckBox = new CheckBox();
	final Button comfirmButton = new Button();	

	
	public IPCPView(Panel outerPanel)
	{
		
		final Label ipcpLabel = new Label("IPCP");
		panel.add(ipcpLabel);

		final HorizontalPanel ipcpPanel = new HorizontalPanel();
		panel.add(ipcpPanel);
	
		ipcpPanel.add(dnsCheckBox);
		dnsCheckBox.setText("request_dns");

		ipcpPanel.add(vjcompCheckBox);
		vjcompCheckBox.setText("vjcomp");

		final DockPanel toolPanel = new DockPanel();
		panel.add(toolPanel);
		toolPanel.setStyleName("if-horizontal");
		
		final Button delButton = new Button();
		toolPanel.add(delButton, DockPanel.EAST);
		delButton.setText("删除");
		delButton.setWidth("3cm");
		
		toolPanel.add(comfirmButton, DockPanel.EAST);
		comfirmButton.setText("确定");
		comfirmButton.setWidth("3cm");
/*		
		comfirmButton.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				checkAll();
				if(isPass){
					String infCmd = getInfCmd(); 
					String commands = getCommands();
					String text = "interface=" + infCmd + "&commands=" + commands;
					ifController.setDialer(new SettingAction(comfirmButton), text);
				}else {
					Window.alert("输入有错误，请修改！");
				}
			}
			
		});
		*/
		outerPanel.add(panel);
				
	}
}
