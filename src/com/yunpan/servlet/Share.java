package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.UserShare;
import com.yunpan.dao.UserShareDao;

public class Share extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		String url = "http://localhost:8080/share?url="+req.getParameter("url");
		//获取数据库
		UserShareDao userShareDao = new UserShareDao();
		try {
			UserShare userShare = new UserShare();
			userShare = userShareDao.selectShare(url);
			json.put("data", userShare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toJSONString());
		out.close();
	}
}
