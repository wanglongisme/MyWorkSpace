package com.isay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isay.dao.DataDao;

@Service("dataService")
public class DataService{

	@Autowired
	private DataDao dd;
	
	public List getDataList(int page) {
		return dd.getDataList(page);
	}

	public List getList(int id, String date) {
		return dd.getList(id,date);
	}

	public List getDataAreaList(int id) {
		return dd.getDataAreaList(id);
	}

	public List getDataDateList(int id, int page) {
		return dd.getDataDateList(id, page);
	}

}
