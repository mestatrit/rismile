package risetek.client.view;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LinkView {
	VerticalPanel panel = new VerticalPanel();
	final TextBox mtuTextBox = new TextBox();
	final TextBox mruTextBox = new TextBox();
	final TextBox mrruTextBox = new TextBox();
	final Label mtuLabel = new Label("");
	final Label mruLabel = new Label("");
	final Label mrruLabel = new Label("");

	public LinkView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");
		panel.setTitle("Link");
		
		final Label linkLabel = new Label("LINK");
		panel.add(linkLabel);

		final Grid linkGrid = new Grid();
		panel.add(linkGrid);
		linkGrid.resize(3, 3);
		linkGrid.setStyleName("if-Grid");
		linkGrid.getColumnFormatter().setStyleName(0, "head");
		linkGrid.getColumnFormatter().setWidth(0, "30%");
		linkGrid.getColumnFormatter().setWidth(1, "30%");
		linkGrid.getColumnFormatter().setWidth(2, "40%");
		linkGrid.setText(0, 0, "mtu(296-1500):");
		linkGrid.setText(1, 0, "mru(296-1500):");
		linkGrid.setText(2, 0, "mrru(296-4096):");
		
		linkGrid.setWidget(0, 1, mtuTextBox);
		mtuTextBox.setWidth("100%");
		//mtuTextBox.addChangeListener(this);
		
		linkGrid.setWidget(1, 1, mruTextBox);
		mruTextBox.setWidth("100%");
		//mruTextBox.addChangeListener(this);
		
		linkGrid.setWidget(2, 1, mrruTextBox);
		mrruTextBox.setWidth("100%");
		//mrruTextBox.addChangeListener(this);
		
		linkGrid.setWidget(0, 2, mtuLabel);
		linkGrid.setWidget(1, 2, mruLabel);
		linkGrid.setWidget(2, 2, mrruLabel);
		
		outerPanel.add(panel);
	}
}
