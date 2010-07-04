package com.risetek.keke.client.presenter;

import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
    KekesComposite view;
	Node	current;
	
	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(ASticklet sticklet ) {
		Node head = sticklet.HistoryNodesStack.pop();
		sticklet.HistoryNodesStack.push(head);
		view.renderKekes( sticklet.getChildrenNode(head) , sticklet.getCurrentNode());
	}
}
