package com.risetek.icons.server.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class TreedIcons {


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
/*
	@Persistent
    private Key Parentkey;
*/
	@Persistent
	private Blob image = null;

	public TreedIcons(String name, byte[] image) {
    	setKey(KeyFactory.createKey(TreedIcons.class.getSimpleName(), name));
		this.setImage(image);
	}

	public TreedIcons(String name) {
    	setKey(KeyFactory.createKey(TreedIcons.class.getSimpleName(), name));
	}
	
	public void save() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(this);
		} finally {
			pm.close();
		}
	}

	public static List<TreedIcons> getList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<TreedIcons> list = (List<TreedIcons>)pm.newQuery(TreedIcons.class).execute();
			ArrayList<TreedIcons> alist = new ArrayList<TreedIcons>();
			Iterator<TreedIcons> i = list.iterator();
			while (i.hasNext()) {
				alist.add(i.next());
			}
			return alist;
		} finally {
			pm.close();
		}
	}
	
	public static TreedIcons getIcon(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	Key key = KeyFactory.createKey(TreedIcons.class.getSimpleName(), name);
    	TreedIcons icon = null;
		try {
			icon = pm.getObjectById(TreedIcons.class, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if( icon == null ) {
    		icon = new TreedIcons(name);
    		icon.save();
    	}
    	return icon;
	}

	public void setImage(byte[] image) {
		this.image = new Blob(image);
	}

	public byte[] getImage() {
		if( image == null )
			return null;
		return image.getBytes();
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
}
