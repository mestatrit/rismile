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

import com.risetek.keke.client.data.PosConfig;
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

    /** Configuration object. */
    public PosConfig config() {
        return config;
    }
    /** Is the context locked? (keys locked) */
    public boolean locked() {
        return locked;
    }

    /** The lock thread object. */
    /*
    public LockThread lockThread() {
        return lockthread;
    }
	*/
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
    }

    // *** Main initializtion, transaction start and finish methods ***

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
}


