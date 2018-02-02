package com.isay.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.isay.model.Diary;

@Repository("diaryDaoImpl")
public class DiaryDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	Logger log = Logger.getRootLogger();
	

	public boolean insertDiary(Diary d) {
		StringBuffer sql = new StringBuffer();
		if(d!=null && "".equals(d.getTitle().trim())){
			sql.append("INSERT INTO r_diary(info,createTime,title,type) VALUES(?,NOW(),?,2)");
		}else{
			sql.append("INSERT INTO r_diary(info,createTime,title,type) VALUES(?,NOW(),?,1)");
		}
		int r = jdbcTemplate.update(sql.toString(), d.getInfo(),d.getTitle());
		boolean res = (r>0)?true:false;
		return res;
	}


	public Diary getDiary(int id) {
		String sql = "select id,info, title, left(createTime,16) as 'createTime' from r_diary where id=?";
		Diary diary = null;
		try{
			diary = (Diary) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(Diary.class));
		}catch(EmptyResultDataAccessException e){
			String info = "not found the diary by id: "+id;
			log.info(info);
		}
		return diary;
	}


	public List getBlogAndDiaryList(int page) {
		int start = (page-1)*12;
		String sql = "select id,info,createTime,title,type from r_diary order by id desc limit ?,12";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, new Object[]{start});
		return res;
	}
	

	public List getDiaryList(int page) {
		int start = (page-1)*12;
		String sql = "select id,info,createTime,title,type from r_diary order by createTime desc limit ?,12";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, new Object[]{start});
		return res;
	}
	

	public List getBlogList(int page) {
		int start = (page-1)*12;
		String sql = "select id,info,createTime,title from r_diary where type=1 order by id desc limit ?,12";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql, new Object[]{start});
		return res;
	}


	public boolean updateDiary(Diary diary) {
		String sql = "update r_diary set title=?,info=? where id=?";
		int r = jdbcTemplate.update(sql, diary.getTitle(), diary.getInfo(), diary.getId());
		boolean res = (r>0)?true:false;
		return res;
	}


	public String getPage(String pageType) {
		StringBuffer sql = new StringBuffer();
		if(pageType == null || "0".equals(pageType)){
			//查blog和diary的全部页数
			sql.append("SELECT CEIL(COUNT(*)/12) AS 'page' FROM r_diary");
		}else if("1".equals(pageType)){
			//blog
			sql.append("SELECT CEIL(COUNT(*)/12) AS 'page' FROM r_diary where type=1 ");
		}else if("2".equals(pageType)){
			//diary
			sql.append("SELECT CEIL(COUNT(*)/12) AS 'page' FROM r_diary where type=2 ");
		}
		String page = jdbcTemplate.queryForObject(sql.toString(), new Object[]{},String.class);
		return page;
	}


	public boolean delDiary(int id) {
		String sql = "delete from r_diary where id=?";
		int r = jdbcTemplate.update(sql, id);
		boolean res = (r>0)?true:false;
		return res;
	}


}
