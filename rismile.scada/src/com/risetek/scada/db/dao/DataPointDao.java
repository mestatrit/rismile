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
package com.risetek.scada.db.dao;
/*
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import com.google.appengine.api.datastore.Blob;
import com.risetek.scada.Common.IntValuePair;
import com.risetek.scada.db.dataPoints;
import com.risetek.scada.vo.UserComment;
*/

import com.risetek.scada.Common.Common;
import com.risetek.scada.db.PMF;
import com.risetek.scada.vo.DataPointVO;

import java.util.Comparator;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class DataPointDao extends BaseDao {
    
    //
    ///
    /// Data Points
    ///
    //
	/*
    private static final String DATA_POINT_SELECT =
        "select dp.id, dp.dataSourceId, dp.data, ds.name, ds.dataSourceType "+
        "from dataPoints dp join dataSources ds on ds.id = dp.dataSourceId ";
    */
	public List<DataPointVO> getDataPoints() {
    	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(DataPointVO.class);
		//query.setRange(0, 10);
	    //query.setOrdering("id desc");

	    try {
	        List<DataPointVO> results = (List<DataPointVO>)query.execute();
	        return results;
	    } finally {
	        query.closeAll();
	    }

/*
        List<DataPointVO> dps = query(DATA_POINT_SELECT, new DataPointRowMapper());
        setRelationalData(dps);
        Collections.sort(dps, new DataPointNameComparator());
        return dps;
*/        
    }
    
    public List<DataPointVO> getDataPoints(int dataSourceId) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(DataPointVO.class);
		//query.setRange(0, 10);
	    //query.setOrdering("id desc");

	    try {
	        List<DataPointVO> results = (List<DataPointVO>)query.execute();
	        return results;
	    } finally {
	        query.closeAll();
	    }
/*    	
        List<DataPointVO> dps = query(DATA_POINT_SELECT +" where dp.dataSourceId=?",
                new Object[] {dataSourceId}, new DataPointRowMapper());
        setRelationalData(dps);
        Collections.sort(dps, new DataPointNameComparator());
        return dps;
*/
    }
    /*
    private static class DataPointNameComparator implements Comparator<DataPointVO> {
        public int compare(DataPointVO dp1, DataPointVO dp2) {
            if (null == dp1.getName())
                return -1;
            return dp1.getName().compareToIgnoreCase(dp2.getName());
        }
    }
    */
    /*
    public DataPointVO getDataPoint(int id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(dataPoints.class);
		query.setRange(0, 10);
	    query.setOrdering("id desc");

	    try {
	        List<DataPointVO> results = (List<DataPointVO>)query.execute();
	        return results.;
	    } finally {
	        query.closeAll();
	    }

         DataPointVO dp = queryForObject(DATA_POINT_SELECT +" where dp.id=?",
                new Object[] {id}, new DataPointRowMapper(), null);
        setRelationalData(dp);
        return dp;

    }
    */
