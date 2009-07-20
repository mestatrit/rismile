package com.risetek.scada.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.risetek.scada.db.dao.DataPointDao;
import com.risetek.scada.vo.DataPointVO;

@SuppressWarnings("serial")
public class licenseQueryServiceImpl extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version='1.0' encoding='utf-8'?><Risetek>");
		List<DataPointVO> dps = (new DataPointDao()).getDataPoints();

		if (dps.iterator().hasNext()) {
			for (DataPointVO e : dps) {
				out.println("<SET id='" + e.getId() + "'>" + (new String(e.getData().getBytes()))
						+ "</SET>");
			}
		}

		out.println("</Risetek>");
		out.flush();
	}

}