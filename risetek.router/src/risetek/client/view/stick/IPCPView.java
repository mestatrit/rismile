package risetek.client.view.stick;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

public class IPCPView {

	final CheckBox dnsCheckBox = new CheckBox();
	final CheckBox vjcompCheckBox = new CheckBox();
	final Button comfirmButton = new Button();	

	
	public IPCPView(Panel outerPanel)
	{
		final FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		outerPanel.add(flexTable);
		flexTable.setStyleName("router-config");
		final HTML Title = new HTML("IPCP参数");
		Title.setStyleName("table-title");
		flexTable.setWidget(0, 0, Title);
		
		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");

		flexTable.setWidget(1, 0, tempGrid);
		
		tempGrid.setWidget(0, 0, dnsCheckBox);
		dnsCheckBox.setText("获取DNS服务器地址");

		tempGrid.setWidget(0, 1, vjcompCheckBox);
		vjcompCheckBox.setText("vjcomp压缩");
	}
}
