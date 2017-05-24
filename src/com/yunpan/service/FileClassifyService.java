package com.yunpan.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author lon将文件类型分为几个大类
 *
 */
public class FileClassifyService {
	public String fileclassify(String type) {
		Map<String, String> map = new HashMap<String, String>();
		// 视频
		map.put("rmvb", "video");
		map.put("wmv", "video");
		map.put("rm", "video");
		map.put("avi", "video");
		map.put("mp4", "video");
		// music
		map.put("wma", "music");
		map.put("mpg", "music");
		map.put("mp3", "music");
		// picture
		map.put("png", "picture");
		map.put("bmp", "picture");
		map.put("jpg", "picture");
		map.put("jpeg", "picture");
		map.put("gif", "picture");
		// 文档
		map.put("doc", "document");
		map.put("docx", "document");
		map.put("txt", "document");
		map.put("ppt", "document");
		map.put("htm", "document");
		map.put("xls", "document");
		map.put("pdf", "document");
		// gzip
		map.put("zip", "gzip");
		map.put("rar", "gzip");
		map.put("gz", "gzip");
		map.put("jar", "gzip");
		String fileType = null;
		if (map.get(type) != null) {
			fileType = map.get(type);
		} else
			fileType = "others";
		return fileType;
	}
}
