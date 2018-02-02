package com.isay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isay.dao.AdminDaoImpl;
import com.isay.model.User;

@Service
public class AdminServiceImpl implements UserService{
	
	@Autowired
	private AdminDaoImpl adminDaoImpl;
	
	
	public User login(User user) {
		return adminDaoImpl.login(user);
	}
}
