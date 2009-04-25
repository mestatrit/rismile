package com.risetek.scada.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Blob;
import com.google.gwt.core.client.GWT;
import com.risetek.scada.db.dao.*;
import com.risetek.scada.vo.DataPointVO;

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
			
			DataPointVO dp = new DataPointVO(0, new Blob("blob".getBytes()));
			(new DataPointDao()).saveDataPoint(dp);

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
