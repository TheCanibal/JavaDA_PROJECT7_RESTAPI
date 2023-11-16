package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.repositories.DBUserRepository;

@Controller
public class LoginController {

    @Autowired
    private DBUserRepository userRepository;

    /**
     * Display the login page
     * 
     * @return login.html file to load
     */
    @GetMapping("/login")
    public String login(Model model) {
	return "login";
    }

    /**
     * display user list
     * 
     * @return user list page
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
	ModelAndView mav = new ModelAndView();
	mav.addObject("users", userRepository.findAll());
	mav.setViewName("user/list");
	return mav;
    }

    /**
     * display error 403 page
     * 
     * @return 403 error page
     */
    @GetMapping("/access-denied")
    public ModelAndView error() {
	ModelAndView mav = new ModelAndView("403");
	String errorMessage = "You are not authorized for the requested data.";
	mav.addObject("errorMsg", errorMessage);
	return mav;
    }
}
