package com.risetek.keke.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.risetek.keke.client.nodes.Node;

public class KekesComposite extends Composite implements UiKeke {

	private Grid keke = new Grid(maxKeke,1);
	public KekesComposite() {
		initWidget(keke);
		this.setWidth("100%");
		setHeight(maxKeke*kekeHeight+"px");
	    keke.setCellPadding(0);
	    keke.setCellSpacing(0);
	    this.setStyleName("KekesComposite");
	}

	private void clearStickers() {
		for( int spacekeke = 0; spacekeke < maxKeke; spacekeke++ )
		{
			HTMLPanel p = new HTMLPanel("");
			p.setWidth("100%");
			p.setHeight((kekeHeight+6)+"px");
			keke.setWidget(spacekeke, 0, p);
		}
	}
	
	public void renderKekes(Node head, Node current)
	{
		clearStickers();
		int numberOfNodes = 0;
		int numberToCurrentNodes = 0;
		Node p = head;
		while( p != null ) {
			numberOfNodes++;
			p = p.next;
		}
		
		p = head;
		while( (p != current) && (p != null) ) {
			numberToCurrentNodes++;
			p = p.next;
		}
		
		Node firstRender = null;
		while( numberToCurrentNodes-- > 0) {
			firstRender = firstRender == null ? head : firstRender.next;
		}

		// 显示活动前的Nodes
		int index = 0;
		while( firstRender != null ) {
			keke.setWidget(index++, 0, firstRender.getComposite());
			firstRender = firstRender.next;
		}
		// 绘制当前有效的Node
		keke.setWidget(1, 0, current.getComposite());
		keke.getRowFormatter().setStyleName(1, "hilight");
		
		index = 2;
		p = current.next;
		while( ( p != null ) && ( index < maxKeke ) ) {
			keke.setWidget(index, 0, p.getComposite());
			index++;
			p = p.next; 
		}		
	}
	
}
