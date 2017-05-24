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
 * ¼ì²âµÇÂ¼×´Ì¬
 * 
 * @author lon
 *
 */
@SuppressWarnings("serial")
public class ValidateLogin extends HttpServlet {
	private String username;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		username = (String) session.getAttribute("user");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		if (username != null || !username.isEmpty()) {
			json.put("status", 1);
			json.put("username", username);
		} else
			json.put("status", 0);
		out.write(json.toJSONString());
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
