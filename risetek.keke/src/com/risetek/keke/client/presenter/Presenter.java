package com.risetek.keke.client.presenter;

import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
    KekesComposite view;
	Node	current;
	
	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(AWidget widget ) {
		Node head = widget.NodesStack.pop();
		widget.NodesStack.push(head);
		view.renderKekes( head.children , widget.current);
	}
}
