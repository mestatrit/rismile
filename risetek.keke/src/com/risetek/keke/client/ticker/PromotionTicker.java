package com.risetek.keke.client.ticker;

import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.ui.KekeComposite;

public class PromotionTicker extends Ticker {
	

	public PromotionTicker(Node node) {
		comp = new KekeComposite(node,"Promotion");
	}
}
