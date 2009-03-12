package risetek.client.view.stick;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

public class BundView {

	final CheckBox mppcRadioButton = new CheckBox();
	
	public BundView(Panel outerPanel)
	{
		final FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		outerPanel.add(flexTable);
		flexTable.setStyleName("router-config");
		final HTML Title = new HTML("LINK参数");
		Title.setStyleName("table-title");
		flexTable.setWidget(0, 0, Title);

		final Grid tempGrid = new Grid(1,2);
		tempGrid.setBorderWidth(1);
		tempGrid.setWidth("100%");

		flexTable.setWidget(1, 0, tempGrid);
		
		tempGrid.setWidget(0,0 ,mppcRadioButton );
		mppcRadioButton.setText("MPPC 压缩");
		
	}
	
}
