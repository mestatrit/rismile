package com.risetek.keke.server.db;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class StickletsDB {

	final static String[][] news = {
		{ "3", "Named", "epay.remote.help", "" },
		{ "0", "Stay", "基础知识", "<img v=\"20090218213217243\" />" },
		{ "0", "Stay", "怎么操作", "<img v=\"20090218213218178\" />" },
		{ "0", "Stay", "该问谁？", "<img v=\"20090218213215625\" />" }, };
	
	static {
		new StickletsDB("aaatest", 3).save();
	}
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private int dimention;
	
    public StickletsDB(String stickletsName, int dim) {
    	key = KeyFactory.createKey(StickletsDB.class.getSimpleName(), stickletsName);
    	dimention = dim;
    }
    
	public void save() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(this);
		} finally {
			pm.close();
		}
	}
	
	public static StickletsDB getSticklets(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + StickletsDB.class.getName() + " where stickletsName == " + name;
		List<StickletsDB> list = (List<StickletsDB>)pm.newQuery(query).execute();
		if( list.size() == 0)
			return null;
		return list.get(0);
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
				.append("\" p=\"").append(sticklets.key.getName()).append("\"><p><img v=\"Named\" /></p></sticklet>");
		}
		value.append("</ePay>");
		return value.toString();
	}
}