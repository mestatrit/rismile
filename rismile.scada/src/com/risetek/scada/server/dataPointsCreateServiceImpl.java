package com.risetek.scada.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risetek.scada.db.PMF;
import com.risetek.scada.db.dataPoints;
import com.google.appengine.api.datastore.Blob;
import com.google.gwt.core.client.GWT;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class dataPointsCreateServiceImpl extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String blob = req.getParameter("blob");

		if (null != blob) {

			GWT.log(blob, null);
			
			PersistenceManager pm = PMF.get().getPersistenceManager();

			dataPoints dp = new dataPoints(0, new Blob("blob".getBytes()));

			try {
				pm.makePersistent(dp);
			} finally {
				pm.close();
			}

		}
		else
		{
			GWT.log("miss blob", null);
		}

		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?><Risetek>");
		out.println("OK");
		out.println("</Risetek>");
		out.flush();
	}

}
