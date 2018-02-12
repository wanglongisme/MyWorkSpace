package com.isay.model;

public class Blog {
	private int id; 
	private String info;
	private String createTime;
	private String title;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	@Override
	public String toString() {
		return "Diary [id=" + id + ", info=" + info + ", createTime=" + createTime + ", title=" + title + "]";
	}
	
	
}
