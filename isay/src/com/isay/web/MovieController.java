package com.isay.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isay.model.Book;
import com.isay.model.Movie;
import com.isay.service.MovieService;

@Controller
public class MovieController {
	
	@Autowired
	private MovieService ms;
	
	@ResponseBody
	@RequestMapping(value="/getMovieImgByDouBan", method = RequestMethod.POST)
	public String getMovieImgByDouBan(String movieName){
		List<String> res = null;
		try {
			res = ms.getMovieImgByDouBan(movieName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(res.toString());
		return res.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/seeAMovie", method = RequestMethod.POST)
	public String seeAMovie(Movie movie){
		return String.valueOf(ms.seeAMovie(movie));
	}
	
	@ResponseBody
	@RequestMapping(value="/getMovieList", method = RequestMethod.POST)
	public String getMovieList() throws Exception{
		List<Movie> list = ms.getMovieList();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(list);
	}
	
}
