package com.isay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isay.model.User;
import com.isay.service.UserService;

@Controller
public class UserControl {
	
	@Autowired
	private UserService us;
	
	@RequestMapping(value="/admin/login", method = RequestMethod.POST)
	public ModelAndView login(User user){
		User u = us.login(user);
		ModelAndView modelAndView = new ModelAndView();
		//modelAndView.addObject("user", u);
		if(u==null){
			modelAndView.setViewName("login_again");	
		}else{
			modelAndView.setViewName("admin");	
		}
		return modelAndView;
	}
}
