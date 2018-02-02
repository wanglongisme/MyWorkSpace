package com.isay.dao;

import com.isay.model.SayInfo;

public class SayDao {
	public String create(SayInfo si){
		System.out.println(si.toString());
		return "ok";
	}
}
