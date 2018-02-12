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
import com.isay.model.Blog;
import com.isay.model.Tag;
import com.isay.service.BlogService;

/**
 * Blog Controller
 * @codeTime 2018-02-08 10:35:54
 * @author wl
 * @desc code refactor.
 */
@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	private Gson gson = new Gson();
	
	/**
	 * create blog
	 * @param Blog object
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/createBlog", method = RequestMethod.POST)
	public String create(Blog blog){
		boolean res = this.blogService.create(blog);
		return String.valueOf(res);
	}
	
	/**
	 * delete blog by id
	 * @param id
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/delBlog", method = RequestMethod.POST)
	public String del(int blogId){
		boolean res = this.blogService.del(blogId);
		return String.valueOf(res);
	}
	
	/**
	 * update a blog
	 * @param Blog
	 * @return String "true" or "false"
	 */
	@ResponseBody
	@RequestMapping(value="/updateBlog", method = RequestMethod.POST)
	public String update(Blog blog){
		boolean res = this.blogService.update(blog);
		return String.valueOf(res);
	}
	/**
	 * get a blog
	 * @param blogId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getBlogById")
	public String getBlog(int blogId){
		List<Blog> blogList = this.blogService.getBlog(blogId);
		return gson.toJson(blogList);
	}
	
	/**
	 * get blog list by page.
	 * @param  int page
	 * @return List (Blog)
	 */
	@ResponseBody
	@RequestMapping(value="/getBlogListByPage", method = RequestMethod.POST)
	public String getList(int page){
		List<Blog> list = this.blogService.getList(page);
		return gson.toJson(list);
	}
	
	/**
	 * get blog list by full.
	 * @param 
	 * @return List (id,title,createTime)
	 */
	@ResponseBody
	@RequestMapping(value="/getBlogList", method = RequestMethod.POST)
	public String getFullList(){
		List<Blog> list = this.blogService.getFullList();
		return gson.toJson(list);
	}
	/**
	 * get blog page and total.
	 * @return page and total
	 */
	@ResponseBody
	@RequestMapping(value="/getBlogPageAndTotal", method = RequestMethod.POST)
	public String getBlogPageAndTotal(){
		int total = this.blogService.getTotal();
		List<Integer> list = new ArrayList<Integer>();
		int page = (int) Math.ceil(total/12);
		list.add(total);
		list.add(page);
		return gson.toJson(list);
	}
	
	/**
	 * get blog tag list by full.
	 * @param 
	 * @return List (id,name,isUse)
	 */
	@ResponseBody
	@RequestMapping(value="/getBlogTagList", method = RequestMethod.POST)
	public String getTagList(){
		List<Tag> list = this.blogService.getTagList();
		return gson.toJson(list);
	}
	
	/**
	 * search blog and diary by title.
	 * @param 
	 * @return List (id,name,isUse)
	 */
	@ResponseBody
	@RequestMapping(value="/searchContent", method = RequestMethod.POST)
	public String searchContent(String keyWord){
		//此处泛型Blog并不正规.
		List<Blog> list = this.blogService.searchContent(keyWord);
		return gson.toJson(list);
	}
	
}
