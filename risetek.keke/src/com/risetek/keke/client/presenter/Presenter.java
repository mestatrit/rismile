package com.risetek.keke.client.presenter;

import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
    KekesComposite view;
	Stick	current;
	
	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(ASticklet sticklet ) {
		Stick head = sticklet.HistoryNodesStack.pop();
		sticklet.HistoryNodesStack.push(head);
		view.renderKekes( sticklet.getChildrenNode(head) , sticklet.getCurrentNode());
	}
}
