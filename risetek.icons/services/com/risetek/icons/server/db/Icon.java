package com.risetek.icons.server.db;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Icon implements IsSerializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@SuppressWarnings({ "unused"})
    private Key key;
	
	@SuppressWarnings("unused")
	@Persistent
	private String name;
	
	@Persistent
	private byte[] image;

	public Icon(String name, byte[] image) {
		this.name = name;
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
	
	
	public static Icon getIcon(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	Key key = KeyFactory.createKey(Icon.class.getSimpleName(), name);
    	return pm.getObjectById(Icon.class, key);
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}
}
