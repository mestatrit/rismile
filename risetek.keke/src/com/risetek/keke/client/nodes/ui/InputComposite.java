package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.risetek.keke.client.nodes.Stick;

public class InputComposite extends StickComposite {
	
	DockPanel tipsPanel = new DockPanel();
	public Label input = new Label();
	

	public InputComposite(Stick node) {
		super(node);
		
		Label Type = new Label(node.Ticker);
		Label brief = new Label(node.Promotion);

		tipsPanel.setSize("100%", "100%");
		tipsPanel.add(Type, DockPanel.NORTH);
		tipsPanel.add(brief, DockPanel.CENTER);
		tipsPanel.add(input, DockPanel.SOUTH);

		outPanel.add(tipsPanel, DockPanel.EAST);
	}
}
