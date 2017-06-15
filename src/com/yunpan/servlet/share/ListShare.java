package com.yunpan.servlet.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.User;
import com.yunpan.bean.UserShare;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserShareDao;

/**
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
		UserShareDao userShareDao = new UserShareDao();
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			User user = userDao.queryUser(username);
			List<UserShare> list = userShareDao.selectAllShare(user);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setUrl(list.get(i).getUrl());
				jsonArray.add(list.get(i));
			}
			json.put("data", jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("data", 0);
		}
		out.write(json.toJSONString());
		out.close();
	}
}
