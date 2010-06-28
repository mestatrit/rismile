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
/*
		Node p = head.children;
		int index = 0;
		Vector<Node> nodes = new Vector<Node>(); 
		while( p != null ) {
			nodes.add(p);
			p = p.next;
		}
*/
		view.renderKekes( head.children , widget.current);
	}
}
