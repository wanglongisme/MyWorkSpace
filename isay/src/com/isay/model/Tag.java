package com.isay.model;

public class Tag {
	private int id;
	private String name;
	private int isUse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + ", isUse=" + isUse + "]";
	}
	
	
}
