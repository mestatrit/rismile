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

public class Common {
    
    //private static final String SESSION_USER = "sessionUser";
    //private static final String ANON_VIEW_KEY = "anonymousViews";
    
    public static final int NEW_ID = -1;
    
    /*
     * Updating the Mango version:
     *   - Create a DBUpdate subclass for the old version number. This may not do anything in particular to the
     *     schema, but is still required to update the system settings so that the database has the correct version.
     */
    public static final String getVersion() {
        return "1.4.2";
    }
    
    public interface ContextKeys {
        String DATABASE_ACCESS = "DATABASE_ACCESS";
        String IMAGE_SETS = "IMAGE_SETS";
        String RUNTIME_MANAGER = "RUNTIME_MANAGER";
        String SCHEDULER = "SCHEDULER";
        String EVENT_MANAGER = "EVENT_MANAGER";
        String FREEMARKER_CONFIG = "FREEMARKER_CONFIG";
        String BACKGROUND_PROCESSING = "BACKGROUND_PROCESSING";
        String HTTP_RECEIVER_MULTICASTER = "HTTP_RECEIVER_MULTICASTER";
        String DOCUMENTATION_MANIFEST = "DOCUMENTATION_MANIFEST";
    }
    
    public interface TimePeriods {
        int SECONDS = 1;
        int MINUTES = 2;
        int HOURS = 3;
        int DAYS = 4;
        int WEEKS = 5;
        int MONTHS = 6;
        int YEARS = 7;
    }
    
    public interface GroveServlets {
        String MANGO_ID = "mangoId";
        String VERSION_CHECK = "versionCheck";
        String MANGO_LOG = "mangoLog";
    }
    
    public static String getPeriodDescription(int periodType, int periods) {
        String periodDescription;
        switch (periodType) {
        case TimePeriods.SECONDS:
            periodDescription = " second";
            break;
        case TimePeriods.MINUTES:
            periodDescription = " minute";
            break;
        case TimePeriods.HOURS:
            periodDescription = " hour";
            break;
        case TimePeriods.DAYS:
            periodDescription = " day";
            break;
        case TimePeriods.WEEKS:
            periodDescription = " week";
            break;
        case TimePeriods.MONTHS:
            periodDescription = " month";
            break;
        case TimePeriods.YEARS:
            periodDescription = " year";
            break;
        default :
            throw new RuntimeException("Unsupported time period: "+ periodType);
        }
        
        if (periods != 1)
            periodDescription += "s";
        
        return Integer.toString(periods) + periodDescription;
    }

}
