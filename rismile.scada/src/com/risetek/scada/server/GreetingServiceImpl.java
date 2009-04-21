package com.risetek.scada.server;

import javax.jdo.PersistenceManager;

import com.risetek.scada.client.GreetingService;
import com.risetek.scada.db.PMF;
import com.risetek.scada.db.dataPoints;
import com.google.appengine.api.datastore.Blob;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) {
		String serverInfo = getServletContext().getServerInfo();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		dataPoints dp = new dataPoints(0, new Blob(input.getBytes()));
		
		try {
            pm.makePersistent(dp);
        } finally {
            pm.close();
        }
        
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
}
