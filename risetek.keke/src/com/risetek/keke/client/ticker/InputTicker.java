package com.risetek.keke.client.ticker;

import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.ui.InputComposite;
import com.risetek.keke.client.ui.KekeComposite;

public class InputTicker extends Ticker {
	

	public InputTicker(Node node) {
		comp = new InputComposite(node);
	}
}
