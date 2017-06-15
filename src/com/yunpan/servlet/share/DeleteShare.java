package com.yunpan.servlet.share;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.yunpan.dao.UserShareDao;

public class DeleteShare extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		// ·ÖÏí¼ÇÂ¼µÄid
		String id = req.getParameter("id");
		UserShareDao userShareDao = new UserShareDao();
		try {
			userShareDao.deleteShare(id);
			json.put("status", 1);
		} catch (Exception e) {
			json.put("status", "É¾³ýÊ§°Ü");
		}
		out.write(json.toString());
		out.close();
	}
}
