package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.verifycode.login.VerifyCodeUtils;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserShare;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserShareDao;

/**
 * 
 * @author lon文件分享
 *
 */
@SuppressWarnings("serial")
public class InsertShare extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		// 获取文件的id,有效期
		String id = req.getParameter("id");
		String status = req.getParameter("status");
		// 获取数据库连接
		FileDao fileDao = new FileDao();
		UserShareDao userShareDao = new UserShareDao();
		UserDao userDao = new UserDao();
		UserShare userShare = new UserShare();
		// 生成分享的url
		String url = "http://localhost:8080/share?url=" + username + VerifyCodeUtils.generateVerifyCode(6);
		// 生成提取码
		String code = VerifyCodeUtils.generateVerifyCode(4);
		//生成时间
		Date date = new Date();
		SimpleDateFormat formate;
		formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = formate.format(date);
		try {
			// 获取文件和用户对象
			Document doc = fileDao.selectFile(id);
			User user = userDao.queryUser(username);
			// 添加到用户记录
			userShare.setFileId(doc);
			userShare.setUserId(user);
			userShare.setCode(code);
			userShare.setUrl(url);
			userShare.setDateTime(time);
			userShare.setStatus(status);
			System.out.println("生成的链接"+url);
			userShareDao.insertShare(userShare);
			json.put("data", userShare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}
}
