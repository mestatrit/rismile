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
package com.risetek.scada.vo;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Blob;

import com.risetek.scada.Common.Common;
import com.risetek.scada.vo.dataSource.PointLocatorVO;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DataPointVO {
    private static final long serialVersionUID = -1;
    
    public interface LoggingTypes {
        int ON_CHANGE = 1;
        int ALL = 2;
        int NONE = 3;
    }
    
    public interface PurgeTypes {
        int DAYS = Common.TimePeriods.DAYS;
        int WEEKS = Common.TimePeriods.WEEKS;
        int MONTHS = Common.TimePeriods.MONTHS;
        int YEARS = Common.TimePeriods.YEARS;
    }
    
    public String getDataTypeDescription() {
        return pointLocator.getDataTypeDescription();
    }
    
    public String getConfigurationDescription() {
        return pointLocator.getConfigurationDescription();
    }
    
    public boolean isNew() {
        return id == Common.NEW_ID;
    }
    
    //
    ///
    /// Properties
    ///
    //
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;// = Common.NEW_ID;
    private String name;
	@Persistent
    private int dataSourceId;
	@Persistent
	private Blob data;
	
	
    public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	private boolean enabled;
    private int pointFolderId;
    private int loggingType = LoggingTypes.ON_CHANGE;
    private double tolerance = 0;
    private int purgeType = Common.TimePeriods.YEARS;
    private int purgePeriod = 1;
    private List<UserComment> comments;
    
    private PointLocatorVO pointLocator;
    
    //
    ///
    /// Convenience data from data source
    ///
    //
    private int dataSourceTypeId;
    private String dataSourceName;
    
    //
    ///
    /// Runtime data
    ///
    //
    /*
     * This is used by the watch list and graphic views to cache the last known value for a point to determine if
     * the browser side needs to be refreshed. Initially set to this value so that point views will update 
     * (since null values in this case do in fact equal each other).
     */
    
    
    public int getDataSourceId() {
        return dataSourceId;
    }
    public void setDataSourceId(int dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public int getPointFolderId() {
        return pointFolderId;
    }
    public void setPointFolderId(int pointFolderId) {
        this.pointFolderId = pointFolderId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PointLocatorVO getPointLocator() {
        return pointLocator;
    }
    public void setPointLocator(PointLocatorVO pointLocator) {
        this.pointLocator = pointLocator;
    }
    public String getDataSourceName() {
        return dataSourceName;
    }
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
    public int getDataSourceTypeId() {
        return dataSourceTypeId;
    }
    public void setDataSourceTypeId(int dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }
    public int getLoggingType() {
        return loggingType;
    }
    public void setLoggingType(int loggingType) {
        this.loggingType = loggingType;
    }
    public int getPurgePeriod() {
        return purgePeriod;
    }
    public void setPurgePeriod(int purgePeriod) {
        this.purgePeriod = purgePeriod;
    }
    public int getPurgeType() {
        return purgeType;
    }
    public void setPurgeType(int purgeType) {
        this.purgeType = purgeType;
    }
    public double getTolerance() {
        return tolerance;
    }
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
    public List<UserComment> getComments() {
        return comments;
    }
    public void setComments(List<UserComment> comments) {
        this.comments = comments;
    }

	public DataPointVO(int dataSourceId, Blob data) {
		this.dataSourceId = dataSourceId;
		this.data = data;
	}
    
    
    //
    ///
    /// Serialization
    ///
    //
    //private static final int version = 2;
    
}
