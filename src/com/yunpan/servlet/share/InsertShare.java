package com.yunpan.servlet.share;

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
 * @author lon�ļ�����
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
		// ��ȡ�ļ���id,��Ч��
		String id = req.getParameter("id");
		String status = req.getParameter("status");
		// ��ȡ���ݿ�����
		FileDao fileDao = new FileDao();
		UserShareDao userShareDao = new UserShareDao();
		UserDao userDao = new UserDao();
		UserShare userShare = new UserShare();
		// ���ɷ�����url
		String string =VerifyCodeUtils.generateVerifyCode(9);
		String url = username + string.substring(0,4);
		// ������ȡ��
		String code = string.substring(4).toLowerCase();
		//����ʱ��
		Date date = new Date();
		SimpleDateFormat formate;
		formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = formate.format(date);
		try {
			// ��ȡ�ļ����û�����
			Document doc = fileDao.selectFile(id);
			User user = userDao.queryUser(username);
			// ���ӵ��û���¼
			userShare.setFileId(doc);
			userShare.setUserId(user);
			userShare.setCode(code);
			userShare.setUrl(url);
			userShare.setDateTime(time);
			userShare.setStatus(status);
			userShareDao.insertShare(userShare);
			json.put("status", 1);
			json.put("data", userShare);
		} catch (Exception e) {
			json.put("status", 0);
		}
		out.write(json.toString());
		out.close();
	}
}