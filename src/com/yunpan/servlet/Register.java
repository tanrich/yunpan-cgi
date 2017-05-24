package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.User;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserRoomDao;

@SuppressWarnings("serial")
public class Register extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		// 得到用户名，密码，再次密码
		String r_username = req.getParameter("r_username");
		String r_password = req.getParameter("r_password");
		String r_confirm_password = req.getParameter("r_confirm_password");
	
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		UserDao userDao = new UserDao();
		UserRoomDao userRoomDao = new UserRoomDao();
		User user = new User();
		int x = 0;
		try {
			if (r_confirm_password.equals(r_password)) {

				// 将新注册的用户信息新增到用户表之中
				user.setUsername(r_username);
				user.setPassword(r_password);
				String name = "E:\\upload\\"+r_username;
				File file = new File(name);
				file.mkdir();
				x = userDao.insertUser(user);
				if (x != 0) {
					x = userRoomDao.insertRoom(String.valueOf(x));
					if(x==1){
						json.put("status", 1);
					}	
				} else {
					json.put("status", 0);
				}

			} else {
				json.put("status", 0);
			}

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
