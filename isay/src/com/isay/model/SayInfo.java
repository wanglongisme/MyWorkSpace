package com.isay.model;


/*
 * 没有使用该类,本打算作为日志bean,后台日志和blog使用一个表了
 */
public class SayInfo {
	private String title;
	private String info;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public String toString() {
		return "SayInfo [title=" + title + ", info=" + info + "]";
	}
	
}
