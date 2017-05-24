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
import com.yunpan.dao.UserRoomDao;

public class ValidateRoomSpace extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		PrintWriter out = resp.getWriter();
		//获取数据库
		UserRoomDao userRoomDao = new UserRoomDao();
		UserDao userDao = new UserDao();
		try {
			User user = userDao.queryUser(username);
			float size =userRoomDao.selectRoom(String.valueOf(user.getId()));
			json.put("allSpace", 500);
			json.put("leftSpace", size);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 0);
		}
		out.write(json.toString());
		out.close();
		
	}
}
