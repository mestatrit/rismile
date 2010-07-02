package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.nodes.Node;


public class PromotionComposite extends StickComposite {
	
	Label Type = new Label();
	public Label brief = new Label();
	DockPanel tipsPanel = new DockPanel();
	
	
	public PromotionComposite(Node node) {
		super(node);
		
		Type.setText(node.Ticker);
		brief.setText(node.Promotion);

		tipsPanel.setSize("100%", "100%");
		tipsPanel.add(Type, DockPanel.NORTH);
		tipsPanel.add(brief, DockPanel.CENTER);
		outPanel.add(tipsPanel, DockPanel.EAST);
	}

}
