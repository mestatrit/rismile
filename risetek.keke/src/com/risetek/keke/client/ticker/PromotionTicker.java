package com.risetek.keke.client.ticker;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.risetek.keke.server.node.Node;

public class PromotionTicker extends Composite {
	
	HTMLPanel panel = new HTMLPanel("");
	
	public PromotionTicker(Node node) {
		initWidget(panel);
	}
}
