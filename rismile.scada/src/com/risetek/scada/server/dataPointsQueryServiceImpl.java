package com.risetek.scada.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risetek.scada.db.PMF;
import com.risetek.scada.db.dataPoints;

@SuppressWarnings("serial")
public class dataPointsQueryServiceImpl extends HttpServlet {
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?><Risetek>");
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(dataPoints.class);
		query.setRange(0, 10);
	    query.setOrdering("id desc");

	    try {
	        List<dataPoints> results = (List<dataPoints>)query.execute();
	        if (results.iterator().hasNext()) {
	            for (dataPoints e : results) {
	        		out.println("<SET id='" + e.getId() + "'>" + e.getBlob() + "</SET>");
	            }
	        }
	    } finally {
	        query.closeAll();
	    }		
		
		out.println("</Risetek>");
	    out.flush();
	}

}