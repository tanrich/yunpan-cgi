package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;

/**
 * Ä£ºý²éÑ¯
 *
 */
@SuppressWarnings("serial")
public class Query extends HttpServlet {

	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		String fileName = req.getParameter("fileName");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		try {
			User user = userDao.queryUser(username);
			List<Document> list = fileDao.likeQuery(user.getId(), fileName);
			json.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}
}
