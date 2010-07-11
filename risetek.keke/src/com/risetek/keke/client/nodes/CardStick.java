package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDHandler;

public class CardStick extends Stick {

	public CardStick(String promotion) {
		super(promotion, "Card");
	}

	@Override
	public int enter(D3Context context) {
		ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
		return super.enter(context);
	}
	
	@Override
	public int leave(D3Context context) {
		ClientEventBus.INSTANCE.removeHandler(cardhandler, HIDCARDEvent.TYPE);
		return 0;
	}

	@Override
	public Composite getComposite() {
		return null;
	}

	private final HIDCARDHandler cardhandler = new HIDCARDHandler() {
		@Override
		public void onEvent(HIDCARDEvent event) {
		}
	};

	
}
