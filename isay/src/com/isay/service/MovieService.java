package com.isay.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isay.model.Book;
import com.isay.model.Movie;

@Service("movieService")
public class MovieService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<String> getMovieImgByDouBan(String movieName) throws IOException {
		 if(movieName == null || movieName.trim() == "") {
			 return null;
		 }
		 //String url = "https://api.douban.com/v2/movie/search?count=5&q="+movieName;
		 String url = "https://api.douban.com/v2/movie/search?count=5";
		 System.out.println(url);
		 Document document = Jsoup.connect(url).ignoreContentType(true).data("q", movieName)//请求参数  
				 .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")//设置urer-agent  get();
				 .timeout(3000)
				 .get();
		String html = document.body().text();
    	System.out.println("获取'"+movieName+"'body.size: "+html.length());
    	System.out.println(html);
    	//解析json
    	JsonObject jsonObject = new JsonParser().parse(html).getAsJsonObject();
    	JsonArray jsonArray = jsonObject.getAsJsonArray("subjects");
    	List<String> movieImgList = new ArrayList<String>();
    	for(JsonElement book : jsonArray) {
    		JsonElement j = book.getAsJsonObject().get("title");
    		if(j != null && movieName.equals(j.getAsString().trim())) {
    			//从搜索结果中匹配
    			System.out.println("已找到匹配节点: "+movieName);
    			JsonElement imageJe = book.getAsJsonObject().get("images");
    			JsonElement mediumJPG = imageJe.getAsJsonObject().get("medium");
    			movieImgList.add(mediumJPG.toString());
    		}
    	}
    	return movieImgList;
	}

	public boolean seeAMovie(Movie movie) {
		StringBuffer sql = new StringBuffer();
		if(movie != null){
			sql.append("INSERT INTO mymovie(movieName,seeTime,pic,status,des,star) VALUES(?,?,?,?,?,?)");
		}
		int r = jdbcTemplate.update(sql.toString(), movie.getMovieName(),movie.getSeeTime(),movie.getPic(),movie.getStatus(),movie.getDes(),movie.getStar());
		return (r>0) ? true : false;
	}

	public List<Movie> getMovieList() {
		String sql = 
				 " SELECT * FROM (SELECT id,movieName,seeTime,pic,STATUS,des,star FROM myMovie WHERE STATUS=2) AS t1"
				+" UNION ALL "
				+" SELECT * FROM (SELECT id,movieName,seeTime,pic,STATUS,des,star FROM myMovie WHERE STATUS=1 ORDER BY seeTime DESC) AS t3";
		List<Movie> mList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Movie.class));
		return mList;
	}
}
