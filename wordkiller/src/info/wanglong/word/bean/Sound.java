package info.wanglong.word.bean;

public class Sound {
	private int id;
	private String fileName;
	private String size;
	private String lan;
	private int spd;
	private String downLoadPath;
	public String getDownLoadPath() {
		return downLoadPath;
	}
	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getLan() {
		return lan;
	}
	public void setLan(String lan) {
		this.lan = lan;
	}
	public int getSpd() {
		return spd;
	}
	public void setSpd(int spd) {
		this.spd = spd;
	}
	
	@Override
	public String toString() {
		return "Sound [id=" + id + ", fileName=" + fileName + ", size=" + size + ", lan=" + lan + ", spd=" + spd + "]";
	}
	
	
}