/*    
    public DataPointVO getDataPointFromPointViewId(int pointViewId) {
        DataPointVO dp = queryForObject(
                DATA_POINT_SELECT +" where dp.id=(select dataPointId from pointViews where id=?)",
                new Object[] {pointViewId}, new DataPointRowMapper(), null);
        setRelationalData(dp);
        return dp;
    }
    
    class DataPointRowMapper implements GenericRowMapper<DataPointVO> {
        public DataPointVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DataPointVO dp;
            try {
                dp = (DataPointVO)SerializationHelper.readObject(rs.getBlob(3).getBinaryStream());
            }
            catch (ShouldNeverHappenException e) {
                dp = new DataPointVO();
                dp.setName("Point configuration lost. Please recreate.");
                dp.setTextRenderer(new PlainRenderer(""));
            }
            dp.setId(rs.getInt(1));
            dp.setDataSourceId(rs.getInt(2));
            
            // Data source information.
            dp.setDataSourceName(rs.getString(4));
            dp.setDataSourceTypeId(rs.getInt(5));
            
            // The spinwave changes were not correctly implemented, so we need to handle potential errors here.
            if (dp.getPointLocator() == null) {
                // Use the data source tpe id to determine what type of locator is needed.
                dp.setPointLocator(new DataSourceDao().getDataSource(dp.getDataSourceId()).createPointLocator());
            }
            
            return dp;
        }
    }
    
    private void setRelationalData(List<DataPointVO> dps) {
        for (DataPointVO dp : dps)
            setRelationalData(dp);
    }
    
    private void setRelationalData(DataPointVO dp) {
        if (dp == null)
            return;
        setEventDetectors(dp);
        setPointComments(dp);
    }
    
    
    */
    
    public void saveDataPoint(final DataPointVO dp) {
    	
		PersistenceManager pm = PMF.get().getPersistenceManager();

        //if (dp.getId() == Common.NEW_ID)
        {
			try {
				pm.makePersistent(dp);
			} finally {
				pm.close();
			}
        }
        /*
        else
        {
        	// TODO: update!
			try {
				pm.makePersistent(dp);
			} finally {
				pm.close();
			}
        }
    	*/
/*    	
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // Decide whether to insert or update.
                if (dp.getId() == Common.NEW_ID)
                    insertDataPoint(dp);
                else
                    updateDataPoint(dp);
            }
        });
        */
    }
    /*
    private void insertDataPoint(final DataPointVO dp) {
        // Create a default text renderer
        dp.setTextRenderer(new PlainRenderer(""));
        
        // Insert the main data point record.
        dp.setId(doInsert("insert into dataPoints (dataSourceId, data) values (?,?)",
                new Object[] {dp.getDataSourceId(), SerializationHelper.writeObject(dp)},
                new int[] {Types.INTEGER, Types.BLOB}));
        
        // Save the relational information.
        saveEventDetectors(dp);
    }
    
    private void updateDataPoint(final DataPointVO dp) {
        // Delete any point values where data type doesn't match the vo, just in case the data type was changed.
        new PointValueDao().deletePointValuesWithMismatchedType(dp.getId(), dp.getPointLocator().getDataTypeId());
        
        // Save the VO information.
        updateDataPointShallow(dp);
        
        // Save the relational information.
        saveEventDetectors(dp);
    }
    
    private void updateDataPointShallow(final DataPointVO dp) {
        ejt.update("update dataPoints set data=? where id=?",
                new Object[] {SerializationHelper.writeObject(dp), dp.getId()}, new int[] {Types.BLOB, Types.INTEGER});
    }
    
    public void deleteDataPoints(final int dataSourceId) {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                List<Integer> pointIds = queryForList("select id from dataPoints where dataSourceId=?",
                        new Object[] {dataSourceId}, Integer.class);
                if (pointIds.size() > 0)
                    deleteDataPointImpl(createDelimitedList(new HashSet<Integer>(pointIds), ",", null));
            }
        });
    }
    
    public void deleteDataPoint(final int dataPointId) {
        getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                deleteDataPointImpl(Integer.toString(dataPointId));
            }
        });
    }
    
    private void deleteDataPointImpl(String dataPointIdList) {
        dataPointIdList = "("+ dataPointIdList +")";
        ejt.update("delete from eventHandlers where eventTypeId="+ EventType.EventSources.DATA_POINT +
                " and eventTypeRef1 in "+ dataPointIdList);
        ejt.update("delete from userComments where commentType=2 and typeKey in "+ dataPointIdList);
        ejt.update("delete from pointEventDetectors where dataPointId in "+ dataPointIdList);
        ejt.update("delete from pointValues where dataPointId in "+ dataPointIdList);
        ejt.update("delete from dataPointUsers where dataPointId in "+ dataPointIdList);
        ejt.update("delete from pointViews where dataPointId in "+ dataPointIdList);
        ejt.update("delete from watchListPoints where dataPointId in "+ dataPointIdList);
        ejt.update("delete from dataPoints where id in "+ dataPointIdList);
    }
    
    
    
    //
    //
    ///
    /// Event detectors
    ///
    //
    //
    private static final String EVENT_DETECTOR_SELECT =
        "select id, detectorType, alarmLevel, limit, duration, durationType, binaryState, multistateState, "+
        "  changeCount, alphanumericState "+
        "from pointEventDetectors "+
        "where dataPointId=? "+
        "order by id";
    private void setEventDetectors(final DataPointVO dp) {
        dp.setEventDetectors(query(EVENT_DETECTOR_SELECT, new Object[] {dp.getId()},
                new GenericRowMapper<PointEventDetectorVO>() {
                    public PointEventDetectorVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PointEventDetectorVO detector = new PointEventDetectorVO();
                        int i=0;
                        detector.setId(rs.getInt(++i));
                        detector.setDetectorType(rs.getInt(++i));
                        detector.setAlarmLevel(rs.getInt(++i));
                        detector.setLimit(rs.getDouble(++i));
                        detector.setDuration(rs.getInt(++i));
                        detector.setDurationType(rs.getInt(++i));
                        detector.setBinaryState(charToBool(rs.getString(++i)));
                        detector.setMultistateState(rs.getInt(++i));
                        detector.setChangeCount(rs.getInt(++i));
                        detector.setAlphanumericState(rs.getString(++i));
                        detector.njbSetDataPoint(dp);
                        return detector;
                    }
                }));
    }
    
    private static final String EVENT_DETECTOR_INSERT =
        "insert into pointEventDetectors "+
        "  (dataPointId, detectorType, alarmLevel, limit, duration, durationType, "+
        "  binaryState, multistateState, changeCount, alphanumericState) "+
        "values (?,?,?,?,?,?,?,?,?,?)";
    private static final String EVENT_DETECTOR_UPDATE =
        "update pointEventDetectors set alarmLevel=?, limit=?, duration=?, durationType=?, "+
        "  binaryState=?, multistateState=?, changeCount=?, alphanumericState=? " +
        "where id=?";
    private void saveEventDetectors(DataPointVO dp) {
        // Get the ids of the existing detectors for this point.
        final List<Integer> existingDetectorIds = queryForList(
                "select id from pointEventDetectors where dataPointId=?",
                new Object[] {dp.getId()}, Integer.class);
        
        // Insert or update each detector in the point.
        for (PointEventDetectorVO ped : dp.getEventDetectors()) {
            if (ped.getId() < 0)
                // Insert the record.
                ped.setId(doInsert(EVENT_DETECTOR_INSERT,
                        new Object[] { dp.getId(), ped.getDetectorType(), ped.getAlarmLevel(), ped.getLimit(), 
                                ped.getDuration(), ped.getDurationType(), boolToChar(ped.isBinaryState()), 
                                ped.getMultistateState(), ped.getChangeCount(), ped.getAlphanumericState() },
                        new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DOUBLE, Types.INTEGER,
                                Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR}));
            else {
                ejt.update(EVENT_DETECTOR_UPDATE,
                        new Object[] { ped.getAlarmLevel(), ped.getLimit(), ped.getDuration(), ped.getDurationType(),
                                boolToChar(ped.isBinaryState()), ped.getMultistateState(), ped.getChangeCount(),
                                ped.getAlphanumericState(), ped.getId()}, 
                        new int[] { Types.INTEGER, Types.DOUBLE, Types.INTEGER, Types.INTEGER, Types.VARCHAR, 
                                Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER });
                
                // Remove the id reference from the list of existing detectors.
                existingDetectorIds.remove(new Integer(ped.getId()));
            }
        }
        
        // Delete detectors for any remaining ids in the list of existing detectors.
        for (Integer id : existingDetectorIds) {
            ejt.update(
                    "delete from eventHandlers "+
                    "where eventTypeId="+ EventType.EventSources.DATA_POINT +" and eventTypeRef1=? and eventTypeRef2=?",
                    new Object[] {dp.getId(), id});
            ejt.update("delete from pointEventDetectors where id=?", new Object[] {id});
        }
    }
    
    
    
    //
    /// Point comments
    //
    private static final String POINT_COMMENT_SELECT = UserCommentRowMapper.USER_COMMENT_SELECT +
            "where uc.commentType= "+ UserComment.TYPE_POINT +" and uc.typeKey=? "+
            "order by uc.ts";
    private void setPointComments(DataPointVO dp) {
        dp.setComments(query(POINT_COMMENT_SELECT, new Object[] {dp.getId()},
                new UserCommentRowMapper()));
    }
    
*/

	@Override
	public void onResponse(String resp) {
		
	}
}
