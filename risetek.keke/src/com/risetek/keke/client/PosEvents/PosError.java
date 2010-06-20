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

package com.risetek.keke.client.PosEvents;



/**
 * PosEvent for handling errors. Typically another
 * PosEvent will encounter an error condition, create
 * this object with the event type and push it on the
 * stack. The clear key clears it. This mainly provides
 * user feedback. Catalog all POS errors here
 *
 * @author  Quentin Olson
 */
public class PosError extends PosEvent {

    /** Base event */
    public static final int ERROR_EVENT = 1000;
    /** Bad input, or invalid state transition. */
    public static final int INVALID_INPUT = 1001;
    /** Insufficient funds for current operation. */
    public static final int INSUFFICIENT_FUNDS = 1004;
    /** Manager required for this operation */
    public static final int MGR_REQUIRED = 1005;
    /**  */
    public static final int LOGON_FAILED = 1006;
    /**  */
    public static final int BAD_USER = 1007;
    /**  */
    public static final int BAD_PASSWORD = 1008;
    /**  */
    public static final int INVALID_DRAWER_NO = 1009;
    /**  */
    public static final int INVALID_CUSTOMER_NO = 1010;
    /**  */
    public static final int INVALID_CHECK_NO = 1011;
    /**  */
    public static final int RECALL_FALLED = 1012;
    /**  */
    public static final int OVER_MEDIA_LIMIT = 1013;
    /**  */
    public static final int UNDER_MEDIA_LIMIT = 1014;
    /**  */
    public static final int INVALID_CC = 1015;
    /**  */
    public static final int LOCKED = 1016;
    /**  */
    public static final int CASH_EXCEEDED = 1017;

    private int errorcode;
    private String prompttext;

    /** Error code */
    public int errorCode() {
        return errorcode;
    }

    /** User feedback prompt. */
    public String promptText() {
        return prompttext;
    }

    /**
     * Constructor with error code. Depending on
     * code (switch) set up promptText ().
     */
    public PosError(int code) {

        super();
        errorcode = code;

        switch (errorcode) {
            case INVALID_INPUT:
                prompttext = "InvalidInput";
                break;
            case INSUFFICIENT_FUNDS:
                prompttext = "InsufficientFunds";
                break;
            case MGR_REQUIRED:
                prompttext = "MgrIntervention";
                break;
            case LOGON_FAILED:
                prompttext = "Failed";
                break;
            case BAD_USER:
                prompttext = "BadName";
                break;
            case BAD_PASSWORD:
                prompttext = "BadPass";
                break;
            case INVALID_DRAWER_NO:
            case INVALID_CUSTOMER_NO:
            case INVALID_CHECK_NO:
            case RECALL_FALLED:
                prompttext = "Failed";
                break;
            case CASH_EXCEEDED:
                prompttext = "CashLimitExceeded";
                break;
            case OVER_MEDIA_LIMIT:
            case UNDER_MEDIA_LIMIT:
            case INVALID_CC:
            case LOCKED:
            default:
                prompttext = " -- unknown error --";
                break;
        }
    }

    /**
     * Do nothing.
     */
    public void engage(int value) {
        context().eventStack().nextEvent();
    }

    /** Always return true. */
    public boolean validTransition(String event) {
        return true;
    }

    /** Clear impementation for clear, do nothing. */
    public void clear() {
    }

    /** Always return true. */
    public boolean checkProfile(int event) {
        return true;
    }

    private static String eventname = "PosError";

    /** Return staic name. */
    public String toString() {
        return eventname;
    }

    /** Return staic name. */
    public static String eventName() {
        return eventname;
    }
}


