package com.yunpan.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.dao.FileDao;

/**
 * 
 * 文件下载
 *
 */
@SuppressWarnings("serial")
public class DownLoadFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		// 获取数据库操作
		FileDao fileDao = new FileDao();
		// 获取需要下载的文件名字
		String fileName = req.getParameter("fileName");
		// 获取用户的名字
		String username = (String) session.getAttribute("user");
		// 获取下载的文件路径
		String path = req.getParameter("filePath");
		// 数据库里存的文件的路径
		String filePath = "\\" + username + "\\" + path;
		try {
			//获取数据库中的文件扩展名
//			String fileType = fileDao.listFile(filePath)
			// 获取/upload/系统路径
			String systemPath = req.getServletContext().getRealPath("/upload/") + filePath + "\\"+fileName;
			File file = new File(systemPath);
			//判断文件是否存在,存在则下载，不存在则返回
			if(file.exists()){
				InputStream in = new FileInputStream(file);
				OutputStream output = resp.getOutputStream();
				int b;
				while((b=in.read())!=-1){
					output.write(b);
				}
				in.close();
				output.close();
			}else
			json.put("data", "文件不存在");
			
		} catch (Exception e) {
			json.put("data", "文件不存在");
		}finally {
			out.write(json.toString());
			out.close();
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
