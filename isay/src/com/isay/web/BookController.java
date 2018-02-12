package com.isay.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isay.model.Book;
import com.isay.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bs;
	
	@ResponseBody
	@RequestMapping(value="/getBookImgByDouBan", method = RequestMethod.POST)
	public String getBookImgByDouBan(String bookName){
		List<String> res = bs.getBookImgByDouBan(bookName);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(res);
	}
	
	@ResponseBody
	@RequestMapping(value="/readABook", method = RequestMethod.POST)
	public String readABook(Book book){
		return bs.readABook(book)+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/getBookList", method = RequestMethod.POST)
	public String getBookList() throws Exception{
		List list = bs.getBookList();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(list);
	}
}
