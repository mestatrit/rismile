package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.PosEvents.PosEvent;
import com.risetek.keke.client.context.UiKeke;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.resources.IconManage;

public class InputComposite extends Composite {
	
	Label Type = new Label();
	Label brief = new Label();
	public Label input = new Label();

	Image img = new Image();
	DockPanel outPanel = new DockPanel();
	Grid g = new Grid(3,1);
	public PosEvent event;

	public InputComposite(Node node) {
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		Type.setText(node.Ticker);
		brief.setText(node.Promotion);
		img.setResource(IconManage.getIcon(node.imgName));
		outPanel.add(img, DockPanel.WEST);
		outPanel.setCellHeight(img, "90%");
		outPanel.setCellWidth(img, "20%");
		outPanel.setCellHorizontalAlignment(img, HasHorizontalAlignment.ALIGN_CENTER);
		outPanel.setCellVerticalAlignment(img, HasVerticalAlignment.ALIGN_MIDDLE);
	    g.setCellPadding(0);
	    g.setCellSpacing(0);
		g.setWidget(0, 0, Type);
		g.setWidget(1, 0, brief);
		g.setWidget(2, 0, input);
		g.setSize("100%", "100%");
		outPanel.add(g, DockPanel.EAST);
		initWidget(outPanel);
		setStyleName("KekeComposite");
	}

}
