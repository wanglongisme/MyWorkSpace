package com.isay.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.isay.model.Diary;

@Service("diaryService")
public class DiaryService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean create(Diary diary) {
		String sql = "INSERT INTO diary(title, info, createTime) VALUES(?,?,NOW())";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String title = df.format(new Date());
		int r = jdbcTemplate.update(sql, title,diary.getInfo());
		boolean res = (r>0) ? true : false;
		return res;
	}

	public boolean del(int diaryId) {
		String sql = "DELETE FROM diary WHERE id=?";
		int r = jdbcTemplate.update(sql, diaryId);
		boolean res = (r>0) ? true : false;
		return res;
	}

	public boolean update(Diary diary) {
		String sql = "UPDATE diary SET info=? WHERE id=?";
		int r = jdbcTemplate.update(sql, diary.getInfo(), diary.getId());
		boolean res = (r>0) ? true : false;
		return res;
	}

	public List<Diary> getDiary(int diaryId) {
		String sql = "SELECT id,title,info,LEFT(createTime,16) AS 'createTime' FROM diary WHERE id=?";
		List<Diary> diaryList = jdbcTemplate.query(sql, new Object[]{diaryId}, new BeanPropertyRowMapper(Diary.class));
		return diaryList;
		
	}

	public List<Diary> getList(int page) {
		int start = (page-1)*12;
		String sql = "SELECT id,info,LEFT(createTime,16) AS 'createTime' FROM diary ORDER BY createTime DESC LIMIT ?,12";
		List<Diary> diaryList = jdbcTemplate.query(sql, new Object[]{start}, new BeanPropertyRowMapper(Diary.class));
		return diaryList;
	}

	public List<Diary> getFullList() {
		String sql = "SELECT id,title,LEFT(createTime,16) AS 'createTime' FROM diary ORDER BY createTime DESC";
		List<Diary> diaryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Diary.class));
		return diaryList;
	}

	public int getTotal() {
		String sql = "SELECT COUNT(*) AS 'total' FROM diary";
		int total = jdbcTemplate.queryForObject(sql, Integer.class);
		return total;
	}

	public List<Diary> getTitleList(int page) {
		int start = (page-1)*12;
		String sql = "SELECT id,title,LEFT(createTime,16) AS 'createTime' FROM diary ORDER BY createTime DESC LIMIT ?,12";
		List<Diary> diaryList = jdbcTemplate.query(sql, new Object[]{start}, new BeanPropertyRowMapper(Diary.class));
		return diaryList;
	}

	
}
