package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.PosEvents.PosEvent;
import com.risetek.keke.client.context.UiKeke;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.resources.IconManage;

public class KekeComposite extends Composite {
	
	Label brief = new Label();
	Image img = new Image();
	DockPanel outPanel = new DockPanel();
	Grid g = new Grid(1,2);
	public PosEvent event;

	public KekeComposite(String title, String imgName, String eventName) {
		brief.setText(title);
		img.setResource(IconManage.getIcon(imgName));
		
		g.setWidget(0, 0, img);
		g.setWidget(0, 1, brief);
		g.setSize("100%", "100%");
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		outPanel.add(g, DockPanel.CENTER);
		initWidget(outPanel);
	}

	public KekeComposite(Node node, String eventName) {
		brief.setText(node.Promotion);
		img.setResource(IconManage.getIcon(node.imgName));
		
		g.setWidget(0, 0, img);
		g.setWidget(0, 1, brief);
		g.setSize("100%", "100%");
		outPanel.setPixelSize(UiKeke.kekeWidth, UiKeke.kekeHeight);
		outPanel.add(g, DockPanel.CENTER);
		initWidget(outPanel);
	}

}
