package com.isay.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isay.model.Diary;
import com.isay.service.DiaryService;

@Controller
public class DiaryController {

	@Autowired
	private DiaryService diaryService;
	
	private Gson gson = new Gson();
	/**
	 * write a diary
	 * @param Diary
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/createDiary", method = RequestMethod.POST)
	public String createDiary(Diary diary){
		boolean res = this.diaryService.create(diary);
		return String.valueOf(res);
	}
	
	/**
	 * delete diary by id
	 * @param id
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/delDiary", method = RequestMethod.POST)
	public String del(int diaryId){
		boolean res = this.diaryService.del(diaryId);
		return String.valueOf(res);
	}
	
	/**
	 * update a diary
	 * @param Diary
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/updateDiary", method = RequestMethod.POST)
	public String update(Diary diary){
		boolean res = this.diaryService.update(diary);
		return String.valueOf(res);
	}
	/**
	 * get a diary
	 * @param blogId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getDiaryById")
	public String getBlog(int diaryId){
		List<Diary> blogList = this.diaryService.getDiary(diaryId);
		return gson.toJson(blogList);
	}
	
	/**
	 * get diary list by page.
	 * @param  int page
	 * @return List (Blog)
	 */
	@ResponseBody
	@RequestMapping(value="/getDiaryListByPage", method = RequestMethod.POST)
	public String getList(int page){
		List<Diary> list = this.diaryService.getList(page);
		return gson.toJson(list);
	}
	
	/**
	 * get diary title list by page.
	 * @param  int page
	 * @return List (Blog)
	 */
	@ResponseBody
	@RequestMapping(value="/getDiaryTitleListByPage", method = RequestMethod.POST)
	public String getTitleList(int page){
		List<Diary> list = this.diaryService.getTitleList(page);
		return gson.toJson(list);
	}
	/**
	 * get diary list by full.
	 * @param 
	 * @return List (id,title,createTime)
	 */
	@ResponseBody
	@RequestMapping(value="/getDiaryList", method = RequestMethod.POST)
	public String getFullList(){
		List<Diary> list = this.diaryService.getFullList();
		return gson.toJson(list);
	}
	/**
	 * get diary page and total.
	 * @return page and total
	 */
	@ResponseBody
	@RequestMapping(value="/getDiaryPageAndTotal", method = RequestMethod.POST)
	public String getDiaryPageAndTotal(){
		int total = this.diaryService.getTotal();
		List<Integer> list = new ArrayList<Integer>();
		int page = (int) Math.ceil(total/12);
		list.add(total);
		list.add(page);
		return gson.toJson(list);
	}
	
}
