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
package com.risetek.scada.vo.dataSource;

import java.io.Serializable;

import com.risetek.scada.rt.dataSource.PointLocatorRT;

public interface PointLocatorVO extends Serializable {
    public int getDataTypeId();
    public String getDataTypeDescription();
    public String getConfigurationDescription();
    public boolean isSettable();
    public PointLocatorRT createRuntime();
//    public void validate(DwrResponse response);
}
