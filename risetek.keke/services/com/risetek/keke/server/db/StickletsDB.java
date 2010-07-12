package com.risetek.keke.server.db;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class StickletsDB {

	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	static class flowSticks {
		@PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Long id;

		@Persistent
	    private StickletsDB sticklets;
		

		@Persistent
	    private int dimention;
		
		@Persistent
	    private String stickletType;

		@Persistent
	    private String Promotion;
	
		@Persistent
	    private String Params;
		
		public flowSticks(int dim, String type, String promotion, String params) {
			dimention = dim;
			stickletType = type;
			Promotion = promotion;
			Params = params;
		}
	}
	
	final static String[][] news = {
		{ "3", "Named", "epay.remote.help", "" },
		{ "0", "Stay", "基础知识", "<img v=\"20090218213217243\" />" },
		{ "0", "Stay", "怎么操作", "<img v=\"20090218213218178\" />" },
		{ "0", "Stay", "该问谁？", "<img v=\"20090218213215625\" />" }, };
	
	static {
//		deleteAll();
		initDB3();
	}
	
	private static void deleteAll() {
		List<StickletsDB> list = getSticklets();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistentAll(list);
	}
	private static void initDB1() {
		StickletsDB s = new StickletsDB("kkktest", 3);
		/*
		s.flows.add(new flowSticks(0, "Stay", "基础知识", "<img v=\"20090218213217243\" />"));
		s.flows.add(new flowSticks(0, "Stay", "怎么操作", "<img v=\"20090218213218178\" />"));
		s.flows.add(new flowSticks(0, "Stay", "该问谁？", "<img v=\"20090218213215625\" />"));
		*/
		s.flows.add(new flowSticks(0, "Stay", "abc", "<img v=\"20090218213217243\" />"));
		s.flows.add(new flowSticks(0, "Stay", "opr", "<img v=\"20090218213218178\" />"));
		s.flows.add(new flowSticks(0, "Stay", "who", "<img v=\"20090218213215625\" />"));
		s.save();
	}

	private static void initDB2() {
		StickletsDB s = new StickletsDB("ppptest", 3);
		s.flows.add(new flowSticks(0, "Stay", "基础知识", "<img v=\"20090218213217243\" />"));
		s.flows.add(new flowSticks(0, "Stay", "怎么操作", "<img v=\"20090218213218178\" />"));
		s.flows.add(new flowSticks(0, "Stay", "该问谁？", "<img v=\"20090218213215625\" />"));
		s.save();
	}
	
	private static void initDB3() {
		StickletsDB s = new StickletsDB("pp1test", 3);
		s.flows.add(new flowSticks(0, "Stay", "a", "<img v=\"20090218213217243\" />"));
		s.flows.add(new flowSticks(0, "Stay", "b", "<img v=\"20090218213218178\" />"));
		s.flows.add(new flowSticks(0, "Stay", "c", "<img v=\"20090218213215625\" />"));
		s.save();
	}
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private int dimention;
	
	@Persistent(mappedBy = "sticklets")
	private List<flowSticks> flows = new Vector<flowSticks>();
	
    public StickletsDB(String stickletsName, int dim) {
    	key = KeyFactory.createKey(StickletsDB.class.getSimpleName(), stickletsName);
    	dimention = dim;
    }
    
	public void save() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			// pm.deletePersistent(this);
			pm.makePersistent(this);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
	
	public static StickletsDB getSticklets(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	Key key = KeyFactory.createKey(StickletsDB.class.getSimpleName(), name);
    	return pm.getObjectById(StickletsDB.class, key);
	}

	public static List<StickletsDB> getSticklets() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<StickletsDB> list = (List<StickletsDB>)pm.newQuery(StickletsDB.class).execute();
		return list;
	}

	public static String getStickletsXML() {
		StringBuffer value = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?><ePay>");
		List<StickletsDB> list = getSticklets();
		for( int loop = 0; loop < list.size(); loop++ ) {
			StickletsDB sticklets = list.get(loop);
			value.append("<stick t=\"Named\" d=\"").append(sticklets.dimention)
				.append("\" p=\"").append(sticklets.key.getName()).append("\"><p><img v=\"Named\" /></p></stick>");
			
			List<flowSticks> flows = sticklets.flows;
			for( int f = 0; f < flows.size(); f++ ) {
				flowSticks fs = flows.get(f);
				
				value.append("<stick t=\""+fs.stickletType+"\" d=\"").append(fs.dimention)
				.append("\" p=\"").append(fs.Promotion).append("\">");
				if( fs.Params != null)
					value.append("<p>"+fs.Params+"</p>");
				value.append("</stick>");
			}
			
		}
		value.append("</ePay>");
		return value.toString();
	}
}