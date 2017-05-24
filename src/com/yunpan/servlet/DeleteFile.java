package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserRoom;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserRoomDao;
import com.yunpan.service.FileService;

/**
 * 
 * @author lon删除文件
 *
 */
public class DeleteFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = req.getSession();
		// 获取数据库
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		UserRoomDao userRoomDao = new UserRoomDao();
		FileService fileService = new FileService();
		// 获取要删除的文件id
		String id = req.getParameter("id");
		String username = (String) session.getAttribute("user");
		String systemPath = null;
		try {
			Document doc = fileDao.selectFile(id);
			User user = userDao.queryUser(username);
			String fileName = doc.getFileName();
			String filePath = doc.getFilePath();
			String fileType = doc.getFileType();
			float fileSize = 0;
			float userSize = 0;
			// 判断是否删除文件夹
			systemPath = "E:\\upload"  + filePath;
			if (fileType.equals("folder")) {
				File folder = new File(systemPath + "/" + fileName);
				System.out.println(folder.getPath());
				if (folder.exists()) {
					File[] fs = folder.listFiles();
					if(fs!=null&&fs.length>0){
						fileService.deleteFile(folder);
						filePath = filePath + "/" + fileName;
						// 获取删除文件的大小
						fileSize = fileDao.countSize(filePath);
						// 获取用户剩余空间大小
						userSize = userRoomDao.selectRoom(String.valueOf(user.getId()));
						// 先删文件夹
						fileDao.deleteOne(id);
						// 再删除这个文件夹下所有文件
						
						fileDao.deleteFolder(filePath);
						userRoomDao.updateRoom(user, userSize+fileSize);
						json.put("status", 1);
					}else{
						fileService.deleteFile(folder);
						fileDao.deleteOne(id);
						json.put("status", 1);
					}
				} else
					json.put("status", 0);
			} else {
				// 进行磁盘操作
				File file = new File(systemPath + "/" + fileName + "." + fileType);
				if (file.exists()) {
					file.delete();
					// 进行数据库
					Document document = fileDao.selectFile(id);
					// 获取删除文件的大小
					fileSize = document.getSize();
					// 获取用户剩余空间大小
					userSize = userRoomDao.selectRoom(String.valueOf(user.getId()));
					fileDao.deleteOne(id);
					userRoomDao.updateRoom(user, userSize + fileSize);
					json.put("status", 1);
				} else
					json.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
