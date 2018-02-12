package com.isay.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("jobWantNum")
public class JobWantNum {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	Logger log = Logger.getRootLogger();
	
	/*
	 * �ɼ�51jobְλ�Ķ�ʱ����
	 * 2017-3-7 09:29:31
	 */
	//@Scheduled(cron="16 51 9 ? * MON")
	//@Scheduled(cron="16 32 12 ? * TUE,FRI")
	public void job1(){
		System.out.println(new Date()+"��ʼÿ������job1....");
		
		String daAreaSql = "select da.areaId,a.area from r_da_area da left join r_area a on a.areaId=da.areaId where da.id=1";
		List areaRes = jdbcTemplate.queryForList(daAreaSql);
		Iterator it = areaRes.iterator();
		//rex 1
		String pattern = "��(\\d+)��ְλ";
		Pattern r = Pattern.compile(pattern);
		//rex 2
		String p = "[0-9]+";
        Pattern r2 = Pattern.compile(p);
        String dataSql = "INSERT INTO r_da_data(id,dataVal,TIME,logId,areaId,dateVal) VALUES(1,?,now(),?,?,SUBSTRING(now(),6,5))";
        String logSql = "INSERT INTO r_da_log(TIME,requestUrl,CODE,LOG) VALUES(now(),?,?,?)";
		while(it.hasNext()) {
		    Map areaMap = (Map) it.next();
		    String areaId = areaMap.get("areaId").toString();
		    String rUrl = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea="+areaId+"&keyword=java&keywordtype=2&lang=c&stype=2&postchannel=0000&fromType=1&confirmdate=9";
		    Map<String,String> map = SendHttpUtil.sendPost(rUrl, "", "gbk");
		    try {
				Thread.sleep(32000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    if(map != null && map.size()>0){
		    	String code = map.get("code");
		    	String head = map.get("head");
		    	//insert log
		    	jdbcTemplate.update(logSql, rUrl, code, head);
		    	int logId = jdbcTemplate.queryForInt("SELECT max(logId) from r_da_log");
		    	//insert data
		    	String html = map.get("html");
				Matcher m = r.matcher(html);
				if (m.find( )) {
			   	  	Matcher m2 = r2.matcher(m.group());
			   	  	if(m2.find()){
			   	  		String data = m2.group();
			   	  		System.out.println("job1()-->{areaId:"+areaId+",data:"+data+"}");
			   	  		jdbcTemplate.update(dataSql, data, logId, areaId);
			   	  	}else{
			   	  		log.info("job1û��ƥ��,areaId="+areaId+",ģʽ:"+p);
			   	  		System.out.println("job1û��ƥ��,areaId="+areaId+",ģʽ:"+p);
			   	  	}
				} else {
		         System.out.println("û��ƥ��,ģʽ: "+pattern);
		      }
		   }
		}
	}
	
	/*
	 * �ɼ�������Ƹְλ�Ķ�ʱ����
	 * 2017-3-7 09:29:31
	 */
	//@Scheduled(cron="8 22 18 ? * MON")
	//@Scheduled(cron="8 16 11 ? * TUE,FRI")
	public void job2(){
		System.out.println(new Date()+"��ʼÿ������job2....");
		
		String daAreaSql = "select da.areaId,a.area from r_da_area da left join r_area a on a.areaId=da.areaId where da.id=2";
		List areaRes = jdbcTemplate.queryForList(daAreaSql);
		Iterator it = areaRes.iterator(); 
		//rex 1
		String pattern = "��<em>(\\d+)</em>��ְλ";
		Pattern r = Pattern.compile(pattern);
		//rex 2
		String p = "[0-9]+";
        Pattern r2 = Pattern.compile(p);
        String dataSql = "INSERT INTO r_da_data(id,dataVal,TIME,logId,areaId,dateVal) VALUES(2,?,now(),?,?,SUBSTRING(now(),6,5))";
        String logSql = "INSERT INTO r_da_log(TIME,requestUrl,CODE,LOG) VALUES(now(),?,?,?)";
		while(it.hasNext()) {
		    Map areaMap = (Map) it.next();
		    String areaId = areaMap.get("areaId").toString();
		    String area = areaMap.get("area").toString();
		    try {
		    	//������Ҫ����
				area = URLEncoder.encode(area,"utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		    String rUrl = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl="+area+"&kw=java&p=1&isadv=0";
		    Map<String,String> map = SendHttpUtil.sendPost(rUrl, "", "utf-8");
		    try {
				Thread.sleep(32000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    if(map != null && map.size()>0){
		    	String code = map.get("code");
		    	String head = map.get("head");
		    	//insert log
		    	jdbcTemplate.update(logSql, rUrl, code, head);
		    	int logId = jdbcTemplate.queryForInt("SELECT max(logId) from r_da_log");
		    	//insert data
		    	String html = map.get("html");
				Matcher m = r.matcher(html);
				if (m.find( )) {
			   	  	Matcher m2 = r2.matcher(m.group());
			   	  	if(m2.find()){
			   	  		String data = m2.group();
			   	  		System.out.println("job2()-->{areaId:"+areaId+",data:"+data+"}");
			   	  		jdbcTemplate.update(dataSql, data, logId, areaId);
			   	  	}else{
			   	  		System.out.println("job2û��ƥ��,areaId="+areaId+",ģʽ:"+p);
			   	  		log.info("job2û��ƥ��,areaId="+areaId+",ģʽ:"+p);
			   	  	}
				} else {
		         System.out.println("û��ƥ��,ģʽ: "+pattern);
		      }
		   }
		}  
	}
}
