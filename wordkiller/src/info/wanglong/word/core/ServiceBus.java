package info.wanglong.word.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServiceBus {
	ApplicationContext context;
    protected JdbcTemplate jdbcTemplate;
	public ServiceBus() {
		this.context = new ClassPathXmlApplicationContext("info/wanglong/word/core/config.xml");
		this.jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
	}
}
