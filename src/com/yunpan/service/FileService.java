package com.yunpan.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunpan.bean.Document;
import com.yunpan.bean.UserFile;
/**
 * 
 * @author lon
 *				给定一个目录，筛选出这个目录下的所有文件
 */
public class FileService {
public List<Document> dealFile(List<UserFile> userFileList,String path){
	List<Document> doclist = new ArrayList<Document>();
	Iterator<UserFile> it = userFileList.iterator();
	Document doc = new Document();
	while(it.hasNext()){
		
		if(doc.getFilePath().equals(path)){
			doclist.add(doc);
		}
	}
	return doclist; 
}
}
