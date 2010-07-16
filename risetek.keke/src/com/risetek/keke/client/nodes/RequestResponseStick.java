package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.sticklet.Sticklets;

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

	@Override
	public void RemoteResponse(String[][] sticklet_src) {
		Sticklet s = Sticklets.loadSticklet(sticklet_src);
		D3Context.CallSticklet(s);
	}

}
