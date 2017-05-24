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
import com.yunpan.bean.UserFile;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;

/**
 * 
 * @author lon分类查询
 *
 */
public class ListByKinds extends HttpServlet {
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
		UserFileDao userFileDao = new UserFileDao();
		UserDao userDao = new UserDao();
		//获取kinds
		String kinds = req.getParameter("kinds");
		try {
			//得到user对象
			User user = userDao.queryUser(username);
			//获取file对象
			Document doc = new Document();
			doc.setKinds(kinds);
			//封装
			UserFile userFile = new UserFile();
			userFile.setUserId(user);
			userFile.setFileId(doc);
			//数据库查询
			List<Document> list = userFileDao.listByKinds(userFile);
			json.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
