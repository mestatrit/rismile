// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.

package nl.justobjects.pushlet.core;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nl.justobjects.pushlet.util.Log;
import nl.justobjects.pushlet.util.PushletException;
import nl.justobjects.pushlet.util.Rand;
import nl.justobjects.pushlet.util.Sys;

/**
 * Manages lifecycle of Sessions.
 *
 * @author Just van den Broecke - Just Objects &copy;
 * @version $Id: SessionManager.java,v 1.11 2007/11/23 14:33:07 justb Exp $
 */
public class SessionManager implements ConfigDefs {

	/**
	 * Singleton pattern:  single instance.
	 */
	private static SessionManager instance;

	static {
		// Singleton + factory pattern:  create single instance
		// from configured class name
		try {
			instance = (SessionManager) Config.getClass(SESSION_MANAGER_CLASS, "nl.justobjects.pushlet.core.SessionManager").newInstance();
			Log.info("SessionManager created className=" + instance.getClass());
		} catch (Throwable t) {
			Log.fatal("Cannot instantiate SessionManager from config", t);
		}
	}

	/**
	 * Timer to schedule session leasing TimerTasks.
	 */
	private Timer timer;
	private final long TIMER_INTERVAL_MILLIS = 60000;

	/**
	 * Map of active sessions, keyed by their id.
	 */
	private Map<String, Session> sessions = Collections.synchronizedMap(new HashMap(13));

	/**
	 * Shadow cache of active Sessions.
	 */
	private Session[] sessionCache = new Session[0];

	/**
	 * Flag indicating subscriptions have changed.
	 */
	private volatile boolean sessionCacheDirty = false;

	/**
	 * Singleton pattern: protected constructor needed for derived classes.
	 */
	protected SessionManager() {
	}

	/**
	 * Create new Session (but add later).
	 */
	public Session createSession(Event anEvent) throws PushletException {
		// Trivial
		return Session.create(createSessionId());
	}

	/**
	 * Create unique Session id.
	 */
	public String createSessionId() {
		// Other cases use random name

		// Create a unique session id
		// In 99.9999 % of the cases this should be generated at once
		String id = null;
		while (true) {
			id = Rand.randomName(Config.getIntProperty(SESSION_ID_SIZE));
			if (!hasSession(id)) {
				// Created unique session id
				break;
			}
		}
		return id;
	}

	/**
	 * Singleton pattern: get single instance.
	 */
	public static SessionManager getInstance() {
		return instance;
	}

	/**
	 * Get number of listening Sessions.
	 */
	public Session getSession(String anId) {
		return (Session) sessions.get(anId);
	}

	/**
	 * Get copy of listening Sessions.
	 */
	public Session[] getSessions() {
		return (Session[]) sessions.values().toArray(new Session[0]);
	}

	/**
	 * Get number of listening Sessions.
	 */
	public int getSessionCount() {
		return sessions.size();
	}

	/**
	 * Get status info.
	 */
	public String getStatus() {
		Session[] sessions = getSessions();
		String statusInfo = "SessionMgr: " + sessions.length + " sessions \\n";
		for (int i = 0; i < sessions.length; i++) {
			statusInfo = statusInfo + sessions[i] + "\\n";
		}
		return statusInfo;
	}

	/**
	 * Is Session present?.
	 */
	public boolean hasSession(String anId) {
		return sessions.containsKey(anId);
	}

	/**
	 * Add session.
	 */
	public void addSession(Session session) {
		// log(session.getId() + " at " + session.getAddress() + " adding ");
		sessions.put(session.getId(), session);
		sessionCacheDirty = true;
		info(session.getId() + " at " + session.getAddress() + " added ");
	}

	/**
	 * Register session for removal.
	 */
	public Session removeSession(Session aSession) {
		Session session = (Session) sessions.remove(aSession.getId());
		if (session != null) {
			sessionCacheDirty = true;
			info(session.getId() + " at " + session.getAddress() + " removed ");
		}
		return session;
	}

	public Session[] getSnapshot() {
		// If no session change return immediately.
		if (!sessionCacheDirty) {
			return sessionCache;
		}

		// Session cache is dirty: recreate
		synchronized (sessionCache) {
			// ASSERT: cache is dirty, need to update cache

			// Session cache array may be reused: make all entries null.
			for (int i = 0; i < sessionCache.length; i++) {
				sessionCache[i] = null;
			}

			// Copy all sessions into cache
			// toArray() expands cache size if required
			sessionCache = (Session[]) sessions.values().toArray(sessionCache);

			// Mark session cache actualized
			sessionCacheDirty = false;

			return sessionCache;
		}
	}


	/**
	 * Util: stdout printing.
	 */
	public void start() {
		if (timer != null) {
			stop();
		}
		timer = new Timer(false);
		timer.schedule(new AgingTimerTask(), TIMER_INTERVAL_MILLIS, TIMER_INTERVAL_MILLIS);
		info("started; interval=" + TIMER_INTERVAL_MILLIS + "ms");
	}

	/**
	 * Util: stdout printing.
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		sessions.clear();
		sessionCache = new Session[0];
		info("stopped");
	}

	/**
	 * Util: stdout printing.
	 */
	private void info(String s) {
		Log.info("SessionManager: " + new Date() + " " + s);
	}

	/**
	 * Util: stdout printing.
	 */
	private void warn(String s) {
		Log.warn("SessionManager: " + s);
	}

	/**
	 * Util: stdout printing.
	 */
	private void debug(String s) {
		Log.debug("SessionManager: " + s);
	}

	/**
	 * Manages session timeouts.
	 */
	private class AgingTimerTask extends TimerTask {
		private long lastRun = Sys.now();

		public void run() {
			long now = Sys.now();
			long delta = now - lastRun;
			lastRun = now;
			// info("tick " + delta);

			Session[] sessions = getSnapshot();
			Session nextSession = null;
			for (int i = 0; i < sessions.length; i++) {
				nextSession = sessions[i];

				// Null denotes end of cache array
				if (nextSession == null) {
					break;
				}

				try {
					// Age the lease
					nextSession.age(delta);

					// Stop session if lease expired
					if (nextSession.isExpired()) {
						info("Session expired: " + nextSession);
						nextSession.stop();
					}
				} catch (Throwable t) {
					warn("Error in timer task : " + t);
				}
			}
		}
	}
}
