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

package com.risetek.keke.client.events;


/**
 *
 * @author  Quentin Olson
 */
public class PosException extends java.lang.Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -324679433441812275L;

	public PosException() {
    }

    private static String eventname = "PosException";

    public String toString() {
        return eventname;
    }

    public static String eventName() {
        return eventname;
    }
}


