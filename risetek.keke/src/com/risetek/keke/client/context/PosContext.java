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
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.keke;
import com.risetek.keke.client.PosEvents.PosEvent;
import com.risetek.keke.client.PosEvents.PosException;
import com.risetek.keke.client.data.LoginWidget;
import com.risetek.keke.client.data.PosConfig;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.ui.KekesComposite;
/**
 *
 * A pos context is initially created with a site, pos number anda config ID.
 * initConfig () is called from the constructor to load the PosConfig,
 * based on config ID, all PosConfig relationships are loaded at that
 * time also.
 *
 *
 */
public class PosContext {

    private PosConfig config;
    private StringBuffer inputline;
    private PosEventStack eventstack;
    private boolean locked;
	public Vector<keke> kekes;

	Node	kekeTree;
	Node	currentNode;
	public static Stack<Node>	NodesStack = new Stack<Node>();
	
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
    public PosContext(KekesComposite view) {
    	this.view = view;
        inputline = new StringBuffer();
        eventstack = new PosEventStack();
        kekes = new Vector<keke>();
        
        kekeTree = LoginWidget.INSTANCE.getNode();
        NodesStack.push(kekeTree);
        currentNode = kekeTree.children;
        presenter = new Presenter(view);
        
	    Updates();
        
        /*
	    loadEvent(new PosInitEvent());
	    eventStack().nextEvent();
	    */
    }

    /*
     * 界面发生变化，更新界面。
     */
    public void Updates() {
    	
    	Node p = null ;
    	if( NodesStack.size() > 0)
    		p = NodesStack.lastElement();
    	presenter.setParentNode(p);
    	presenter.setCurrentNode(currentNode);
    	presenter.upDate();
    }
    
    public void clearKekes()
    {
        kekes = new Vector<keke>();
        currentKeke = 0;
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
    
	private int currentKeke = 0;

	public void renderKekes()
	{
		view.renderKekes(kekes, currentKeke);
	}
	/*
	public void downKeke(int value) {
		currentKeke++;
		if( currentKeke >= kekes.size() )
			currentKeke = kekes.size() - 1;
		renderKekes();
	}
	*/
	/*
	public void rightKeke(int value) throws PosException {
		keke k = kekes.elementAt(currentKeke);
	    loadEvent(k.event);
	    eventStack().nextEvent();
	}
	*/
	/*
	public void upKeke(int value) {
		currentKeke--;
	
		if( currentKeke < 0 )
			currentKeke = 0;

		renderKekes();
	}
	*/
}


