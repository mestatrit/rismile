package risetek.client.view.stick;

import risetek.client.model.IfModel;
import risetek.client.model.ifaceData;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class IfaceView {
	final TextBox idleTextBox = new TextBox();
	final TextBox sessionTextBox = new TextBox();
	final Label idleLabel = new Label("");
	final Label sessionLabel = new Label("");
	
	final CheckBox natCheckBox = new CheckBox();
	final CheckBox ondemandCheckBox = new CheckBox();
	
	public IfaceView(Panel outerPanel)
	{
		final FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		outerPanel.add(flexTable);
		flexTable.setStyleName("router-config");
		final HTML authTitleHTML = new HTML("IFACE参数");
		authTitleHTML.setStyleName("table-title");
		flexTable.setWidget(0, 0, authTitleHTML);
		
		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");
		flexTable.setWidget(1, 0, tempGrid);
		
		tempGrid.setWidget(0,0 ,ondemandCheckBox );
		ondemandCheckBox.setText("按需拨号");

		tempGrid.setWidget(0,1, natCheckBox);
		natCheckBox.setText("网络地址转换（NAT）");
		
		/*
		
		final Label timeoutLabel = new Label("TIMEOUT");
		panel.add(timeoutLabel);

		final Grid timeoutGrid = new Grid();
		panel.add(timeoutGrid);
		timeoutGrid.resize(2, 3);
		timeoutGrid.setStyleName("if-Grid");
		timeoutGrid.getColumnFormatter().setStyleName(0, "head");
		timeoutGrid.getColumnFormatter().setWidth(0, "30%");
		timeoutGrid.getColumnFormatter().setWidth(1, "30%");
		timeoutGrid.getColumnFormatter().setWidth(2, "40%");
		timeoutGrid.setText(0, 0, "idle(0-60000):");
		timeoutGrid.setText(1, 0, "session(0-71582787):");

		timeoutGrid.setWidget(0, 1, idleTextBox);
		idleTextBox.setWidth("100%");
		//idleTextBox.addChangeListener(this);
		
		timeoutGrid.setWidget(1, 1, sessionTextBox);
		sessionTextBox.setWidth("100%");
		//sessionTextBox.addChangeListener(this);
		
		timeoutGrid.setWidget(0, 2, idleLabel);
		timeoutGrid.setWidget(1, 2, sessionLabel);
		
		*/
	}
	
	public void render(IfModel data)
	{
		ifaceData conf = data.config.ifacedata;
		ondemandCheckBox.setChecked(conf.dial_on_demand);
		natCheckBox.setChecked(conf.nat);
		
	}
}
