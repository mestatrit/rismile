package com.risetek.keke.client.ui;

import java.util.Vector;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.risetek.keke.client.keke;
import com.risetek.keke.client.context.UiKeke;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.ticker.TickerFactory;

public class KekesComposite extends Composite implements UiKeke {

	Grid keke = new Grid(maxKeke,1);
	public KekesComposite() {
		initWidget(keke);
		setPixelSize(kekeWidth, maxKeke*kekeHeight);
	    keke.setCellPadding(0);
	    keke.setCellSpacing(0);
	}

	public void clearStickers() {
		for( int spacekeke = 0; spacekeke < maxKeke; spacekeke++ )
		{
			HTMLPanel p = new HTMLPanel("");
			p.setPixelSize(kekeWidth, kekeHeight);
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
			keke.setWidget(index++, 0, TickerFactory.Produce(firstRender).comp);
			firstRender = firstRender.next;
		}
		// 绘制当前有效的Node
		keke.setWidget(1, 0, TickerFactory.Produce(current).comp);
		keke.getRowFormatter().setStyleName(1, "hilight");
		
		index = 2;
		p = current.next;
		while( ( p != null ) && ( index < maxKeke ) ) {
			keke.setWidget(index, 0, TickerFactory.Produce(p).comp);
			index++;
			p = p.next; 
		}		
	}
	
}
