package com.yunpan.servlet.share;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.UserShare;
import com.yunpan.dao.UserShareDao;

@SuppressWarnings("serial")
public class Share extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		String url = req.getParameter("url");
		String code = req.getParameter("code");
		// 获取数据库
		UserShareDao userShareDao = new UserShareDao();
		try {
			if (url != null) {
				UserShare userShare = new UserShare();
				userShare = userShareDao.selectShare(url);
				if (userShare != null && url.equals(userShare.getUrl())) {
					if (code != null && code.equals(userShare.getCode())) {
						if (userShare.getStatus().equals("失效")) {
							json.put("status", 0);
						} else {
							json.put("status", 1);
							json.put("data", userShare);
							System.out.println(userShare.getDateTime());
						}
					} else {
						json.put("status", 0);
					}
				} else {
					json.put("status", 0);
				}
			}
		} catch (Exception e) {
			json.put("status", 0);
		}
		out.write(json.toString());
		out.close();
	}
}
