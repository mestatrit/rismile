/*
 * Copyright (C) 2001 Global Retail Technology, LLC
 * <http://www.globalretailtech.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.risetek.keke.client.context;

import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.PosEvents.PosEvent;
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
import com.risetek.keke.client.data.LoginWidget;
import com.risetek.keke.client.data.PosConfig;
import com.risetek.keke.client.datamodel.Kekes;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.ui.KekesComposite;


public class PosContext {

    private PosConfig config;
    private StringBuffer inputline;
    private PosEventStack eventstack;
    private boolean locked;
	Node	kekeTree;
	public Stack<Node>	NodesStack = new Stack<Node>();
	
	public static void Log(String message) {
		GWT.log(message);
	}

    public void loadEvent(PosEvent event)
    {
    	eventStack().pushEvent(event);
    	event.setContext(this);
    }
    
    /** Configuration object. */
    public PosConfig config() {
        return config;
    }
    /** Is the context locked? (keys locked) */
    public boolean locked() {
        return locked;
    }


    /** The event stack for this context. */
    public PosEventStack eventStack() {
        return eventstack;
    }

    public void setLocked(boolean value) {
        locked = value;
    }
    /**
     * PosConext constructors.
     */

    /**
     * Constructor PosContext with the site ID, the POS number and the
     * PosConfig ID.
     */
    KekesComposite view;
    Presenter	presenter;
    public AWidget		widget;
    public PosContext(KekesComposite view) {
    	this.view = view;
        inputline = new StringBuffer();
        eventstack = new PosEventStack();
       
 //       kekeTree = LoginWidget.INSTANCE.getNode();
 //       NodesStack.push(kekeTree);
        presenter = new Presenter(view,this);
        
        widget = LoginWidget.INSTANCE;
        widget.Execute(this);
        
        ClientEventBus.INSTANCE.addHandler(uphanlde, HIDUPEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(downhanlde, HIDDOWNEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(lefthandle, HIDLEFTEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(righthandler, HIDRIGHTEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
        }

    public void updateView() {
   //     presenter.upDate();
        presenter.upDate(widget);
    }
    /**
     * Converts the input buffer to an int.
     */
    public int input() {

        int val = 0;
        return val;
    }

    /**
     * Converts the input buffer to a double.
     */
    public double inputDouble() {
        if (inputline.length() == 0)
            return 0;
        return new Double(inputline.toString()).doubleValue();
    }

    /**
     * Returns the input buffer as a String.
     */
    public String inputLine() {
        if (inputline.length() == 0)
            inputline.append("0");
        return inputline.toString();
    }

    /**
     * Erase the last character in the input buffer (clear ()).
     */
    public void eraseLast() {
        inputline.deleteCharAt(inputline.length() - 1);
    }

    /**
     * Clears the input buffer.
     */
    public void clearInput() {
        if (inputline.length() > 0)
            inputline.delete(0, inputline.length());
    }

    /**
     * Multiplies the input buffer by the value.
     */
    public void multByInput(int i) {
        int tmp;
        if (inputline.length() == 0)
            tmp = 0;
        else
            tmp = input();
        tmp *= i;
        if (inputline.length() > 0)
            inputline.delete(0, inputline.length());
        inputline.append(Integer.toString(tmp));
    }

    /**
     * Add the value to the end of the input buffer as a
     * string.
     */
    public void addToInput(int i) {
        inputline.append(Integer.toString(i));
    }
    /**
     * Enable all keys.
     */
    public void enableKeys() {
    }

    /**
     * Disable keys that should disable, note number, enter, clear are left alone
     * since they are requrired for logon.
     */
    public void disableKeys() {
    }


    /**
     * Get the current user name.
     */
    public String userName() {
           return "login";
    }

    /**
     *Returns the current logged on user.
     */
    public String toString() {
           return "logon";
    }

    public void close() {

        inputline = null;
        eventstack = null;
    }
    
	HIDUPHandler uphanlde = new HIDUPHandler(){
		@Override
		public void onEvent(HIDUPEvent event) {
			Node p = widget.current.getParent(widget);
			if( p != null ) {
				
				p = p.children;
				if( p == widget.current )
					return;
				while( p.next != widget.current )
					p = p.next;
				
				widget.current = p;
				updateView();
			}
			
		}
	};
	
	
	HIDDOWNHandler downhanlde = new HIDDOWNHandler(){

		@Override
		public void onEvent(HIDDOWNEvent event) {
			if( widget.current.next != null ) {
				widget.current = widget.current.next;
				updateView();
			}
		}
	};
	
	HIDLEFTHandler lefthandle = new HIDLEFTHandler() {

		@Override
		public void onEvent(HIDLEFTEvent event) {
			if( widget.NodesStack.size() > 1 ) {
				widget.current = widget.NodesStack.pop();
				updateView();
			}
		}
		
	};
	
	HIDRIGHTHandler righthandler = new HIDRIGHTHandler() {

		@Override
		public void onEvent(HIDRIGHTEvent event) {
			if( widget.current.engage() != 0 ) {
				widget.NodesStack.push(widget.current);
				widget.current = widget.current.children;
				updateView();
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
	
}


