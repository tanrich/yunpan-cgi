package com.yunpan.servlet.share;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserFile;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;
import com.yunpan.dao.UserRoomDao;
import com.yunpan.dao.UserShareDao;

/**
 * 保存次数加1
 */
public class SaveShare extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// 获取下载的分享id
		String id = req.getParameter("id");
		//数据库
		UserShareDao userShareDao = new UserShareDao();
		UserFileDao userFileDao = new UserFileDao();
		UserDao userDao = new UserDao();
		FileDao fileDao = new FileDao();
		UserRoomDao userRoomDao = new UserRoomDao();
		try {
			//获取用户对象
			User user = userDao.queryUser(username);
			//分享记录保存次数加1
			userShareDao.updateStimes(id);
			//获取文件对象
			Document doc = userShareDao.selectShareById(id).getFileId();
			float size = userRoomDao.selectRoom(String.valueOf(user.getId()))-doc.getSize();
			UserFile userFile = new UserFile();
			userFile.setFileId(doc);
			userFile.setUserId(user);
			//文件夹所在路径
			String systemPath = "E:\\upload" +doc.getFilePath()+"\\"+doc.getFileName()+"."+doc.getFileType();
			String savePath = "E:\\upload\\" +username+"\\"+doc.getFileName()+"."+doc.getFileType();
			File ofile = new File(systemPath);
			File nfile = new File(savePath);
			if(ofile.exists()){
				InputStream in = new FileInputStream(ofile);
				FileOutputStream fs =new FileOutputStream(nfile);
				byte[]buffer = new byte[1024];
				while(in.read(buffer)!=-1){
					fs.write(buffer);
				}
				in.close();
				fs.close();
				doc.setFilePath("\\"+username);
				//添加记录到用户文件关系
				userFileDao.insertUserFile(userFile);
				//添加文件记录到文件表
				fileDao.uploadFile(doc);
				//用户空间更新
				userRoomDao.updateRoom(user, size);
				json.put("status", 1);
			}else
				json.put("status", "error");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}
}
