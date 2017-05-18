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
		map.put("rmvb", "vidio");
		map.put("wmv", "vidio");
		map.put("rm", "vidio");
		map.put("avi", "vidio");
		map.put("mp4", "vidio");
		// music
		map.put("wma", "music");
		map.put("mpg", "music");
		map.put("mp3", "music");
		// picture
		map.put("png", "music");
		map.put("bmp", "music");
		map.put("jpg", "music");
		map.put("jpeg", "music");
		map.put("gif", "music");
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
