package com.yunpan.bean;

public class Report {
	private Integer id;
	private Document fileId;
	private String reason;
	private String reportStatus;
	private String dateTime;
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Document getFileId() {
		return fileId;
	}

	public void setFileId(Document fileId) {
		this.fileId = fileId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
}
