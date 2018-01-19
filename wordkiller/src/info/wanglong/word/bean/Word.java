package info.wanglong.word.bean;

public class Word {
	int id;
	String en;
	String cn;
	String type;
	String voiceLabel;
	int star;
	int indexCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVoiceLabel() {
		return voiceLabel;
	}
	public void setVoiceLabel(String voiceLabel) {
		this.voiceLabel = voiceLabel;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(int indexCode) {
		this.indexCode = indexCode;
	}
	
	@Override
	public String toString() {
		return "Word [id=" + id + ", en=" + en + ", cn=" + cn + ", type=" + type + ", voiceLabel=" + voiceLabel
				+ ", star=" + star + ", indexCode=" + indexCode + "]";
	}
	
}
