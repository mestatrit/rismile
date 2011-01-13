package com.risetek.rismile.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeartbeatServiceImpl extends HttpServlet {
	private static final long serialVersionUID = -2228785224639932740L;

	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?><Risetek><OK>" +
		"<nopassword>未配置密码！</nopassword>" +
		"<run_time>0天0小时24分钟</run_time>" +
		"</OK></Risetek>");
	    out.flush();
	}
}
