package com.yunpan.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserFile;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;
import com.yunpan.service.FileClassifyService;

/**
 * 
 * @author lon 上传文件
 *
 */
@SuppressWarnings("serial")
public class UploadFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		UserFileDao userFileDao = new UserFileDao();
		FileClassifyService fileCS = new FileClassifyService();
		JSONObject json = new JSONObject();
		PrintWriter output = resp.getWriter();
		HttpSession session = req.getSession();
		// 获取用户名，文件路径（传到哪个文件夹下）

		String path = null;
		path = req.getParameter("path");
		String username = (String) session.getAttribute("user");
		// 测试数据

		// 得到上传文件夹的路径
		// /upload/tom
		User user = userDao.queryUser(username);
		Document doc = new Document();
		UserFile userFile = new UserFile();
		int fileId = 0;
		String fileType = null;
		String kinds = null;
		String fileName = null;
		//String filePath = "/" + username + "/" + path;
		String filePath = "/" + username;
		System.out.println(filePath);
		String systemPath = null;
		System.out.println(path);
		if (path == null || path.equals("/")) {
			System.out.println("11111");
			systemPath = req.getServletContext().getRealPath("/upload/") + username;
		} else{
			System.out.println("2222");
			filePath = "/" + username + "/" + path;
			systemPath = req.getServletContext().getRealPath("/upload/") + username + "/" + path;
			
		}
		System.out.println(systemPath);
		File file = new File(systemPath);
		// 判断上传文件夹是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(systemPath + "目录不存在，需要创建");
			file.mkdir();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");

		if (!ServletFileUpload.isMultipartContent(req)) {
			System.out.println("isMultipartContent");
			return;
		}
		try {
			List<FileItem> list = upload.parseRequest(req);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				} else {// 如果fileitem中封装的是上传文件
					// 得到上传的文件名称，
					String filename = item.getName();
					// 得到文件的名字 ，类型 ，
					System.out.println("91:" + filename.length());
					System.out.println("92:" + filename.substring(filename.lastIndexOf(".")));
					fileName = filename.substring(0, filename.lastIndexOf("."));
					fileType = filename.substring(filename.lastIndexOf(".") + 1);
					kinds = fileCS.fileclassify(fileType);
					System.out.println(fileName + "    " + fileType);
					// 成功上传则 保存文件信息到数据库
					doc.setFileName(fileName);
					doc.setFileType(fileType);
					doc.setFilePath(filePath);
					doc.setKinds(kinds);
					// 判断是否存在此文件，如果在同一个目录下存在一样的文件，则进行覆盖，但是不进行数据库修改
					String ff = systemPath + "/" + fileName + "." + fileType;
					System.out.println(ff);
					File f = new File(ff);
					if (!f.exists()) {
						fileId = fileDao.uploadFile(doc);
						Document document = fileDao.selectFile(String.valueOf(fileId));
						userFile.setFileId(document);
						userFile.setUserId(user);
						if (fileId != 0)
							fileId = userFileDao.insertUserFile(userFile);
						else
							break;
					}

					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("/") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(systemPath + "/" + filename);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
						// + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();

				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
			json.put("status", 0);

		} finally {
			if (fileId != 0) {
				json.put("status", 1);
			} else
				json.put("status", 0);
			output.write(json.toString());
			output.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
