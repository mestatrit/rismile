package risetek.client.view.stick;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BundView {
	VerticalPanel panel = new VerticalPanel();

	final CheckBox mppcRadioButton = new CheckBox();
	
	public BundView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");

		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");
		panel.add(tempGrid);
		
		tempGrid.setWidget(0,0 ,mppcRadioButton );
		mppcRadioButton.setText("MPPC 压缩");
		
		outerPanel.add(panel);
	}
	
}
