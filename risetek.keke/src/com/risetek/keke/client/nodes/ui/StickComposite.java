package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.resources.IconManage;
import com.risetek.keke.client.ui.UiKeke;

public class StickComposite extends Composite {
	
	// TODO: 个点的其它地方装饰。
	Grid outgrid = new Grid(3,3);
	DockPanel outPanel = new DockPanel();
	Image img = new Image();

	public StickComposite(Stick node) {
		
		outgrid.getCellFormatter().addStyleName(0,0,"topleft");
		outgrid.getCellFormatter().addStyleName(0,1,"topmiddler");
		outgrid.getCellFormatter().addStyleName(0,2,"topright");

		outgrid.getCellFormatter().addStyleName(1,0,"midleft");
		outgrid.getCellFormatter().addStyleName(1,1,"midmiddler");
		outgrid.getCellFormatter().setStyleName(1,2,"midright");

		outgrid.getCellFormatter().addStyleName(2,0,"bottomleft");
		outgrid.getCellFormatter().addStyleName(2,1,"bottommiddler");
		outgrid.getCellFormatter().addStyleName(2,2,"bottomright");
		
		outgrid.setCellPadding(0);
		outgrid.setCellSpacing(0);
		outgrid.setWidth("100%");

		outgrid.setHTML(0, 0, "");
		outgrid.setHTML(0, 1, "");
		outgrid.setHTML(0, 2, "");

		outgrid.setHTML(2, 0, "");
		outgrid.setHTML(2, 1, "");
		outgrid.setHTML(2, 2, "");
		
		outgrid.setWidget(1,1, outPanel);
		outPanel.setWidth("100%");
		outPanel.setHeight(UiKeke.kekeHeight+"px");
		
		// ICON 的处理。 

		img.setResource(IconManage.getIcon(node.imgName));
		outPanel.add(img, DockPanel.WEST);
		outPanel.setCellWidth(img, "80px");
		outPanel.setCellHorizontalAlignment(img, HasHorizontalAlignment.ALIGN_CENTER);
		outPanel.setCellVerticalAlignment(img, HasVerticalAlignment.ALIGN_MIDDLE);

		initWidget(outgrid);
		setStyleName("stick");
	}
}
