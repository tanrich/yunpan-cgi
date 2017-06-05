package com.yunpan.bean;



public class UserShare {
	private Integer id;
	private String url;
	private String code;
	private String dateTime;
	private int saveTimes;
	private int downloadTimes;
	private String status;
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public int getSaveTimes() {
		return saveTimes;
	}
	public void setSaveTimes(int saveTimes) {
		this.saveTimes = saveTimes;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private User userId;
	private Document fileId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public Document getFileId() {
		return fileId;
	}
	public void setFileId(Document fileId) {
		this.fileId = fileId;
	}
}
