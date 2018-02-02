package com.isay.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public class InsertTable_R_da_data {
	
	public static void main(String[] args) {
		String[] areaArr = {"010000","050000","070200","120200","120300","170200","200200","210200","220200","230200","230300","240200"};
		String[] dateArr = {"03-09","03-10","03-11","03-12","03-13","03-14","03-15","03-16","03-17","03-18","03-19","03-20","03-21","03-22","03-23",};
		String sql = "INSERT INTO aa(id,dataVal,TIME,logId,areaId,dateVal) VALUES(1,4567,NOW(),0,?,?);";
		int num = 0;
		
		String url = "jdbc:mysql://localhost:3306/wldata?characterEncoding=utf-8";  
		String name = "com.mysql.jdbc.Driver";  
	    String user = "root";  
	    String password = "123";  
	    Connection conn = null;  
	    PreparedStatement pst = null;  
	    try { 
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            conn.setAutoCommit(false);
        	for(int i=0; i<areaArr.length; i++){
    			for(int j=0; j<dateArr.length; j++){
    				pst = conn.prepareStatement(sql);//准备执行语句  
    				pst.setString(1, areaArr[i]);
    				pst.setString(2, dateArr[j]);
                	int r = pst.executeUpdate();
    				num = num + r;
    			}
    		}
        	System.out.println(num);
            conn.commit();
            pst.close();
            conn.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
	
	}
}
