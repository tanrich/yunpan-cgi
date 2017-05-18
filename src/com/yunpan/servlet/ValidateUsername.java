package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.User;
import com.yunpan.dao.UserDao;
/**
 * 
 * @author lon
 *		用户名是否注册验证
 */
@SuppressWarnings("serial")
public class ValidateUsername extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		try {
			String username = req.getParameter("r_username");
			System.out.println(username);
			UserDao userDao = new UserDao();
			User user = userDao.queryUser(username);
			System.out.println(user.getUsername());
			if (user.getUsername().equals(username)) {
				json.put("status", 0);
			} else
				json.put("status", 1);
		} catch (Exception e) {
			json.put("status", 1);
		}finally {
			out.write(json.toString());
			out.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
