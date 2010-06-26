package com.risetek.keke.client.presenter;

import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDDOWNEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDDOWNHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDLEFTEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDLEFTHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDRIGHTEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDRIGHTHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDUPEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDUPHandler;
import com.risetek.keke.client.data.AWidget;
import com.risetek.keke.client.datamodel.Kekes;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
	PosContext context;
    KekesComposite view;
	Node	current;
/*
	// 对事件处理的函数：
	HIDUPHandler uphanlde = new HIDUPHandler(){
		@Override
		public void onEvent(HIDUPEvent event) {
			Node p = current.getParent(context);
			if( p != null ) {
				
				p = p.children;
				if( p == current )
					return;
				while( p.next != current )
					p = p.next;
				
				current = p;
				upDate();
			}
			
		}
	};
	
	HIDDOWNHandler downhanlde = new HIDDOWNHandler(){

		@Override
		public void onEvent(HIDDOWNEvent event) {
			if( current.next != null ) {
				current = current.next;
				upDate();
			}
		}
	};
	
	HIDLEFTHandler lefthandle = new HIDLEFTHandler() {

		@Override
		public void onEvent(HIDLEFTEvent event) {
			if( context.NodesStack.size() > 1 ) {
				current = context.NodesStack.pop();
				upDate();
			}
		}
		
	};
	
	HIDRIGHTHandler righthandler = new HIDRIGHTHandler() {

		@Override
		public void onEvent(HIDRIGHTEvent event) {
			if( current.engage() != 0 ) {
				context.NodesStack.push(current);
				current = null;
				upDate();
			}
		}
		
	};
	
	HIDCARDHandler cardhandler = new HIDCARDHandler() {

		@Override
		public void onEvent(HIDCARDEvent event) {
		//	if( Kekes.current.defaultOption instanceof CardKeke )
			{
				// Kekes.current.moveRight();
				Kekes.current.defaultOption.card();
			}
		}
		
	};
*/		
	
	public Presenter(KekesComposite view, PosContext context) {
		this.context = context;
		this.view = view;
		/*
        ClientEventBus.INSTANCE.addHandler(uphanlde, HIDUPEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(downhanlde, HIDDOWNEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(lefthandle, HIDLEFTEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(righthandler, HIDRIGHTEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
        */
	}

	public void upDate(AWidget widget ) {
		Node head = widget.NodesStack.pop();
		widget.NodesStack.push(head);
		view.renderKekes( head , widget.current);
	}
/*
	public void upDate() {
		if( context.NodesStack.size() > 0 ) {
			Node p = context.NodesStack.pop();
			context.NodesStack.push(p);
			if( current == null )
				current = p.children;
			view.renderKekes( p.children , current);
		}
	}
	*/
}
