package risetek.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RouterView {

	VerticalPanel panel = new VerticalPanel();
	final Grid routeListGrid = new Grid();
	final TextBox destTextBox = new TextBox();
	final TextBox maskTextBox = new TextBox();
	final Label destLabel = new Label("");
	final Label maskLabel = new Label("");
	final Button addRouteButton = new Button("添加路由");
	
	public RouterView(Panel outerPanel)
	{
		panel.setBorderWidth(1);
		panel.setWidth("100%");

		panel.addStyleName("if-layout");
		
		/*
		DisclosurePanel disclosurePanel = new DisclosurePanel("ROUTE");
		outerPanel.add(disclosurePanel);
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(panel);
		disclosurePanel.setWidth("100%");
		*/
		
//		routeListGrid.setStyleName("if-Grid");
		
		final Grid routeGrid = new Grid(3,3);
		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("100%");
		panel.add(routeGrid);

		routeGrid.setStyleName("if-Grid");
		routeGrid.getColumnFormatter().addStyleName(0, "head");
		routeGrid.getColumnFormatter().setWidth(0, "30%");
		routeGrid.getColumnFormatter().setWidth(1, "30%");
		routeGrid.getColumnFormatter().setWidth(2, "40%");
		routeGrid.setText(0, 0, "目的地址:");
		routeGrid.setText(0, 1, "掩码:");

		/*
		routeGrid.setWidget(0, 2, destTextBox);
		destTextBox.setWidth("100%");
		//destTextBox.addChangeListener(this);

		routeGrid.setWidget(1, 1, maskTextBox);
		maskTextBox.setWidth("100%");
		//maskTextBox.addChangeListener(this);

		routeGrid.setWidget(0, 2, destLabel);
		routeGrid.setWidget(1, 2, maskLabel);
		*/
		
		routeGrid.setWidget(0, 2, addRouteButton);
		//addRouteButton.addClickListener(new AddRouteListener());
		addRouteButton.setWidth("3cm");

		outerPanel.add(panel);

	}
}
