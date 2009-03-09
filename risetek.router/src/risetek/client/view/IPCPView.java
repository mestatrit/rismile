package risetek.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
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
		panel.setBorderWidth(1);
		panel.setWidth("100%");
		
		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");
		panel.add(tempGrid);
		
		tempGrid.setWidget(0, 0, dnsCheckBox);
		dnsCheckBox.setText("获取DNS服务器地址");

		tempGrid.setWidget(0, 1, vjcompCheckBox);
		vjcompCheckBox.setText("vjcomp压缩");

		outerPanel.add(panel);
				
	}
}
