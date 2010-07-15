package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.nodes.ui.PromotionComposite;

public class RequestResponseStick extends Stick {

	public RequestResponseStick(String promotion) {
		super(promotion, "Response");
	}

	@Override
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
}
