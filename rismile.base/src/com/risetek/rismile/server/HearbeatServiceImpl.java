package com.risetek.rismile.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HearbeatServiceImpl extends HttpServlet {
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?><Risetek><OK>" +
		"<nopassword>未配置密码！</nopassword></OK></Risetek>");
	    out.flush();
	}

}
