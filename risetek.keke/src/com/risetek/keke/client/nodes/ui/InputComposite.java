package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.nodes.Node;

public class InputComposite extends StickComposite {
	
	Label Type = new Label();
	public Label brief = new Label();
	DockPanel tipsPanel = new DockPanel();
	public Label input = new Label();
	

	public InputComposite(Node node) {
		super(node);
		
		Type.setText(node.Ticker);
		brief.setText(node.Promotion);

		tipsPanel.setSize("100%", "100%");
		tipsPanel.add(Type, DockPanel.NORTH);
		tipsPanel.add(brief, DockPanel.CENTER);
		tipsPanel.add(input, DockPanel.SOUTH);

		outPanel.add(tipsPanel, DockPanel.EAST);
	}
}
