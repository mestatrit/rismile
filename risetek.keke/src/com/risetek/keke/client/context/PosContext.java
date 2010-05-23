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

import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;
import com.risetek.keke.client.Risetek_keke;
import com.risetek.keke.client.keke;
import com.risetek.keke.client.data.PosConfig;
import com.risetek.keke.client.events.PosEvent;
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
    public PosContext() {
        inputline = new StringBuffer();
        eventstack = new PosEventStack();
        kekes = new Vector<keke>();
    }

    public void clearKekes()
    {
        kekes = new Vector<keke>();
        currentKeke = -1;
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
    
	public static int maxKeke = 5 - 1;
	public int currentKeke = -1;
    
	public void renderKekes()
	{
		// 应该计算多少个可可，然后安排合适的位置进行显示。
		if( kekes.size() == 0 )
			return;

		// 首先清除显示内容。
		for( int spacekeke = 0; spacekeke < maxKeke; spacekeke++ )
		{
			Risetek_keke.keke.setWidget(spacekeke, 0, new HTML(" "));
		}
		
		
		int mid = maxKeke /2 ;

		if( currentKeke == -1 )
		{
			currentKeke = kekes.size() > maxKeke ? maxKeke : kekes.size() - 1;
			currentKeke = currentKeke / 2;
		}

		int index = currentKeke -1;
		for( int spacekeke = mid-1; spacekeke >= 0 && index >= 0; spacekeke--, index-- )
		{
			Risetek_keke.keke.setWidget(spacekeke, 0, (kekes.elementAt(index)));
		}

		index = currentKeke+1;
		for( int spacekeke = mid+1; spacekeke < maxKeke && index < kekes.size(); spacekeke++, index++ )
		{
			Risetek_keke.keke.setWidget(spacekeke, 0, (kekes.elementAt(index)));
		}
		
		Risetek_keke.keke.setWidget(mid, 0, (kekes.elementAt(currentKeke)));
		Risetek_keke.keke.getRowFormatter().setStyleName(mid, Risetek_keke.style.hilight());
	}

    
}


