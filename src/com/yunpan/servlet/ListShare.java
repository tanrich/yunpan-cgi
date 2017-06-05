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
import com.yunpan.dao.UserShareDao;
/**
 * 
 * 
 * @author lon获取用户所有分享结果
 *
 */
public class ListShare extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		UserDao userDao = new UserDao();
		UserShareDao userShareDAo = new UserShareDao();
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		try {
			User user = userDao.queryUser(username);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("data", 0);
	}
}
