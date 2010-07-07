package com.risetek.keke.client.presenter;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
	private KekesComposite view;
	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(D3Context context ) {
		Sticklet sticklet = context.getSticklet();
		Stick head = sticklet.HistoryNodesStack.peek();
		view.renderKekes( sticklet.getChildrenNode(head) , sticklet.getCurrentNode());
	}
}
