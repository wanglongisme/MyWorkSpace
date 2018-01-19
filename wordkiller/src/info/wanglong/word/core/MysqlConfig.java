package info.wanglong.word.core;

/**
 * @desc mysql 配置类
 * @author wl
 * @time 2017-12-18 12:03:43 
 */
public class MysqlConfig implements IMyDBConfig {

	public String getDriverClass() {
		return "com.mysql.jdbc.Driver";
	}

	public String getUrl() {
		return "jdbc:mysql://localhost:3306/word?useUnicode=true&amp;characterEncoding=utf-8";
	}

	public String getUserName() {
		return "root";
	}

	public String getPassword() {
		return "123456";
	}

}
