package risetek.client.view;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IfaceView {
	VerticalPanel panel = new VerticalPanel();
	final TextBox idleTextBox = new TextBox();
	final TextBox sessionTextBox = new TextBox();
	final Label idleLabel = new Label("");
	final Label sessionLabel = new Label("");
	
	final CheckBox natCheckBox = new CheckBox();
	final CheckBox ondemandCheckBox = new CheckBox();
	
	public IfaceView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");
		panel.setTitle("IFACE");
		
		final Label ondemandLabel = new Label("ON-DEMAND");
		panel.add(ondemandLabel);

		panel.add(ondemandCheckBox);
		ondemandCheckBox.setText("ON-DEMAND");

		
		
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
		
		
		final Grid natgrid = new Grid(5,5);
		natgrid.setBorderWidth(1);
		natgrid.setWidth("100%");
		
		panel.add(natgrid);
		
		final Label natLabel = new Label("NAT");
		natgrid.setWidget(0,0, natLabel);

		natgrid.setWidget(0,1, natCheckBox);
		natCheckBox.setText("NAT");
		
		
		outerPanel.add(panel);
		
	}
}
