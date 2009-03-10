package risetek.client.view.stick;

import risetek.client.model.IfModel;
import risetek.client.model.linkData;

import com.google.gwt.user.client.ui.Button;
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
	final Label mtuLabel = new Label("MTU值");
	final Label mruLabel = new Label("MRU值");
	final Label mrruLabel = new Label("MRRU值");

	public LinkView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");
		panel.setTitle("Link");
	
		final Grid linkGrid = new Grid(1,7);
		linkGrid.setBorderWidth(1);
		
		panel.add(linkGrid);

		linkGrid.setStyleName("if-Grid");
		linkGrid.getColumnFormatter().setStyleName(0, "head");

		linkGrid.getColumnFormatter().setWidth(0, "15%");
		linkGrid.getColumnFormatter().setWidth(1, "15%");
		linkGrid.getColumnFormatter().setWidth(2, "15%");
		linkGrid.getColumnFormatter().setWidth(3, "15%");
		linkGrid.getColumnFormatter().setWidth(4, "15%");
		linkGrid.getColumnFormatter().setWidth(5, "15%");
		linkGrid.getColumnFormatter().setWidth(6, "10%");

		linkGrid.setText(0, 0, "MTU:");
		linkGrid.setWidget(0, 1, mtuLabel);

		linkGrid.setText(0, 2, "MRU:");
		linkGrid.setWidget(0, 3, mruLabel);

		linkGrid.setText(0, 4, "MRRU:");
		linkGrid.setWidget(0, 5, mrruLabel);
		
		linkGrid.setWidget(0, 6, new Button("修改"));
		
		/*
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
		*/
		
		outerPanel.add(panel);
	}
	
	public void render(IfModel data)
	{
		linkData linkdata = data.config.linkdata;
		mtuLabel.setText(Integer.toString(linkdata.mtu));
		mruLabel.setText(Integer.toString(linkdata.mru));
		mrruLabel.setText(Integer.toString(linkdata.mrru));
	}
	
}
