package com.risetek.keke.server.db.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.risetek.keke.server.db.PMF;

public class UserFacade {
	public static int userNotFound = 0;
	public static int wrongPassword = -1;
	public static int success = 1;
	
	public static void addUser(User user) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key = KeyFactory.createKey(User.class.getSimpleName(), user.getUsername());
		user.setKey(key);
		try {
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}
	public static void addUser(String username, String password, Group group) {
		byte[] byteArray = password.getBytes();
		password = Base64.encode(byteArray);
		User user = new User(username, password, group);
		addUser(user);
	}
	public static void addUser(String username, String password) {
		byte[] byteArray = password.getBytes();
		password = Base64.encode(byteArray);
		User user = new User(username, password, Group.user);
		addUser(user);
	}
	
	public static void delUser(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = pm.getObjectById(User.class, username);
		pm.deletePersistent(user);
	}
	
	public static User getUserByUsername(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = null;
		try {
			user = pm.getObjectById(User.class, username);
		} catch (JDOObjectNotFoundException ex) {
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> getAllUsers() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<User> list = new ArrayList();
		try {
			Extent<User> e = pm.getExtent(User.class, true);
			Iterator i = e.iterator();
			while (i.hasNext()) {
				list.add((User) i.next());
			}
		} finally {
			pm.close();
		}
		return list;
	}
	
	public static int validate(String username, String password) {
		if (getAllUsers().size() == 0) {
			addUser(username, password, Group.admin);
			return success;
		} else {
			User user = getUserByUsername(username);
			if (user == null) {
				return userNotFound;
			} else {
				byte[] byteArray = password.getBytes();
				if (Base64.encode(byteArray).equals(user.getPassword())) {
					return success;
				} else {
					return wrongPassword;
				}
			}
		}
	}
	
	public static boolean isAdmin(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = pm.getObjectById(User.class, username);
		if (user.getGroup() == Group.admin) {
			return true;
		} else {
			return false;
		}
	}
}
