package com.yunpan.servlet.share;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yunpan.bean.Document;
import com.yunpan.dao.UserShareDao;

public class DownloadShare extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		// 获取数据库操作
		UserShareDao userShareDao = new UserShareDao();
		// 获取下载的分享id
		String id = req.getParameter("id");
		Document doc = new Document();
		String userAgent = req.getHeader("User-Agent");
		try {
			userShareDao.updateDLtimes(id);
			doc = userShareDao.selectShareById(id).getFileId();
			// 获取数据库中的文件扩展名和文件路径
			String fileType = doc.getFileType();
			String filePath = doc.getFilePath();
			String fileName = doc.getFileName();
			// 获取/upload/系统路径
			String ss = fileName + "." + fileType;
			String systemPath = "E:\\upload" + filePath + "/" + ss;
			File file = new File(systemPath);
			InputStream in = new FileInputStream(file);
			OutputStream output = resp.getOutputStream();
			byte[] bytes = userAgent.contains("MSIE") ? ss.getBytes() : fileName.getBytes("UTF-8");
			ss = new String(bytes, "ISO-8859-1");
			fileName = new String(bytes, "ISO-8859-1");
			// 判断文件是否存在,存在则下载，不存在则返回
			if (file.exists()) {
				// 设置文件MIME类型
				resp.setContentType("multipart/form-data");
				// 设置Content-Disposition
				resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", ss));

				byte[] b = new byte[1024];
				while (in.read(b) != -1) {
					output.write(b);
				}

			}
			in.close();
			output.close();

		} catch (Exception e) {

		}
	}
}
