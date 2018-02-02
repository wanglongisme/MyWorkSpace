package com.isay.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isay.model.Diary;
import com.isay.service.DiaryService;

@Controller
public class DiaryControl {

	@Autowired
	private DiaryService ds;
	
	@ResponseBody
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(Diary diary){
		boolean res = ds.writeDiary(diary);
		return res+"";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getList", method = RequestMethod.POST)
	public String getList(int page) throws Exception{
		List list = ds.getBlogAndDiaryList(page);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//System.out.println(gson.toJson(list));
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/getBlogList", method = RequestMethod.POST)
	public String getBlogList(int page) throws Exception{
		List list = ds.getBlogList(page);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//System.out.println(gson.toJson(list));
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="/getDiaryList", method = RequestMethod.POST)
	public String getDiaryList(int page) throws Exception{
		List list = ds.getDiaryList(page);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		//System.out.println(gson.toJson(list));
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping("/getDiary/{id}")
	public String getDiary(@PathVariable("id") int id) throws Exception{
		Diary diary = ds.getDiary(id);
		Gson gson = new Gson();
		return gson.toJson(diary);
	}
	
	@ResponseBody
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(Diary diary){
		boolean res = ds.updateDiary(diary);
		return res+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/getPage", method = RequestMethod.POST)
	public String getPage(String pageType) throws Exception{
		String page = ds.getPage(pageType);
		return page;
	}
	
	@ResponseBody
	@RequestMapping(value="/delDiary", method = RequestMethod.POST)
	public String delDiary(int id){
		boolean res = ds.delDiary(id);
		return res+"";
	}
	
}
