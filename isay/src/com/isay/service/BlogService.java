package com.isay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.isay.model.Blog;
import com.isay.model.Tag;

@Service("blogService")
public class BlogService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean create(Blog blog) {
		String sql = "INSERT INTO Blog(title, info, createTime) VALUES(?,?,NOW())";
		int r = jdbcTemplate.update(sql, blog.getTitle(),blog.getInfo());
		boolean res = (r>0) ? true : false;
		return res;
	}

	public boolean del(int blogId) {
		String sql = "DELETE FROM blog WHERE id=?";
		int r = jdbcTemplate.update(sql, blogId);
		boolean res = (r>0) ? true : false;
		return res;
	}

	public boolean update(Blog blog) {
		String sql = "UPDATE blog SET title=?,info=? WHERE id=?";
		int r = jdbcTemplate.update(sql, blog.getTitle(), blog.getInfo(), blog.getId());
		boolean res = (r>0) ? true : false;
		return res;
	}

	public List<Blog> getBlog(int blogId) {
		String sql = "SELECT id,title,info,LEFT(createTime,16) AS 'createTime' FROM blog WHERE id=?";
		List<Blog> blogList = jdbcTemplate.query(sql, new Object[] {blogId}, new BeanPropertyRowMapper(Blog.class));
		return blogList;
		
	}

	public List<Blog> getList(int page) {
		int start = (page-1)*12;
		String sql = "SELECT id,title,LEFT(createTime,16) AS 'createTime' FROM blog ORDER BY createTime DESC LIMIT ?,12";
		List<Blog> blogList = jdbcTemplate.query(sql, new Object[]{start}, new BeanPropertyRowMapper(Blog.class));
		return blogList;
	}

	public List<Blog> getFullList() {
		String sql = "SELECT id,title,LEFT(createTime,10) AS 'createTime' FROM blog ORDER BY createTime DESC";
		List<Blog> blogList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Blog.class));
		return blogList;
	}

	public int getTotal() {
		String sql = "SELECT COUNT(*) AS 'total' FROM blog";
		int total = jdbcTemplate.queryForObject(sql, Integer.class);
		return total;
	}

	public List<Tag> getTagList() {
		String sql = "SELECT id,name,isUse FROM blogLabel ORDER BY id DESC";
		List<Tag> tagList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Tag.class));
		return tagList;
	}

	public List<Blog> searchContent(String keyWord) {
		String sql = 
				 " SELECT id,title,LEFT(createTime,10) AS 'createTime','1' AS 'type' FROM blog WHERE title LIKE ?"
				+" UNION ALL"
				+" SELECT id,title,'' AS 'createTime','2' AS 'type' FROM diary WHERE title LIKE ?";
		keyWord = "%"+keyWord+"%";
		List<Blog> blogList = jdbcTemplate.query(sql, new Object[]{keyWord,keyWord}, new BeanPropertyRowMapper(Blog.class));
		System.out.println(blogList.toString());
		return blogList;
	}

}
