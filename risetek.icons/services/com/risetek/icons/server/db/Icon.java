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
public class Icon {


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	

	@Persistent
	private Blob image;

	public Icon(String name, byte[] image) {
    	setKey(KeyFactory.createKey(Icon.class.getSimpleName(), name));
		this.setImage(image);
	}
	
	public void save() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(this);
		} finally {
			pm.close();
		}
	}

	public static List<Icon> getList() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Icon> list = (List<Icon>)pm.newQuery(Icon.class).execute();
			ArrayList<Icon> alist = new ArrayList<Icon>();
			Iterator<Icon> i = list.iterator();
			while (i.hasNext()) {
				alist.add(i.next());
			}
			return alist;
		} finally {
			pm.close();
		}
	}
	
	public static Icon getIcon(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	Key key = KeyFactory.createKey(Icon.class.getSimpleName(), name);
    	return pm.getObjectById(Icon.class, key);
	}

	public void setImage(byte[] image) {
		this.image = new Blob(image);
	}

	public byte[] getImage() {
		return image.getBytes();
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}
}
