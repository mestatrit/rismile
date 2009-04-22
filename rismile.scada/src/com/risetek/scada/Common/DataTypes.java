/*
    Copyright (C) 2006-2007 Serotonin Software Technologies Inc.

    This program is free software; you can redistribute it and/or modify
    it under the terms of version 2 of the GNU General Public License as 
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA

    @author Matthew Lohbihler
 */
package com.risetek.scada.Common;

import java.util.ArrayList;


public class DataTypes {
    public static final int UNKNOWN = 0;
    public static final int BINARY = 1;
    public static final int MULTISTATE = 2;
    public static final int NUMERIC = 3;
    public static final int ALPHANUMERIC = 4;
    
    public static String getDataTypeDescription(int id) {
        switch (id) {
        case BINARY :
            return "Binary";
        case MULTISTATE :
            return "Multistate";
        case NUMERIC :
            return "Numeric";
        case ALPHANUMERIC :
            return "Alphanumeric";
        }
        return "Unknown";
    }
    
    public static final ArrayList<IntValuePair> dataTypes = new ArrayList<IntValuePair>();
    static {
        dataTypes.add(new IntValuePair(BINARY, getDataTypeDescription(BINARY)));
        dataTypes.add(new IntValuePair(MULTISTATE, getDataTypeDescription(MULTISTATE)));
        dataTypes.add(new IntValuePair(NUMERIC, getDataTypeDescription(NUMERIC)));
        dataTypes.add(new IntValuePair(ALPHANUMERIC, getDataTypeDescription(ALPHANUMERIC)));
    }
    
    public static int getDataType(Object value) {
        if (value == null)
            return UNKNOWN;
        if (value instanceof Double)
            return NUMERIC;
        if (value instanceof Boolean)
            return BINARY;
        if (value instanceof Integer)
            return MULTISTATE;
        if (value instanceof String)
            return ALPHANUMERIC;
       throw new RuntimeException("Unknown data value: "+ value.getClass());
    }
    
    public static Object stringToValue(String valueStr, int dataType) {
        switch (dataType) {
        case BINARY :
            if (valueStr == null || "0".equals(valueStr))
                return Boolean.FALSE;
            if ("1".equals(valueStr))
                return Boolean.TRUE;
            return new Boolean(valueStr);
        case MULTISTATE :
            if (valueStr == null)
                return new Integer(0);
            try {
                return new Integer(valueStr);
            }
            catch (NumberFormatException e) {}
            return new Integer(0);
        case NUMERIC :
            if (valueStr == null)
                return new Double(0);
            try {
                return new Double(valueStr);
            }
            catch (NumberFormatException e) {}
            return new Double(0);
        }
        return null;//StringUtils.escapeLT(valueStr);
    }
    
    public static String valueToString(Object value) {
        if (value == null)
            return null;
        return value.toString();
    }
    
    public static int compare(Object value1, Object value2) {
        switch (getDataType(value1)) {
        case NUMERIC :
            return ((Double)value1).compareTo((Double)value2);
        case BINARY :
            return ((Boolean)value1).compareTo((Boolean)value2);
        case MULTISTATE :
            return ((Integer)value1).compareTo((Integer)value2);
        case ALPHANUMERIC :
            return ((String)value1).compareTo((String)value2);
        }
        return 0;
    }
}
