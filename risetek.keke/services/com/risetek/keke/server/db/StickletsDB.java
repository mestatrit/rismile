package com.risetek.keke.server.db;

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
import com.risetek.keke.client.sticklet.Util;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class StickletsDB {

	@PersistenceCapable(identityType = IdentityType.APPLICATION)
	static class flowSticks {
		@PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//	    @Unique;
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
	
	static {
//		deleteAll();
//		initDB2();
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
		StickletsDB s = new StickletsDB("epay.remote.help", 3);
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
		List<StickletsDB> list = getSticklets();
		String result = new String();
		for( int loop = 0; loop < list.size(); loop++ ) {
			StickletsDB sticklets = list.get(loop);
			List<flowSticks> flows = sticklets.flows;
			int arrayLength = flows.size()+1;
			String[][] sticklet = new String[arrayLength][4];
			
			sticklet[0][0] = sticklets.dimention+"";
			sticklet[0][1] = "Named";
			sticklet[0][2] = sticklets.key.getName();
			sticklet[0][3] = "<p><img v=\"Named\" /></p>";
			
			for( int f = 0; f < flows.size(); f++ ){
				flowSticks fs = flows.get(f);
				sticklet[f+1][0] = fs.dimention+"";
				sticklet[f+1][1] = fs.stickletType;
				sticklet[f+1][2] = fs.Promotion;
				sticklet[f+1][3] = fs.Params;
			}
			
			result = result.concat(Util.stickletToXML(sticklet));
			
		}
		return result;
	}
	

	public static String getStickletsXML(String name) {
		StickletsDB sticklets = getSticklets(name);
		List<flowSticks> flows = sticklets.flows;
		int arrayLength = flows.size() + 1;
		String[][] sticklet = new String[arrayLength][4];

		sticklet[0][0] = sticklets.dimention + "";
		sticklet[0][1] = "Named";
		sticklet[0][2] = sticklets.key.getName();
		sticklet[0][3] = "<p><img v=\"Named\" /></p>";

		for (int f = 0; f < flows.size(); f++) {
			flowSticks fs = flows.get(f);
			sticklet[f + 1][0] = fs.dimention + "";
			sticklet[f + 1][1] = fs.stickletType;
			sticklet[f + 1][2] = fs.Promotion;
			sticklet[f + 1][3] = fs.Params;
		}

		return Util.stickletToXML(sticklet);
	}

}













