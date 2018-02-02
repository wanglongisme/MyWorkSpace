package com.isay.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isay.service.DataService;
import com.isay.service.DiaryService;
import com.isay.tool.Check;

@Controller
public class DataControl {
	@Autowired
	private DataService ds;
	
	Logger log = Logger.getRootLogger();
	@ResponseBody
	@RequestMapping(value="/getDataNameList", method = RequestMethod.POST)
	public String getList(int page) throws Exception{
		List list = ds.getDataList(page);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(list);
	}

	@ResponseBody
	@RequestMapping(value="/getDataList", method = RequestMethod.POST)
	public String getDataList(int id,String dateArr) throws Exception{
		String date = dateArr.substring(0, dateArr.length()-1);
		//System.out.println(date);
		if(Check.checkAZ(date)){
			log.info("getDataList方法检测到非法字符: "+date);
			System.out.println("getDataList方法检测到非法字符: "+date);
			return "";
		}
		List list = ds.getList(id, date);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/getDataAreaList", method = RequestMethod.POST)
	public String getDataAreaList(int id) throws Exception{
		List list = ds.getDataAreaList(id);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/getDataDateList", method = RequestMethod.POST)
	public String getDataDateList(int id,int page) throws Exception{
		List list = ds.getDataDateList(id, page);
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	
}
