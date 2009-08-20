package com.risetek.scada.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;

@SuppressWarnings("serial")
public class gpsQueryServiceImpl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		GWT.log("get gps datas", null);
		GPSDatas datas = GPSDatas.getDatas();
		
		synchronized(datas)
		{
			try {
				datas.notify();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				datas.wait();
				resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.write(datas.Serial());
				out.flush();
				out.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}