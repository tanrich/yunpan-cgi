package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.verifycode.login.AuthImage;
/**
 * 
 * @author lon
 *		验证码验证
 */
@SuppressWarnings("serial")
public class ValidateCheckCode extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		String checkCode = req.getParameter("checkCode");
		checkCode = checkCode.toLowerCase();
		HttpSession session = req.getSession();
		String verifyCode = (String) session.getAttribute("verifyCode");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		if(checkCode.equals(verifyCode)){
			json.put("status", 1);
		}else
			json.put("status", 0);
		out.write(json.toString());
		out.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
