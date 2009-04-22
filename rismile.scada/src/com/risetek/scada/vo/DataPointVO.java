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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

//import com.serotonin.mango.Common;
import com.risetek.scada.Common.Common;
import com.serotonin.mango.rt.dataImage.PointValueTime;
import com.serotonin.mango.view.chart.ChartRenderer;
import com.serotonin.mango.view.text.TextRenderer;
import com.serotonin.mango.vo.dataSource.PointLocatorVO;
import com.serotonin.mango.vo.event.PointEventDetectorVO;
import com.serotonin.util.SerializationHelper;

public class DataPointVO implements Serializable {
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
    private int id = Common.NEW_ID;
    private String name;
    private int dataSourceId;
    private boolean enabled;
    private int pointFolderId;
    private int loggingType = LoggingTypes.ON_CHANGE;
    private double tolerance = 0;
    private int purgeType = Common.TimePeriods.YEARS;
    private int purgePeriod = 1;
    private TextRenderer textRenderer;
    private ChartRenderer chartRenderer;
    private List<PointEventDetectorVO> eventDetectors;
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
    private PointValueTime pointValue = new PointValueTime(null, -1);
    
    public void resetPointValue() {
        this.pointValue = new PointValueTime(null, -1);
    }
    public PointValueTime njbGetPointValue() {
        return pointValue;
    }
    public void njbSetPointValue(PointValueTime pointValue) {
        this.pointValue = pointValue;
    }
    
    
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public TextRenderer getTextRenderer() {
        return textRenderer;
    }
    public void setTextRenderer(TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
    }
    public ChartRenderer getChartRenderer() {
        return chartRenderer;
    }
    public void setChartRenderer(ChartRenderer chartRenderer) {
        this.chartRenderer = chartRenderer;
    }
    public List<PointEventDetectorVO> getEventDetectors() {
        return eventDetectors;
    }
    public void setEventDetectors(List<PointEventDetectorVO> eventDetectors) {
        this.eventDetectors = eventDetectors;
    }
    public List<UserComment> getComments() {
        return comments;
    }
    public void setComments(List<UserComment> comments) {
        this.comments = comments;
    }
    
    
    //
    ///
    /// Serialization
    ///
    //
    private static final int version = 2;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);
        SerializationHelper.writeSafeUTF(out, name);
        out.writeBoolean(enabled);
        out.writeInt(pointFolderId);
        out.writeInt(loggingType);
        out.writeDouble(tolerance);
        out.writeInt(purgeType);
        out.writeInt(purgePeriod);
        out.writeObject(textRenderer);
        out.writeObject(chartRenderer);
        out.writeObject(pointLocator);
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int ver = in.readInt();
        
        // Switch on the version of the class so that version changes can be elegantly handled.
        if (ver == 1) {
            name = SerializationHelper.readSafeUTF(in);
            enabled = in.readBoolean();
            pointFolderId = 0;
            loggingType = in.readInt();
            tolerance = in.readDouble();
            purgeType = in.readInt();
            purgePeriod = in.readInt();
            textRenderer = (TextRenderer)in.readObject();
            chartRenderer = (ChartRenderer)in.readObject();
            pointLocator = (PointLocatorVO)in.readObject();
        }
        else if (ver == 2) {
            name = SerializationHelper.readSafeUTF(in);
            enabled = in.readBoolean();
            pointFolderId = in.readInt();
            loggingType = in.readInt();
            tolerance = in.readDouble();
            purgeType = in.readInt();
            purgePeriod = in.readInt();
            textRenderer = (TextRenderer)in.readObject();
            chartRenderer = (ChartRenderer)in.readObject();
            
            // The spinwave changes were not correctly implemented, so we need to handle potential errors here.
            try {
                pointLocator = (PointLocatorVO)in.readObject();
            }
            catch (IOException e) {
                // Turn this guy off.
                enabled = false;
            }
        }
    }
}
