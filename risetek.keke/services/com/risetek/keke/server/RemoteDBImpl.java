package com.risetek.keke.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risetek.keke.server.db.StickletsDB;

public class RemoteDBImpl extends HttpServlet {

	private static final long serialVersionUID = -1400711288234296672L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String xml = StickletsDB.getStickletsXML();
		PrintWriter out;
		try {
			out = resp.getWriter();
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
