package com.isay.service;

import com.isay.dao.SayDao;
import com.isay.model.SayInfo;

public class SayService {
	private SayDao sd;
	
	public SayDao getSd() {
		return sd;
	}
 
	public void setSd(SayDao sd) {
		this.sd = sd;
	}

	public String create(SayInfo si){
		return sd.create(si);
		
	}
}
