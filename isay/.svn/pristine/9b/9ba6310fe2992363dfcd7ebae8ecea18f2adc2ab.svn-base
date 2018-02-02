package com.isay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isay.dao.DiaryDao;
import com.isay.model.Diary;

@Service("toDiaryService")
public class DiaryService{

	@Autowired
	private DiaryDao dd;

	public boolean writeDiary(Diary d) {
		return dd.insertDiary(d);
	}


	public Diary getDiary(int id) {
		
		return dd.getDiary(id);
	}


	public List getDiaryList(int page) {
		return dd.getDiaryList(page);
	}


	public List getBlogList(int page) {
		return dd.getBlogList(page);
	}
	

	public boolean updateDiary(Diary diary) {
		return dd.updateDiary(diary);
	}


	public String getPage(String pageType) {
		return dd.getPage(pageType);
	}


	public boolean delDiary(int id) {
		return dd.delDiary(id);
	}


	public List getBlogAndDiaryList(int page) {
		return dd.getBlogAndDiaryList(page);
	}
	
}
