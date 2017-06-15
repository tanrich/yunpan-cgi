package com.yunpan.servlet.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.Report;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.ReportDao;

/**
 * 
 * @author lon举报
 *
 */
@SuppressWarnings("serial")
public class ReportShare extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		String reason = req.getParameter("reason");
		String id = req.getParameter("id");
		//数据库
		ReportDao reportDao = new ReportDao();
		FileDao fileDao = new FileDao();
		Document doc = fileDao.selectFile(id);
		// 举报时间
		Date date = new Date();
		SimpleDateFormat formate;
		formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = formate.format(date);
		Report report = new Report();
		report.setFileId(doc);
		report.setDateTime(time);
		report.setReason(reason);
		try {
			reportDao.insertReport(report);
			json.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("status", 0);
		}
		out.write(json.toString());
		out.close();
	}
}
