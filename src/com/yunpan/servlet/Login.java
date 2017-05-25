package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.User;
import com.yunpan.dao.UserDao;

/**
 * 
 * @author lon 验证登陆
 */
@SuppressWarnings("serial")
public class Login extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		HttpSession session = req.getSession();
		// 得到用户名，密码
		String username = req.getParameter("l_username");
		String password = req.getParameter("l_password");
		
		// 测试
		UserDao userDao = new UserDao();
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		try {
			User user = userDao.queryUser(username);

			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				session.setAttribute("user", username);
				System.out.println("success");
				json.put("status", 1);
			} else
				json.put("status", 0);
		} catch (Exception e) {
			json.put("status", 0);
		}finally {
			out.write(json.toString());
			out.flush();
			out.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
