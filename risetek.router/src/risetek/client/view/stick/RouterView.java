package risetek.client.view.stick;

import java.util.ArrayList;

import risetek.client.model.IfModel;
import risetek.client.model.ifaceData;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class RouterView {

	final TextBox destTextBox = new TextBox();
	final TextBox maskTextBox = new TextBox();
	final Label destLabel = new Label("");
	final Label maskLabel = new Label("");
	final Button addRouteButton = new Button("添加路由");
	final FlexTable routeGrid = new FlexTable();
	
	public RouterView(Panel outerPanel)
	{
		final FlexTable flexTable = new FlexTable();
		flexTable.setBorderWidth(1);
		flexTable.setWidth("100%");
		outerPanel.add(flexTable);
		flexTable.setStyleName("router-config");
		final HTML Title = new HTML("接口路由");
		Title.setStyleName("table-title");
		flexTable.setWidget(0, 0, Title);
		/*
		DisclosurePanel disclosurePanel = new DisclosurePanel("ROUTE");
		outerPanel.add(disclosurePanel);
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(panel);
		disclosurePanel.setWidth("100%");
		*/
		
//		routeListGrid.setStyleName("if-Grid");
		
		routeGrid.setBorderWidth(1);
		routeGrid.setWidth("100%");
		flexTable.setWidget(1, 0, routeGrid);

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
	}
	
	public void render(IfModel data)
	{
		ifaceData conf = data.config.ifacedata;
		ArrayList<ifaceData.router> list = conf.routers;
		//routeGrid.resizeRows(list.size()+1);
		for( int loop = 1; loop < list.size()+1; loop++)
		{
			ifaceData.router r = list.get(loop-1);
			routeGrid.setText(loop, 0, r.dest);
			routeGrid.setText(loop, 1, r.mask);
			routeGrid.setWidget(loop, 2, new Button("删除"));
		}
		
	}
}
