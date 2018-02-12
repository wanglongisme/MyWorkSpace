package com.isay.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.isay.model.User;
import com.isay.tool.MD5;

@Repository("adminDaoImpl")
public class AdminDaoImpl {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	Logger log = Logger.getRootLogger();
	
	public User login(User user){
		//System.out.println(user);
		String sql = "select id,username,pwd from r_user where username=? and pwd=?";
		User u = null;
		try{
			u = (User) jdbcTemplate.queryForObject(sql, new Object[]{user.getUserName(), MD5.getMD5(user.getPwd())}, new BeanPropertyRowMapper(User.class));
		}catch(EmptyResultDataAccessException e){
			//System.out.println("not found the user: "+user.toString());
			log.info("not found the user: "+user.toString());
		}
		return u;
	}
}
