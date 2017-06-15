package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
/**
 * ÍË³öµÇÂ¼
 */
public class LogOut extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		try {
			session.setAttribute("user",null);
			json.put("status", 1);
		} catch (Exception e) {
			json.put("status", 0);
		}	
		out.write(json.toJSONString());
		out.flush();
		out.close();
	}

	
}
