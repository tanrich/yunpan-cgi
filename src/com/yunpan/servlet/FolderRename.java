package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.dao.FileDao;

@SuppressWarnings("serial")
public class FolderRename extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		// 获取数据库
		FileDao fileDao = new FileDao();
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 获取要重命名的文件名，和路径
		String id = req.getParameter("id");
		String newFolderName = req.getParameter("newFolderName");
		
		//测试
		String filePath = null;
		String oldFolderName = null;
		String systemPath = null;
		String oldFolderpath = null;
		String newFolderpath = null;
		try {
			Document doc = fileDao.selectFile(id);
			oldFolderName = doc.getFileName();
			filePath = doc.getFilePath();
			oldFolderpath = filePath+"/"+oldFolderName;
			newFolderpath = filePath+"/"+newFolderName;
			//文件夹所在路径
			systemPath = req.getServletContext().getRealPath("/upload")+filePath;
			File oldFolder = new File(systemPath+"/"+oldFolderName);
			File newFolder = new File(systemPath+"/"+newFolderName);
			if(oldFolder.exists()){
				if(!newFolder.exists()){
					//磁盘修改
					oldFolder.renameTo(newFolder);
					//数据库修改
					fileDao.fileRename(newFolderName, id);
					//在此文件夹下所有文件在数据库中的路径进行修改
					fileDao.folderRename(oldFolderpath, newFolderpath);
					json.put("status", 1);
				}else
					json.put("status", 0);
			}else
				json.put("status", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
