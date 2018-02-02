package com.isay.web;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.isay.model.SayInfo;
import com.isay.service.SayService;

public class CreateSay {

	public static SayService ss;
	public SayService getSs() {
		return ss;
	}
	public void setSs(SayService ss) {
		this.ss = ss;
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		SayInfo si = ctx.getBean(SayInfo.class);
//		si.setInfo("I am going testing.");
//		si.setTitle("test");
		create();
		//connectMysq();
	}
	
	
	public static void create() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SayInfo si = ctx.getBean(SayInfo.class);
		si.setInfo("I am going testing.");
		si.setTitle("test");
		ss.create(si);
	}
	
	public static void connectMysq() throws Exception{
		ApplicationContext qtx = new ClassPathXmlApplicationContext("bean-mysql.xml");
		DataSource ds = (DataSource) qtx.getBean("dataSource");
		System.out.println(ds.getConnection().toString());
	}

}
