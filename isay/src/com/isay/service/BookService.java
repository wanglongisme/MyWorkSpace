package com.isay.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isay.model.Book;
import com.isay.tool.SendHttpUtil;

@Service("BookService")
public class BookService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<String> getBookImgByDouBan(String bookName) {
		 Map<String,String> map = SendHttpUtil.sendPost("https://api.douban.com/v2/book/search?q="+bookName+"&count=10", "", "utf-8");
		 if(map != null && map.size()>0){
		    	String code = map.get("code");
		    	String head = map.get("head");
		    	String html = map.get("html");
		    	System.out.println("ªÒ»°°∂"+bookName+"°∑Õº∆¨: code="+code+", head="+head);
		    	//System.out.println(html);
		    	//Ω‚Œˆjson
		    	JsonObject jsonObject = new JsonParser().parse(html).getAsJsonObject();
		    	JsonArray jsonArray = jsonObject.getAsJsonArray("books");
		    	List<String> bookImgList = new ArrayList<String>();
		    	for(JsonElement book : jsonArray) {
		    		JsonElement j = book.getAsJsonObject().get("image");
		    		//System.out.println(j.getAsString());
		    		bookImgList.add(j.getAsString());
		    	}
		    	System.out.println(bookImgList.toString());
		    	return bookImgList;
		}
		return null;
	}

	public boolean readABook(Book book) {
		StringBuffer sql = new StringBuffer();
		if(book != null){
			sql.append("INSERT INTO myreadbook(bookName,readTime,pic,status,des,star) VALUES(?,?,?,?,?,?)");
		}
		int r = jdbcTemplate.update(sql.toString(), book.getBookName(),book.getReadTime(),book.getPic(),book.getStatus(),book.getDes(),book.getStar());
		return (r>0) ? true : false;
	}

	public List<Book> getBookList() {
		String sql = 
				 " SELECT * FROM (SELECT id,bookName,readTime,pic,STATUS,des,star FROM myReadBook WHERE STATUS=1 ORDER BY readTime DESC) AS t1"
				+" UNION ALL "
				+" SELECT * FROM (SELECT id,bookName,readTime,pic,STATUS,des,star FROM myReadBook WHERE STATUS=3) AS t2"
				+" UNION ALL "
				+" SELECT * FROM (SELECT id,bookName,readTime,pic,STATUS,des,star FROM myReadBook WHERE STATUS=2 ORDER BY readTime DESC) AS t3";
		List<Book> bookList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Book.class));
		return bookList;
	}
}
